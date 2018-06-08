package com.ytheekshana.deviceinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class tabApps extends Fragment {
    int apptype = 0;
    Thread LoadApps;
    private PopupWindow mPopupWindow;
    TextView txtappname,txtpackagename,txtappversion,txtappminsdk,txtapptargetsdk,txtappinstalldate,txtappupdatedate;
    ImageView imgappicon;
    ListView userInstalledApps;
    ProgressBar progressLoadApps;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && getActivity()!=null){
            //Toast.makeText(getActivity(), "js", Toast.LENGTH_SHORT).show();
            LoadApps = new Thread() {
                @Override
                public void run() {

                    progressLoadApps.post(new Runnable() {
                        @Override
                        public void run() {
                            progressLoadApps.setVisibility(View.VISIBLE);
                        }
                    });

                    userInstalledApps.post(new Runnable() {
                        @Override
                        public void run() {
                            userInstalledApps.setVisibility(View.GONE);
                        }
                    });

                    List<AppList> installedApps = getInstalledApps();
                    final AppAdapter installedAppAdapter = new AppAdapter(Objects.requireNonNull(getActivity()), installedApps);
                    userInstalledApps.post(new Runnable() {
                        @Override
                        public void run() {
                            userInstalledApps.setAdapter(installedAppAdapter);
                        }
                    });

                    progressLoadApps.post(new Runnable() {
                        @Override
                        public void run() {
                            progressLoadApps.setVisibility(View.GONE);
                        }
                    });

                    userInstalledApps.post(new Runnable() {
                        @Override
                        public void run() {
                            userInstalledApps.setVisibility(View.VISIBLE);
                        }
                    });
                }
            };
            LoadApps.start();
        }
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tabapps, container, false);
        final RelativeLayout relmain = rootView.findViewById(R.id.relmain);
        Spinner spinnerAppType = rootView.findViewById(R.id.spinnerAppType);
        spinnerAppType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    apptype = 1;
                } else if (i == 0) {
                    apptype = 0;
                }
                new Thread(LoadApps).start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        userInstalledApps = rootView.findViewById(R.id.installed_app_list);
        progressLoadApps = rootView.findViewById(R.id.progressLoadApps);

        userInstalledApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String packgename = ((TextView) view.findViewById(R.id.list_package_name)).getText().toString();
                String appname = ((TextView) view.findViewById(R.id.list_app_name)).getText().toString();
                String appversion = ((TextView) view.findViewById(R.id.list_version_name)).getText().toString();
                //Toast.makeText(getContext(), packgename, Toast.LENGTH_SHORT).show();

                try {
                    LayoutInflater inflater = (LayoutInflater) Objects.requireNonNull(getContext()).getSystemService(LAYOUT_INFLATER_SERVICE);
                    View customView = inflater != null ? inflater.inflate(R.layout.apps_popup, null) : null;

                    if (customView != null) {
                        txtappname = customView.findViewById(R.id.txtappnameheading);
                    }
                    if (customView != null) {
                        txtpackagename = customView.findViewById(R.id.txtpackagenameheading);
                    }
                    if (customView != null) {
                        txtappversion = customView.findViewById(R.id.txtappversion);
                    }
                    if (customView != null) {
                        txtappminsdk = customView.findViewById(R.id.txtappminsdk);
                    }
                    if (customView != null) {
                        txtapptargetsdk = customView.findViewById(R.id.txtapptargetsdk);
                    }
                    if (customView != null) {
                        txtappinstalldate = customView.findViewById(R.id.txtappinstalldate);
                    }
                    if (customView != null) {
                        txtappupdatedate = customView.findViewById(R.id.txtappupdatedate);
                    }
                    /*if (customView != null) {
                        txtappdescription = customView.findViewById(R.id.txtappdescription);
                    }
                    */
                    if (customView != null) {
                        imgappicon = customView.findViewById(R.id.imgappicon);
                    }

                    mPopupWindow = new PopupWindow(
                            customView,
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    mPopupWindow.setElevation(5.0f);

                    txtappname.setText(appname);
                    txtpackagename.setText(packgename);
                    txtappversion.setText(appversion);

                    ApplicationInfo appinfo = getContext().getPackageManager().getApplicationInfo(packgename, 0);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        int minsdk = appinfo.minSdkVersion;
                        String minsdkall = "Min : Android " + GetDetails.getAndroidVersion(minsdk) + " (" + GetDetails.GetOSName(minsdk) + ", API " + minsdk + ")";
                        txtappminsdk.setText(minsdkall);
                    } else {
                        txtappminsdk.setVisibility(View.GONE);
                        txtappminsdk.setText("Unknown");
                    }
                    int targetsdk = appinfo.targetSdkVersion;
                    String targetsdkall = "Target : Android " + GetDetails.getAndroidVersion(targetsdk) + " (" + GetDetails.GetOSName(targetsdk) + ", API " + targetsdk + ")";
                    txtapptargetsdk.setText(targetsdkall);

                    String insdate = new SimpleDateFormat("EEE, d MMM yyyy", Locale.US).format(new Date(getContext().getPackageManager().getPackageInfo(packgename, 0).firstInstallTime));
                    String finalinsdate = "Installed : " + insdate;
                    txtappinstalldate.setText(finalinsdate);

                    String updatedate = new SimpleDateFormat("EEE, d MMM yyyy", Locale.US).format(new Date(getContext().getPackageManager().getPackageInfo(packgename, 0).lastUpdateTime));
                    String finalupdatedate = "Last Updated : " + updatedate;
                    txtappupdatedate.setText(finalupdatedate);


                   // txtappdescription.setText(appinfo.);

                    Drawable icon = getContext().getPackageManager().getApplicationIcon(packgename);
                    imgappicon.setImageDrawable(icon);

                    mPopupWindow.showAtLocation(relmain, Gravity.CENTER, 0, 0);
                    View container = (View) mPopupWindow.getContentView().getParent();
                    WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                    WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
                    p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    p.dimAmount = 0.5f;
                    if (wm != null) {
                        wm.updateViewLayout(container, p);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });
        return rootView;
    }

    private List<AppList> getInstalledApps() {
        List<AppList> res = new ArrayList<>();
        PackageManager pm = Objects.requireNonNull(getActivity()).getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(0);

        Collections.sort(packs, new Comparator<PackageInfo>() {
            @Override
            public int compare(PackageInfo lhs, PackageInfo rhs) {
                return String.CASE_INSENSITIVE_ORDER.compare(lhs.applicationInfo.loadLabel(getActivity().getPackageManager()).toString(), rhs.applicationInfo.loadLabel(getActivity().getPackageManager()).toString());
            }
        });

        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!isSystemPackage(p))) {

                String appName = p.applicationInfo.loadLabel(Objects.requireNonNull(getActivity()).getPackageManager()).toString();
                String packageName = p.applicationInfo.packageName;
                String appVersion = "Version : " + p.versionName;
                Drawable icon = p.applicationInfo.loadIcon(getActivity().getPackageManager());
                res.add(new AppList(appName, packageName, appVersion, icon));
            }
        }
        return res;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != apptype;
    }
}