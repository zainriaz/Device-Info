package com.ytheekshana.deviceinfo;

import android.graphics.drawable.Drawable;

class AppInfo {

    private String appName;
    private String versionName;
    private String packageName;
    private Drawable appIcon;

    AppInfo(String appName, String packageName, String versionName, Drawable appIcon) {
        this.appName = appName;
        this.packageName = packageName;
        this.versionName = versionName;
        this.appIcon = appIcon;
    }

    String getAppName() {
        return appName;
    }

    String getPackageName() {
        return packageName;
    }

    String getVersionName() {
        return versionName;
    }

    Drawable getAppIcon() {
        return appIcon;
    }
}