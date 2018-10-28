package com.ytheekshana.deviceinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class tabApps extends Fragment {
    private int apptype = 0;
    private Thread loadApps;
    SwipeRefreshLayout swipeapplist;
    Context context;
    RecyclerView recyclerInstalledApps;

    @Override
    public void onAttach(Context context) {
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

                    swipeapplist.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!swipeapplist.isRefreshing()) {
                                swipeapplist.setRefreshing(true);
                            }
                        }
                    });
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    final RecyclerView.Adapter appAdapter = new AppAdapter(context, getInstalledApps());
                    recyclerInstalledApps.post(new Runnable() {
                        @Override
                        public void run() {

                            recyclerInstalledApps.setLayoutManager(layoutManager);
                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.recycler_layout_animation);

                            recyclerInstalledApps.setLayoutAnimation(controller);
                            recyclerInstalledApps.scheduleLayoutAnimation();
                            recyclerInstalledApps.setAdapter(appAdapter);
                        }
                    });

                    swipeapplist.post(new Runnable() {
                        @Override
                        public void run() {
                            if (swipeapplist.isRefreshing()) {
                                swipeapplist.setRefreshing(false);
                            }
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

                    swipeapplist.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!swipeapplist.isRefreshing()) {
                                swipeapplist.setRefreshing(true);
                            }
                        }
                    });

                    final RecyclerView.Adapter appAdapter = new AppAdapter(context, getInstalledApps());
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    recyclerInstalledApps.post(new Runnable() {
                        @Override
                        public void run() {
                            recyclerInstalledApps.setLayoutManager(layoutManager);
                            LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.recycler_layout_animation);

                            recyclerInstalledApps.setLayoutAnimation(controller);
                            recyclerInstalledApps.scheduleLayoutAnimation();
                            recyclerInstalledApps.setAdapter(appAdapter);
                        }
                    });

                    swipeapplist.post(new Runnable() {
                        @Override
                        public void run() {
                            if (swipeapplist.isRefreshing()) {
                                swipeapplist.setRefreshing(false);
                            }
                        }
                    });
                }
            };
            loadApps.start();
        }

        swipeapplist.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(loadApps).start();
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
                new Thread(loadApps).start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return rootView;
    }

    private ArrayList<AppInfo> getInstalledApps() {
        ArrayList<AppInfo> res = new ArrayList<>();
        final PackageManager pm = context.getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS);

        if (MainActivity.appsort == 1) {
            Collections.sort(packs, new Comparator<PackageInfo>() {
                @Override
                public int compare(PackageInfo arg0, PackageInfo arg1) {
                    CharSequence name0 = arg0.applicationInfo.loadLabel(pm);
                    CharSequence name1 = arg1.applicationInfo.loadLabel(pm);
                    return name0.toString().compareTo(name1.toString());
                }
            });
        } else if (MainActivity.appsort == 2) {
            Collections.sort(packs, new Comparator<PackageInfo>() {
                @Override
                public int compare(PackageInfo arg0, PackageInfo arg1) {
                    long name0 = new File(arg0.applicationInfo.publicSourceDir).length();
                    long name1 = new File(arg1.applicationInfo.publicSourceDir).length();
                    return Long.compare(name1, name0);
                }
            });
        } else if (MainActivity.appsort == 3) {
            Collections.sort(packs, new Comparator<PackageInfo>() {
                @Override
                public int compare(PackageInfo arg0, PackageInfo arg1) {
                    Date name0 = new Date(arg0.lastUpdateTime);
                    Date name1 = new Date(arg1.lastUpdateTime);
                    return name1.compareTo(name0);
                }
            });
        }

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
        return res;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_date:
                MainActivity.appsort = 3;
                loadApps.start();
                break;
            case R.id.action_sort_name:
                MainActivity.appsort = 1;
                loadApps.start();
                break;
            case R.id.action_sort_size:
                MainActivity.appsort = 2;
                loadApps.start();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != apptype;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem menuSort = menu.findItem(R.id.action_sort);
        menuSort.setVisible(true);
    }
}