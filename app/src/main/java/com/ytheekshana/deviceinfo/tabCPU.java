package com.ytheekshana.deviceinfo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.RandomAccessFile;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class tabCPU extends Fragment {
    LinearLayout llayout;
    String LineColor = "#B3B3B3", TextDisColor = "#023071";
    TextView txtCPUUsagedis;
    CPUUsage cu;
    TextView txtCore[];
    String cUsage;
    Timer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabcpu, container, false);
        llayout = rootView.findViewById(R.id.llayout);
        cu = new CPUUsage();

        try {
            double cpuMaxFreq, cpuMinFreq;
            RandomAccessFile readermax, readermin;
            readermax = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "r");
            readermin = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq", "r");
            String maxfreq = readermax.readLine();
            String minfreq = readermin.readLine();
            cpuMaxFreq = Double.parseDouble(maxfreq) / 1000;
            cpuMinFreq = Double.parseDouble(minfreq) / 1000;
            readermax.close();
            readermin.close();

            TextView txtProcessor = new TextView(getContext());
            TextView txtProcessordis = new TextView(getContext());
            View v = new View(getContext());
            v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v.setBackgroundColor(Color.parseColor(LineColor));
            txtProcessor.setText(R.string.Processor);
            txtProcessor.setTypeface(null, Typeface.BOLD);
            txtProcessor.setTextSize(16);
            txtProcessordis.setPadding(0, 0, 0, 15);
            txtProcessordis.setTextColor(Color.parseColor(TextDisColor));
            txtProcessordis.setTextSize(16);
            txtProcessordis.setText(GetDetails.getProcessor());
            txtProcessordis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtProcessor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtProcessor);
            llayout.addView(txtProcessordis);
            llayout.addView(v);

            TextView txtABI = new TextView(getContext());
            TextView txtABIdis = new TextView(getContext());
            View v1 = new View(getContext());
            v1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v1.setBackgroundColor(Color.parseColor(LineColor));
            txtABI.setText(R.string.ABIs);
            txtABI.setTypeface(null, Typeface.BOLD);
            txtABI.setTextSize(16);
            txtABI.setPadding(0, 15, 0, 0);
            txtABIdis.setPadding(0, 0, 0, 15);
            txtABIdis.setTextColor(Color.parseColor(TextDisColor));
            txtABIdis.setTextSize(16);
            String ABIs = "";
            for (int a = 0; a < Build.SUPPORTED_ABIS.length; a++) {
                ABIs += Build.SUPPORTED_ABIS[a] + ", ";
            }
            txtABIdis.setText(ABIs.substring(0, ABIs.length() - 2));
            txtABIdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtABI.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtABI);
            llayout.addView(txtABIdis);
            llayout.addView(v1);

            TextView txtCPUHardware = new TextView(getContext());
            TextView txtCPUHardwaredis = new TextView(getContext());
            View v2 = new View(getContext());
            v2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v2.setBackgroundColor(Color.parseColor(LineColor));
            txtCPUHardware.setText(R.string.CPUHardware);
            txtCPUHardware.setTypeface(null, Typeface.BOLD);
            txtCPUHardware.setTextSize(16);
            txtCPUHardware.setPadding(0, 15, 0, 0);
            txtCPUHardwaredis.setPadding(0, 0, 0, 15);
            txtCPUHardwaredis.setTextColor(Color.parseColor(TextDisColor));
            txtCPUHardwaredis.setTextSize(16);
            txtCPUHardwaredis.setText(GetDetails.getProcessorHardware());
            txtCPUHardwaredis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtCPUHardware.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtCPUHardware);
            llayout.addView(txtCPUHardwaredis);
            llayout.addView(v2);

            TextView txtCPUGovernor = new TextView(getContext());
            TextView txtCPUGovernordis = new TextView(getContext());
            View v3 = new View(getContext());
            v3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v3.setBackgroundColor(Color.parseColor(LineColor));
            txtCPUGovernor.setText(R.string.CPUGoverner);
            txtCPUGovernor.setTypeface(null, Typeface.BOLD);
            txtCPUGovernor.setTextSize(16);
            txtCPUGovernor.setPadding(0, 15, 0, 0);
            txtCPUGovernordis.setPadding(0, 0, 0, 15);
            txtCPUGovernordis.setTextColor(Color.parseColor(TextDisColor));
            txtCPUGovernordis.setTextSize(16);
            txtCPUGovernordis.setText(GetDetails.getCPUGoverner());
            txtCPUGovernordis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtCPUGovernor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtCPUGovernor);
            llayout.addView(txtCPUGovernordis);
            llayout.addView(v3);

            TextView txtCPUCores = new TextView(getContext());
            final TextView txtCPUCoresdis = new TextView(getContext());
            View v4 = new View(getContext());
            v4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v4.setBackgroundColor(Color.parseColor(LineColor));
            txtCPUCores.setText(R.string.Cores);
            txtCPUCores.setTypeface(null, Typeface.BOLD);
            txtCPUCores.setTextSize(16);
            txtCPUCores.setPadding(0, 15, 0, 0);
            txtCPUCoresdis.setPadding(0, 0, 0, 15);
            txtCPUCoresdis.setTextColor(Color.parseColor(TextDisColor));
            txtCPUCoresdis.setTextSize(16);
            txtCPUCoresdis.setText(String.valueOf(Runtime.getRuntime().availableProcessors()));
            txtCPUCoresdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtCPUCores.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtCPUCores);
            llayout.addView(txtCPUCoresdis);
            llayout.addView(v4);

            TextView txtCPUFrequency = new TextView(getContext());
            TextView txtCPUFrequencydis = new TextView(getContext());
            View v5 = new View(getContext());
            v5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v5.setBackgroundColor(Color.parseColor(LineColor));
            txtCPUFrequency.setText(R.string.CPUFrequency);
            txtCPUFrequency.setTypeface(null, Typeface.BOLD);
            txtCPUFrequency.setTextSize(16);
            txtCPUFrequency.setPadding(0, 15, 0, 0);
            txtCPUFrequencydis.setPadding(0, 0, 0, 15);
            txtCPUFrequencydis.setTextColor(Color.parseColor(TextDisColor));
            txtCPUFrequencydis.setTextSize(16);
            String frequ = String.format(Locale.US, "%.0f", cpuMinFreq) + " MHz - " + String.format(Locale.US, "%.0f", cpuMaxFreq) + " MHz";
            txtCPUFrequencydis.setText(frequ);
            txtCPUFrequencydis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtCPUFrequency.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtCPUFrequency);
            llayout.addView(txtCPUFrequencydis);
            llayout.addView(v5);

            TextView txtRunningCPU = new TextView(getContext());
            View v6 = new View(getContext());
            v6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v6.setBackgroundColor(Color.parseColor(LineColor));
            txtRunningCPU.setText(R.string.RunningCPUs);
            txtRunningCPU.setTypeface(null, Typeface.BOLD);
            txtRunningCPU.setTextSize(16);
            txtRunningCPU.setPadding(0, 15, 0, 0);
            txtRunningCPU.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtRunningCPU);

            txtCore = new TextView[Runtime.getRuntime().availableProcessors()];
            for (int corecount = 0; corecount < Runtime.getRuntime().availableProcessors(); corecount++) {
                txtCore[corecount] = new TextView(getContext());
                txtCore[corecount].setPadding(0, 0, 0, 15);
                txtCore[corecount].setTextColor(Color.parseColor(TextDisColor));
                txtCore[corecount].setTextSize(16);
                txtCore[corecount].setText("Core " + corecount + "       ");
                txtCore[corecount].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                llayout.addView(txtCore[corecount]);
            }
            llayout.addView(v6);


            TextView txtCPUUsage = new TextView(getContext());
            txtCPUUsagedis = new TextView(getContext());
            View v7 = new View(getContext());
            v7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v7.setBackgroundColor(Color.parseColor(LineColor));
            txtCPUUsage.setText(R.string.CPUUsage);
            txtCPUUsage.setTypeface(null, Typeface.BOLD);
            txtCPUUsage.setTextSize(16);
            txtCPUUsage.setPadding(0, 15, 0, 0);
            txtCPUUsagedis.setPadding(0, 0, 0, 15);
            txtCPUUsagedis.setTextColor(Color.parseColor(TextDisColor));
            txtCPUUsagedis.setTextSize(16);
            txtCPUUsagedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtCPUUsage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtCPUUsage);
            llayout.addView(txtCPUUsagedis);
            llayout.addView(v7);

            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    cUsage = String.valueOf(cu.getTotalCpuUsage()) + " %";
                    txtCPUUsagedis.post(new Runnable() {
                        @Override
                        public void run() {
                            txtCPUUsagedis.setText(cUsage);
                        }
                    });

                }
            }, 0, 1000);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            timer.cancel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}