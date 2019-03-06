package com.ytheekshana.deviceinfo;


import androidx.annotation.NonNull;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class CPUUsage {
    private static final String TAG = "CpuUsage";
    private RandomAccessFile statFile;
    private CpuInfo mCpuInfoTotal;
    private ArrayList<CpuInfo> mCpuInfoList;

    CPUUsage() {
    }

    private void update() {
        try {
            createFile();
            parseFile();
            closeFile();
        } catch (FileNotFoundException e) {
            statFile = null;
            Log.e(TAG, "cannot open /proc/stat: " + e);
        } catch (IOException e) {
            Log.e(TAG, "cannot close /proc/stat: " + e);
        }
    }

    private void createFile() throws FileNotFoundException {
        statFile = new RandomAccessFile("/proc/stat", "r");
    }

    private void closeFile() throws IOException {
        if (statFile != null)
            statFile.close();
    }

    private void parseFile() {
        if (statFile != null) {
            try {
                statFile.seek(0);
                String cpuLine;
                int cpuId = -1;
                do {
                    cpuLine = statFile.readLine();
                    parseCpuLine(cpuId, cpuLine);
                    cpuId++;
                } while (cpuLine != null);
            } catch (IOException e) {
                Log.e(TAG, "Ops: " + e);
            }
        }
    }

    private void parseCpuLine(int cpuId, String cpuLine) {
        if (cpuLine != null && cpuLine.length() > 0) {
            String[] parts = cpuLine.split("[ ]+");
            String cpuLabel = "cpu";
            if (parts[0].contains(cpuLabel)) {
                createCpuInfo(cpuId, parts);
            }
        } else {
            Log.e(TAG, "unable to get cpu line");
        }
    }

    private void createCpuInfo(int cpuId, String[] parts) {
        if (cpuId == -1) {
            if (mCpuInfoTotal == null)
                mCpuInfoTotal = new CpuInfo();
            mCpuInfoTotal.update(parts);
        } else {
            if (mCpuInfoList == null)
                mCpuInfoList = new ArrayList<>();
            if (cpuId < mCpuInfoList.size())
                mCpuInfoList.get(cpuId).update(parts);
            else {
                CpuInfo info = new CpuInfo();
                info.update(parts);
                mCpuInfoList.add(info);
            }
        }
    }

    int getTotalCpuUsage() {
        update();
        int usage = 0;
        if (mCpuInfoTotal != null)
            usage = mCpuInfoTotal.getUsage();
        return usage;
    }


    @NonNull
    public String toString() {
        update();
        StringBuilder buf = new StringBuilder();
        if (mCpuInfoTotal != null) {
            buf.append("Cpu Total : ");
            buf.append(mCpuInfoTotal.getUsage());
            buf.append("%");
        }
        if (mCpuInfoList != null) {
            for (int i = 0; i < mCpuInfoList.size(); i++) {
                CpuInfo info = mCpuInfoList.get(i);
                buf.append(" Cpu Core(").append(i).append(") : ");
                buf.append(info.getUsage());
                buf.append("%");
                info.getUsage();
            }
        }
        return buf.toString();
    }

    private class CpuInfo {
        private int mUsage;
        private long mLastTotal;
        private long mLastIdle;

        CpuInfo() {
            mUsage = 0;
            mLastTotal = 0;
            mLastIdle = 0;
        }

        private int getUsage() {
            return mUsage;
        }

        void update(String[] parts) {
            long idle = Long.parseLong(parts[4], 10);
            long total = 0;
            boolean head = true;
            for (String part : parts) {
                if (head) {
                    head = false;
                    continue;
                }
                total += Long.parseLong(part, 10);
            }
            long diffIdle = idle - mLastIdle;
            long diffTotal = total - mLastTotal;
            mUsage = (int) ((float) (diffTotal - diffIdle) / diffTotal * 100);
            mLastTotal = total;
            mLastIdle = idle;
            Log.i(TAG, "CPU total=" + total + "; idle=" + idle + "; usage=" + mUsage);
        }
    }
}

