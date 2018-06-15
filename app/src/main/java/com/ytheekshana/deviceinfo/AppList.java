package com.ytheekshana.deviceinfo;

import android.graphics.drawable.Drawable;

class AppList {

    private String AppName;
    private String VersionName;
    private String PackageName;
    private Drawable AppIcon;

    AppList(String AppName, String PackageName, String VersionName, Drawable AppIcon) {
        this.AppName = AppName;
        this.PackageName = PackageName;
        this.VersionName = VersionName;
        this.AppIcon = AppIcon;
    }

    String getAppName() {
        return AppName;
    }

    String getPackageName() {
        return PackageName;
    }

    String getVersionName() {
        return VersionName;
    }

    Drawable getIcon() {
        return AppIcon;
    }
}