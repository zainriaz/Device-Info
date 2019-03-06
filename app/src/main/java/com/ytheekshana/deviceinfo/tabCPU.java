package com.ytheekshana.deviceinfo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.RandomAccessFile;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class tabCPU extends Fragment {
    private TextView txtCPUUsagedis;
    private CPUUsage cu;
    private TextView txtCore[];
    private String cUsage;
    private Timer timer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabcpu, container, false);
        LinearLayout llayout = rootView.findViewById(R.id.llayout);
        cu = new CPUUsage();

        try {
            int textDisColor = MainActivity.themeColor;
            int lineColor = GetDetails.getThemeColor(Objects.requireNonNull(getContext()), R.attr.colorButtonNormal);

            TextView txtProcessor = new TextView(getContext());
            TextView txtProcessordis = new TextView(getContext());
            View v = new View(getContext());
            v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v.setBackgroundColor(lineColor);
            txtProcessor.setText(R.string.Processor);
            txtProcessor.setTypeface(null, Typeface.BOLD);
            txtProcessor.setTextSize(16);
            txtProcessordis.setPadding(0, 0, 0, 15);
            txtProcessordis.setTextColor(textDisColor);
            txtProcessordis.setTextSize(16);
            txtProcessordis.setText(SplashActivity.processorName);
            txtProcessordis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtProcessor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtProcessor);
            llayout.addView(txtProcessordis);
            llayout.addView(v);

            TextView txtABI = new TextView(getContext());
            TextView txtABIdis = new TextView(getContext());
            View v1 = new View(getContext());
            v1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v1.setBackgroundColor(lineColor);
            txtABI.setText(R.string.ABIs);
            txtABI.setTypeface(null, Typeface.BOLD);
            txtABI.setTextSize(16);
            txtABI.setPadding(0, 15, 0, 0);
            txtABIdis.setPadding(0, 0, 0, 15);
            txtABIdis.setTextColor(textDisColor);
            txtABIdis.setTextSize(16);
            txtABIdis.setText(SplashActivity.cpuABIs);
            txtABIdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtABI.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtABI);
            llayout.addView(txtABIdis);
            llayout.addView(v1);

            TextView txtCPUHardware = new TextView(getContext());
            TextView txtCPUHardwaredis = new TextView(getContext());
            View v2 = new View(getContext());
            v2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v2.setBackgroundColor(lineColor);
            txtCPUHardware.setText(R.string.CPUHardware);
            txtCPUHardware.setTypeface(null, Typeface.BOLD);
            txtCPUHardware.setTextSize(16);
            txtCPUHardware.setPadding(0, 15, 0, 0);
            txtCPUHardwaredis.setPadding(0, 0, 0, 15);
            txtCPUHardwaredis.setTextColor(textDisColor);
            txtCPUHardwaredis.setTextSize(16);
            txtCPUHardwaredis.setText(SplashActivity.processorHardware);
            txtCPUHardwaredis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtCPUHardware.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtCPUHardware);
            llayout.addView(txtCPUHardwaredis);
            llayout.addView(v2);

            TextView txtCPUGovernor = new TextView(getContext());
            TextView txtCPUGovernordis = new TextView(getContext());
            View v3 = new View(getContext());
            v3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v3.setBackgroundColor(lineColor);
            txtCPUGovernor.setText(R.string.CPUGovernor);
            txtCPUGovernor.setTypeface(null, Typeface.BOLD);
            txtCPUGovernor.setTextSize(16);
            txtCPUGovernor.setPadding(0, 15, 0, 0);
            txtCPUGovernordis.setPadding(0, 0, 0, 15);
            txtCPUGovernordis.setTextColor(textDisColor);
            txtCPUGovernordis.setTextSize(16);
            txtCPUGovernordis.setText(SplashActivity.cpuGovernor);
            txtCPUGovernordis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtCPUGovernor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtCPUGovernor);
            llayout.addView(txtCPUGovernordis);
            llayout.addView(v3);

            TextView txtCPUCores = new TextView(getContext());
            final TextView txtCPUCoresdis = new TextView(getContext());
            View v4 = new View(getContext());
            v4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v4.setBackgroundColor(lineColor);
            txtCPUCores.setText(R.string.Cores);
            txtCPUCores.setTypeface(null, Typeface.BOLD);
            txtCPUCores.setTextSize(16);
            txtCPUCores.setPadding(0, 15, 0, 0);
            txtCPUCoresdis.setPadding(0, 0, 0, 15);
            txtCPUCoresdis.setTextColor(textDisColor);
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
            v5.setBackgroundColor(lineColor);
            txtCPUFrequency.setText(R.string.CPUFrequency);
            txtCPUFrequency.setTypeface(null, Typeface.BOLD);
            txtCPUFrequency.setTextSize(16);
            txtCPUFrequency.setPadding(0, 15, 0, 0);
            txtCPUFrequencydis.setPadding(0, 0, 0, 15);
            txtCPUFrequencydis.setTextColor(textDisColor);
            txtCPUFrequencydis.setTextSize(16);
            String frequ = String.format(Locale.US, "%.0f", SplashActivity.cpuMinFreq) + " MHz - " + String.format(Locale.US, "%.0f", SplashActivity.cpuMaxFreq) + " MHz";
            txtCPUFrequencydis.setText(frequ);
            txtCPUFrequencydis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtCPUFrequency.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtCPUFrequency);
            llayout.addView(txtCPUFrequencydis);
            llayout.addView(v5);

            TextView txtRunningCPU = new TextView(getContext());
            View v6 = new View(getContext());
            v6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v6.setBackgroundColor(lineColor);
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
                txtCore[corecount].setTextColor(textDisColor);
                txtCore[corecount].setTextSize(16);
                txtCore[corecount].setText(String.valueOf(corecount));
                txtCore[corecount].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                llayout.addView(txtCore[corecount]);
            }
            llayout.addView(v6);


            TextView txtCPUUsage = new TextView(getContext());
            txtCPUUsagedis = new TextView(getContext());
            View v7 = new View(getContext());
            v7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v7.setBackgroundColor(lineColor);
            txtCPUUsage.setText(R.string.CPUUsage);
            txtCPUUsage.setTypeface(null, Typeface.BOLD);
            txtCPUUsage.setTextSize(16);
            txtCPUUsage.setPadding(0, 15, 0, 0);
            txtCPUUsagedis.setPadding(0, 0, 0, 15);
            txtCPUUsagedis.setTextColor(textDisColor);
            txtCPUUsagedis.setTextSize(16);
            txtCPUUsagedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtCPUUsage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtCPUUsage);
            llayout.addView(txtCPUUsagedis);
            llayout.addView(v7);

            TextView txtGPURenderer = new TextView(getContext());
            TextView txtGPURendererdis = new TextView(getContext());
            View v8 = new View(getContext());
            v8.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v8.setBackgroundColor(lineColor);
            txtGPURenderer.setText(R.string.GPURenderer);
            txtGPURenderer.setTypeface(null, Typeface.BOLD);
            txtGPURenderer.setTextSize(16);
            txtGPURenderer.setPadding(0, 15, 0, 0);
            txtGPURendererdis.setPadding(0, 0, 0, 15);
            txtGPURendererdis.setTextColor(textDisColor);
            txtGPURendererdis.setTextSize(16);
            txtGPURendererdis.setText(SplashActivity.gpuRenderer);
            txtGPURendererdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtGPURenderer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtGPURenderer);
            llayout.addView(txtGPURendererdis);
            llayout.addView(v8);

            TextView txtGPUVendor = new TextView(getContext());
            TextView txtGPUVendordis = new TextView(getContext());
            View v9 = new View(getContext());
            v9.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v9.setBackgroundColor(lineColor);
            txtGPUVendor.setText(R.string.GPUVendor);
            txtGPUVendor.setTypeface(null, Typeface.BOLD);
            txtGPUVendor.setTextSize(16);
            txtGPUVendor.setPadding(0, 15, 0, 0);
            txtGPUVendordis.setPadding(0, 0, 0, 15);
            txtGPUVendordis.setTextColor(textDisColor);
            txtGPUVendordis.setTextSize(16);
            txtGPUVendordis.setText(SplashActivity.gpuVendor);
            txtGPUVendordis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtGPUVendor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtGPUVendor);
            llayout.addView(txtGPUVendordis);
            llayout.addView(v9);

            TextView txtGPUVersion = new TextView(getContext());
            TextView txtGPUVersiondis = new TextView(getContext());
            View v10 = new View(getContext());
            v10.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v10.setBackgroundColor(lineColor);
            txtGPUVersion.setText(R.string.GPUVersion);
            txtGPUVersion.setTypeface(null, Typeface.BOLD);
            txtGPUVersion.setTextSize(16);
            txtGPUVersion.setPadding(0, 15, 0, 0);
            txtGPUVersiondis.setPadding(0, 0, 0, 15);
            txtGPUVersiondis.setTextColor(textDisColor);
            txtGPUVersiondis.setTextSize(16);
            txtGPUVersiondis.setText(SplashActivity.gpuVersion);
            txtGPUVersiondis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtGPUVersion.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtGPUVersion);
            llayout.addView(txtGPUVersiondis);
            llayout.addView(v10);

            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    cUsage = String.valueOf(cu.getTotalCpuUsage()) + " %";

                    for (int corecount = 0; corecount < Runtime.getRuntime().availableProcessors(); corecount++) {
                        try {
                            double currentFreq;
                            RandomAccessFile readerCurFreq;
                            readerCurFreq = new RandomAccessFile("/sys/devices/system/cpu/cpu" + corecount + "/cpufreq/scaling_cur_freq", "r");
                            String curfreg = readerCurFreq.readLine();
                            currentFreq = Double.parseDouble(curfreg) / 1000;
                            readerCurFreq.close();
                            final String settextcorecores = "\t\tCore " + corecount + "       " + (int) currentFreq + " Mhz";
                            final int finalCorecount1 = corecount;
                            txtCore[corecount].post(() -> txtCore[finalCorecount1].setText(settextcorecores));

                        } catch (Exception ex) {
                            final String settextcorecoresEX = "\t\tCore " + corecount + "       " + "Idle";
                            final int finalCorecount = corecount;
                            txtCore[corecount].post(() -> txtCore[finalCorecount].setText(settextcorecoresEX));
                        }
                    }
                    txtCPUUsagedis.post(() -> txtCPUUsagedis.setText(cUsage));

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