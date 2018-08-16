package com.ytheekshana.deviceinfo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.content.ContextCompat;

public class MemoryInfo {
    private Activity activity;
    private Context context;
    private double totalRam, availableRam, usedRam, usedRamPercentage;
    private double totalRom, availableRom, usedRom, usedRomPercentage;
    private double totalInternalStorage, availableInternalStorage, usedInternalStorage, usedInternalPercentage;
    private double totalExternalStorage, availableExternalStorage, usedExternalStorage, usedExternalPercentage;

    MemoryInfo(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    public void Ram() {
        try {
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
            assert activityManager != null;
            activityManager.getMemoryInfo(mi);
            availableRam = (double) (mi.availMem / 1024 / 1024);
            totalRam = (double) (mi.totalMem / 1024 / 1024);
            usedRam = totalRam - availableRam;
            usedRamPercentage = (((usedRam) * 100) / totalRam);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void Rom() {
        try {
            StatFs stat = new StatFs(Environment.getRootDirectory().getAbsolutePath());
            availableRom = (((double) stat.getBlockSizeLong() * (double) stat.getAvailableBlocksLong()) / 1024 / 1024 / 1024);
            totalRom = ((double) stat.getBlockSizeLong() * (double) stat.getBlockCountLong()) / 1024 / 1024 / 1024;
            usedRom = totalRom - availableRom;
            usedRomPercentage = (double) ((((stat.getBlockSizeLong() * stat.getBlockCountLong()) - (stat.getBlockSizeLong() * stat.getAvailableBlocksLong())) * 100) / (stat.getBlockSizeLong() * stat.getBlockCountLong()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void InternalStorage() {
        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            availableInternalStorage = (((double) stat.getBlockSizeLong() * (double) stat.getAvailableBlocksLong()) / 1024 / 1024 / 1024);
            totalInternalStorage = ((double) stat.getBlockSizeLong() * (double) stat.getBlockCountLong()) / 1024 / 1024 / 1024;
            usedInternalStorage = totalInternalStorage - availableInternalStorage;
            usedInternalPercentage = (double) ((((stat.getBlockSizeLong() * stat.getBlockCountLong()) - (stat.getBlockSizeLong() * stat.getAvailableBlocksLong())) * 100) / (stat.getBlockSizeLong() * stat.getBlockCountLong()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void ExternalStorage() {
        try {
            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED) && ContextCompat.getExternalFilesDirs(context, null).length >= 2) {
                StatFs stat = new StatFs(GetDetails.getStorageDirectories(context)[0]);
                availableExternalStorage = (((double) stat.getBlockSizeLong() * (double) stat.getAvailableBlocksLong()) / 1024 / 1024 / 1024);
                totalExternalStorage = ((double) stat.getBlockSizeLong() * (double) stat.getBlockCountLong()) / 1024 / 1024 / 1024;
                usedExternalStorage = totalExternalStorage - availableExternalStorage;
                usedExternalPercentage = (double) ((((stat.getBlockSizeLong() * stat.getBlockCountLong()) - (stat.getBlockSizeLong() * stat.getAvailableBlocksLong())) * 100) / (stat.getBlockSizeLong() * stat.getBlockCountLong()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public double getTotalRam() {
        return totalRam;
    }

    public double getAvailableRam() {
        return availableRam;
    }

    public double getUsedRam() {
        return usedRam;
    }

    public double getUsedRamPercentage() {
        return usedRamPercentage;
    }

    public double getTotalRom() {
        return totalRom;
    }

    public double getAvailableRom() {
        return availableRom;
    }

    public double getUsedRom() {
        return usedRom;
    }

    public double getUsedRomPercentage() {
        return usedRomPercentage;
    }

    public double getTotalInternalStorage() {
        return totalInternalStorage;
    }

    public double getAvailableInternalStorage() {
        return availableInternalStorage;
    }

    public double getUsedInternalStorage() {
        return usedInternalStorage;
    }

    public double getUsedInternalPercentage() {
        return usedInternalPercentage;
    }

    public double getTotalExternalStorage() {
        return totalExternalStorage;
    }

    public double getAvailableExternalStorage() {
        return availableExternalStorage;
    }

    public double getUsedExternalStorage() {
        return usedExternalStorage;
    }

    public double getUsedExternalPercentage() {
        return usedExternalPercentage;
    }
}
