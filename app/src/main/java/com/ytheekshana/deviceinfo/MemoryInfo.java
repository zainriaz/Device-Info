package com.ytheekshana.deviceinfo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import androidx.core.content.ContextCompat;

class MemoryInfo {
    private Activity activity;
    private Context context;
    private double totalRam, availableRam, usedRam, usedRamPercentage;
    private double totalRom, availableRom, usedRom, usedRomPercentage;
    private String romPath, internalStoragePath, externalStoragePath;
    private double totalInternalStorage, availableInternalStorage, usedInternalStorage, usedInternalPercentage;
    private double totalExternalStorage, availableExternalStorage, usedExternalStorage, usedExternalPercentage;

    MemoryInfo(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    void Ram() {
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

    void Rom() {
        try {
            StatFs stat = new StatFs(Environment.getRootDirectory().getAbsolutePath());
            availableRom = (((double) stat.getBlockSizeLong() * (double) stat.getAvailableBlocksLong()) / 1024 / 1024 / 1024);
            totalRom = ((double) stat.getBlockSizeLong() * (double) stat.getBlockCountLong()) / 1024 / 1024 / 1024;
            usedRom = totalRom - availableRom;
            usedRomPercentage = (double) ((((stat.getBlockSizeLong() * stat.getBlockCountLong()) - (stat.getBlockSizeLong() * stat.getAvailableBlocksLong())) * 100) / (stat.getBlockSizeLong() * stat.getBlockCountLong()));
            romPath = Environment.getRootDirectory().getAbsolutePath();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void InternalStorage() {
        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            availableInternalStorage = (((double) stat.getBlockSizeLong() * (double) stat.getAvailableBlocksLong()) / 1024 / 1024 / 1024);
            totalInternalStorage = ((double) stat.getBlockSizeLong() * (double) stat.getBlockCountLong()) / 1024 / 1024 / 1024;
            usedInternalStorage = totalInternalStorage - availableInternalStorage;
            usedInternalPercentage = (double) ((((stat.getBlockSizeLong() * stat.getBlockCountLong()) - (stat.getBlockSizeLong() * stat.getAvailableBlocksLong())) * 100) / (stat.getBlockSizeLong() * stat.getBlockCountLong()));
            internalStoragePath = Environment.getExternalStorageDirectory().getPath();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void ExternalStorage() {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && ContextCompat.getExternalFilesDirs(context, null).length >= 2) {
                StatFs stat = new StatFs(GetDetails.getStorageDirectories(context)[0]);
                availableExternalStorage = (((double) stat.getBlockSizeLong() * (double) stat.getAvailableBlocksLong()) / 1024 / 1024 / 1024);
                totalExternalStorage = ((double) stat.getBlockSizeLong() * (double) stat.getBlockCountLong()) / 1024 / 1024 / 1024;
                usedExternalStorage = totalExternalStorage - availableExternalStorage;
                usedExternalPercentage = (double) ((((stat.getBlockSizeLong() * stat.getBlockCountLong()) - (stat.getBlockSizeLong() * stat.getAvailableBlocksLong())) * 100) / (stat.getBlockSizeLong() * stat.getBlockCountLong()));
                externalStoragePath = GetDetails.getStorageDirectories(context)[0];
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    String getRomPath() {
        return romPath;
    }

    String getInternalStoragePath() {
        return internalStoragePath;
    }

    String getExternalStoragePath() {
        return externalStoragePath;
    }

    double getTotalRam() {
        return totalRam;
    }

    double getAvailableRam() {
        return availableRam;
    }

    double getUsedRam() {
        return usedRam;
    }

    double getUsedRamPercentage() {
        return usedRamPercentage;
    }

    double getTotalRom() {
        return totalRom;
    }

    double getAvailableRom() {
        return availableRom;
    }

    double getUsedRom() {
        return usedRom;
    }

    double getUsedRomPercentage() {
        return usedRomPercentage;
    }

    double getTotalInternalStorage() {
        return totalInternalStorage;
    }

    double getAvailableInternalStorage() {
        return availableInternalStorage;
    }

    double getUsedInternalStorage() {
        return usedInternalStorage;
    }

    double getUsedInternalPercentage() {
        return usedInternalPercentage;
    }

    double getTotalExternalStorage() {
        return totalExternalStorage;
    }

    double getAvailableExternalStorage() {
        return availableExternalStorage;
    }

    double getUsedExternalStorage() {
        return usedExternalStorage;
    }

    double getUsedExternalPercentage() {
        return usedExternalPercentage;
    }
}
