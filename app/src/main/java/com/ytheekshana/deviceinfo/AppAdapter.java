package com.ytheekshana.deviceinfo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<AppInfo> mDataSet;
    private int lastPosition = -1,uninstall_position=0;

    AppAdapter(Context context, ArrayList<AppInfo> list) {
        mContext = context;
        mDataSet = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_app_name;
        TextView txt_package_name;
        TextView txt_version_name;
        ImageView img_app_icon;

        ViewHolder(View view) {
            super(view);
            txt_app_name = view.findViewById(R.id.txt_app_name);
            txt_package_name = view.findViewById(R.id.txt_package_name);
            txt_version_name = view.findViewById(R.id.txt_version_name);
            img_app_icon = view.findViewById(R.id.img_app_icon);
        }
    }

    @NonNull
    @Override
    public AppAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.installed_app_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        uninstall_position = holder.getAdapterPosition();
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

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle(appName);
                menu.add("App Info").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + packageName));
                        context.startActivity(intent);
                        return true;
                    }
                });
                menu.add("Uninstall").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
                        intent.setData(Uri.parse("package:" + packageName));
                        context.startActivity(intent);
                        notifyItemRemoved(uninstall_position);
                        return true;
                    }
                });
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
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

}