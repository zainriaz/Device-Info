package com.ytheekshana.deviceinfo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class tabApps extends Fragment {
    int apptype = 0, TextDisColor;
    Thread loadApps;
    SwipeRefreshLayout swipeapplist;
    Activity activity;
    Context context;
    RecyclerView recyclerInstalledApps;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
        activity = null;
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
        final RelativeLayout relmain = rootView.findViewById(R.id.relmain);
        TextDisColor = MainActivity.themeColor;
        Spinner spinnerAppType = rootView.findViewById(R.id.spinnerAppType);
        swipeapplist = rootView.findViewById(R.id.swipeapplist);
        recyclerInstalledApps = rootView.findViewById(R.id.recyclerInstalledApps);

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
        List<PackageInfo> packs = pm.getInstalledPackages(0);

        Collections.sort(packs, new Comparator<PackageInfo>() {
            @Override
            public int compare(PackageInfo arg0, PackageInfo arg1) {
                CharSequence name0 = arg0.applicationInfo.loadLabel(pm);
                CharSequence name1 = arg1.applicationInfo.loadLabel(pm);
                return name0.toString().compareTo(name1.toString());
            }
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
        return res;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != apptype;
    }
}