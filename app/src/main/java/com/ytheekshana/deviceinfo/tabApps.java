package com.ytheekshana.deviceinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class tabApps extends Fragment implements SearchView.OnQueryTextListener {
    private int apptype = 0;
    private Thread loadApps;
    private SwipeRefreshLayout swipeapplist;
    Context context;
    private RecyclerView recyclerInstalledApps;
    PackageManager pm;
    private RecyclerView.Adapter appAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            loadApps = new Thread() {
                @Override
                public void run() {

                    swipeapplist.post(() -> {
                        if (!swipeapplist.isRefreshing()) {
                            swipeapplist.setRefreshing(true);
                        }
                    });
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    appAdapter = new AppAdapter(context, getInstalledApps());
                    recyclerInstalledApps.post(() -> {

                        recyclerInstalledApps.setLayoutManager(layoutManager);
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.recycler_layout_animation);

                        recyclerInstalledApps.setLayoutAnimation(controller);
                        recyclerInstalledApps.scheduleLayoutAnimation();
                        recyclerInstalledApps.setAdapter(appAdapter);
                    });

                    swipeapplist.post(() -> {
                        if (swipeapplist.isRefreshing()) {
                            swipeapplist.setRefreshing(false);
                        }
                    });
                }
            };
            loadApps.start();
        }
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tabapps, container, false);
        Spinner spinnerAppType = rootView.findViewById(R.id.spinnerAppType);
        swipeapplist = rootView.findViewById(R.id.swipeapplist);
        swipeapplist.setColorSchemeColors(MainActivity.themeColor);
        recyclerInstalledApps = rootView.findViewById(R.id.recyclerInstalledApps);
        setHasOptionsMenu(true);

        if (getUserVisibleHint()) {
            loadApps = new Thread() {
                @Override
                public void run() {

                    swipeapplist.post(() -> {
                        if (!swipeapplist.isRefreshing()) {
                            swipeapplist.setRefreshing(true);
                        }
                    });

                    appAdapter = new AppAdapter(context, getInstalledApps());
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    recyclerInstalledApps.post(() -> {
                        recyclerInstalledApps.setLayoutManager(layoutManager);
                        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.recycler_layout_animation);

                        recyclerInstalledApps.setLayoutAnimation(controller);
                        recyclerInstalledApps.scheduleLayoutAnimation();
                        recyclerInstalledApps.setAdapter(appAdapter);
                    });

                    swipeapplist.post(() -> {
                        if (swipeapplist.isRefreshing()) {
                            swipeapplist.setRefreshing(false);
                        }
                    });
                }
            };
            loadApps.start();
        }

        swipeapplist.setOnRefreshListener(() -> {
            //new Thread(loadApps).start();
            if (appAdapter instanceof AppAdapter) {
                reloadAppList();
            }
        });

        spinnerAppType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    apptype = 1;
                } else if (i == 0) {
                    apptype = 0;
                }
                if (appAdapter != null) {
                    reloadAppList();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return rootView;
    }

    private void reloadAppList() {
        new Thread() {
            @Override
            public void run() {
                swipeapplist.post(() -> {
                    if (!swipeapplist.isRefreshing()) {
                        swipeapplist.setRefreshing(true);
                    }
                });
                ((AppAdapter) appAdapter).addData(getInstalledApps());
                swipeapplist.post(() -> {
                    appAdapter.notifyDataSetChanged();
                    if (swipeapplist.isRefreshing()) {
                        swipeapplist.setRefreshing(false);
                    }
                });
            }
        }.start();
    }

    private ArrayList<AppInfo> getInstalledApps() {

        ArrayList<AppInfo> res = new ArrayList<>();
        try {
            if (context != null) {
                pm = context.getPackageManager();
                List<PackageInfo> packs = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS);
                Collections.sort(packs, (arg0, arg1) -> {
                    CharSequence name0 = arg0.applicationInfo.loadLabel(pm);
                    CharSequence name1 = arg1.applicationInfo.loadLabel(pm);
                    return name0.toString().compareTo(name1.toString());
                });
                for (int i = 0; i < packs.size(); i++) {
                    PackageInfo p = packs.get(i);
                    if ((!isSystemPackage(p))) {
                        String appName = p.applicationInfo.loadLabel(context.getPackageManager()).toString();
                        String packageName = p.applicationInfo.packageName;
                        String appVersion = "Version : " + p.versionName;
                        Drawable icon = p.applicationInfo.loadIcon(context.getPackageManager());
                        res.add(new AppInfo(appName, packageName, appVersion, icon));
                    }
                }
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(true);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != apptype;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (recyclerInstalledApps.getAdapter() != null) {
            ((AppAdapter) appAdapter).getFilter().filter(newText);
        }
        return false;
    }
}