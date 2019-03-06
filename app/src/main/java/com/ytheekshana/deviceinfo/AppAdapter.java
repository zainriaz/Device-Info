package com.ytheekshana.deviceinfo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    ArrayList<AppInfo> mDataSet;
    private ArrayList<AppInfo> filterMDataSet;
    private AppFilter filter;
    private int lastPosition = -1;

    AppAdapter(Context context, ArrayList<AppInfo> list) {
        mContext = context;
        mDataSet = list;
        filterMDataSet = list;
    }

    void addData(ArrayList<AppInfo> list) {
        mDataSet.clear();
        mDataSet.addAll(list);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_app_name;
        TextView txt_package_name;
        TextView txt_version_name;
        ImageView img_app_icon;
        ProgressBar progressApp;

        ViewHolder(View view) {
            super(view);
            txt_app_name = view.findViewById(R.id.txt_app_name);
            txt_package_name = view.findViewById(R.id.txt_package_name);
            txt_version_name = view.findViewById(R.id.txt_version_name);
            img_app_icon = view.findViewById(R.id.img_app_icon);
            progressApp = view.findViewById(R.id.progressApp);
        }
    }

    @NonNull
    @Override
    public AppAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.installed_app_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final Context context = holder.itemView.getContext();
        final int TextDisColor = MainActivity.themeColor;

        final String packageName = mDataSet.get(position).getPackageName();
        final Drawable appIcon = mDataSet.get(position).getAppIcon();
        final String appName = mDataSet.get(position).getAppName();
        final String versionName = mDataSet.get(position).getVersionName();

        holder.txt_app_name.setText(appName);
        holder.txt_package_name.setText(packageName);
        holder.txt_version_name.setText(versionName);
        holder.img_app_icon.setImageDrawable(appIcon);

        Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.recycler_up_from_bottom : R.anim.recycler_down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;

        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.setHeaderTitle(appName);
            menu.add("App Info").setOnMenuItemClickListener(item -> {
                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + packageName));
                context.startActivity(intent);
                return true;
            });
            menu.add("Extract App").setOnMenuItemClickListener(item -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Permissions.check(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, null, new PermissionHandler() {
                        @Override
                        public void onGranted() {
                            extractApp(context, packageName, holder.progressApp);
                        }

                        @Override
                        public void onDenied(Context context1, ArrayList<String> deniedPermissions) {
                            Toast.makeText(context1, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    extractApp(context, packageName, holder.progressApp);
                }
                return true;
            });
            menu.add("Uninstall").setOnMenuItemClickListener(item -> {
                Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
                intent.setData(Uri.parse("package:" + packageName));
                context.startActivity(intent);
                mDataSet.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), 1);
                return true;
            });
        });

        holder.itemView.setOnClickListener(v -> {

            try {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View customView = inflater != null ? inflater.inflate(R.layout.apps_popup, null) : null;
                RelativeLayout heading_layout = Objects.requireNonNull(customView).findViewById(R.id.heading_layout);
                heading_layout.setBackgroundColor(TextDisColor);

                TextView txtappname = customView.findViewById(R.id.txtappnameheading);
                TextView txtpackagename = customView.findViewById(R.id.txtpackagenameheading);
                TextView txtappversion = customView.findViewById(R.id.txtappversion);
                TextView txtappminsdk = customView.findViewById(R.id.txtappminsdk);
                TextView txtapptargetsdk = customView.findViewById(R.id.txtapptargetsdk);
                TextView txtappinstalldate = customView.findViewById(R.id.txtappinstalldate);
                TextView txtappupdatedate = customView.findViewById(R.id.txtappupdatedate);
                ImageView imgappicon = customView.findViewById(R.id.imgappicon);

                PopupWindow mPopupWindow = new PopupWindow(customView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                mPopupWindow.setElevation(5.0f);

                txtappname.setText(appName);
                txtpackagename.setText(packageName);
                txtappversion.setText(versionName);

                ApplicationInfo appinfo = context.getPackageManager().getApplicationInfo(packageName, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    int minsdk = appinfo.minSdkVersion;
                    String minsdkall = "Min : Android " + GetDetails.getAndroidVersion(minsdk) + " (" + GetDetails.GetOSName(minsdk) + ", API " + minsdk + ")";
                    txtappminsdk.setText(minsdkall);
                } else {
                    txtappminsdk.setVisibility(View.GONE);
                    txtappminsdk.setText(R.string.unknown_min_sdk);
                }
                int targetsdk = appinfo.targetSdkVersion;
                String targetsdkall = "Target : Android " + GetDetails.getAndroidVersion(targetsdk) + " (" + GetDetails.GetOSName(targetsdk) + ", API " + targetsdk + ")";
                txtapptargetsdk.setText(targetsdkall);

                String insdate = new SimpleDateFormat("EEE, d MMM yyyy", Locale.US).format(new Date(context.getPackageManager().getPackageInfo(packageName, 0).firstInstallTime));
                String finalinsdate = "Installed : " + insdate;
                txtappinstalldate.setText(finalinsdate);

                String updatedate = new SimpleDateFormat("EEE, d MMM yyyy", Locale.US).format(new Date(context.getPackageManager().getPackageInfo(packageName, 0).lastUpdateTime));
                String finalupdatedate = "Last Updated : " + updatedate;
                txtappupdatedate.setText(finalupdatedate);

                Drawable icon = context.getPackageManager().getApplicationIcon(packageName);
                imgappicon.setImageDrawable(icon);

                mPopupWindow.showAtLocation(customView, Gravity.CENTER, 0, 0);
                View container = (View) mPopupWindow.getContentView().getParent();
                WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
                p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                p.dimAmount = 0.5f;
                if (wm != null) {
                    wm.updateViewLayout(container, p);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    private void extractApp(final Context context, final String packageName, final ProgressBar progressApp) {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            final String getExtractpath = sharedPrefs.getString("extract_location", "/storage/emulated/0/DeviceInfo");
            ApplicationInfo appinfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            final File sourceFile = new File(appinfo.sourceDir);
            final File destinationFile = new File(getExtractpath);
            boolean success = true;
            if (!destinationFile.exists()) {
                success = destinationFile.mkdir();
            }
            if (success) {
                new Thread() {
                    @Override
                    public void run() {
                        progressApp.post(() -> progressApp.setVisibility(View.VISIBLE));
                        GetDetails.copy(sourceFile.getAbsoluteFile(), new File(destinationFile, packageName + ".apk"));
                        progressApp.post(() -> {
                            progressApp.setVisibility(View.GONE);
                            Toast.makeText(context, "Exported to " + getExtractpath + "/", Toast.LENGTH_SHORT).show();
                        });

                    }
                }.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new AppFilter(filterMDataSet, this);
        }
        return filter;
    }
}

