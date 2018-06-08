package com.ytheekshana.deviceinfo;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

public class tabMemory extends Fragment {
    LinearLayout llayout;
    String LineColor = "#B3B3B3", TextDisColor = "#023071";
    TextView txtRamTotaldis, txtRamFreedis, txtRamUseddis;
    Double ARam, URam, TRam, FreeSto, TotalSto, UsedSto, UsedPerc;
    ProgressBar pram, prom, pinternal, pexternal;
    int UPerc;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabmemory, container, false);
        llayout = rootView.findViewById(R.id.llayout);
        try {
            TextView txtRAM = new TextView(getContext());
            txtRamFreedis = new TextView(getContext());
            txtRamUseddis = new TextView(getContext());
            txtRamTotaldis = new TextView(getContext());
            pram = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
            pram.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            View v = new View(getContext());
            v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v.setBackgroundColor(Color.parseColor(LineColor));
            txtRAM.setText(R.string.RAM);
            txtRAM.setTypeface(null, Typeface.BOLD);
            txtRAM.setTextSize(16);
            txtRAM.setPadding(0, 0, 0, 25);
            txtRamFreedis.setPadding(0, 0, 0, 15);
            txtRamFreedis.setTextColor(Color.parseColor(TextDisColor));
            txtRamFreedis.setTextSize(16);
            txtRamFreedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            txtRamUseddis.setPadding(0, 0, 0, 15);
            txtRamUseddis.setTextColor(Color.parseColor(TextDisColor));
            txtRamUseddis.setTextSize(16);
            txtRamUseddis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            txtRamTotaldis.setPadding(0, 0, 0, 15);
            txtRamTotaldis.setTextColor(Color.parseColor(TextDisColor));
            txtRamTotaldis.setTextSize(16);
            txtRamTotaldis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            txtRAM.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtRAM);
            llayout.addView(txtRamFreedis);
            llayout.addView(txtRamUseddis);
            llayout.addView(txtRamTotaldis);
            llayout.addView(pram);
            llayout.addView(v);

            final Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
                        ActivityManager activityManager = (ActivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.ACTIVITY_SERVICE);
                        if (activityManager != null) {
                            activityManager.getMemoryInfo(mi);
                        }
                        ARam = (double) (mi.availMem / 1024 / 1024);
                        TRam = (double) (mi.totalMem / 1024 / 1024);
                        URam = TRam - ARam;
                        UPerc = (int) (((URam) * 100) / TRam);
                        String TRAM = "Total\t\t\t\t\t" + Double.toString(TRam) + " MB";
                        String ARAM = "Free\t\t\t\t\t\t" + Double.toString(ARam) + " MB";
                        String URAM = "Used\t\t\t\t\t" + Double.toString(URam) + " MB";
                        txtRamTotaldis.setText(TRAM);
                        txtRamUseddis.setText(URAM);
                        txtRamFreedis.setText(ARAM);
                        pram.setProgress(UPerc);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    h.postDelayed(this, 1000);
                }
            }, 0);

            TextView txtROM = new TextView(getContext());
            TextView txtROMFreedis = new TextView(getContext());
            TextView txtROMUseddis = new TextView(getContext());
            TextView txtROMTotaldis = new TextView(getContext());
            prom = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
            prom.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            View v1 = new View(getContext());
            v1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v1.setBackgroundColor(Color.parseColor(LineColor));
            txtROM.setText(R.string.DashROM);
            txtROM.setTypeface(null, Typeface.BOLD);
            txtROM.setTextSize(16);
            txtROM.setPadding(0, 20, 0, 25);
            txtROMFreedis.setPadding(0, 0, 0, 15);
            txtROMFreedis.setTextColor(Color.parseColor(TextDisColor));
            txtROMFreedis.setTextSize(16);
            txtROMFreedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            txtROMUseddis.setPadding(0, 0, 0, 15);
            txtROMUseddis.setTextColor(Color.parseColor(TextDisColor));
            txtROMUseddis.setTextSize(16);
            txtROMUseddis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            txtROMTotaldis.setPadding(0, 0, 0, 15);
            txtROMTotaldis.setTextColor(Color.parseColor(TextDisColor));
            txtROMTotaldis.setTextSize(16);
            txtROMTotaldis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            txtROM.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtROM);
            llayout.addView(txtROMFreedis);
            llayout.addView(txtROMUseddis);
            llayout.addView(txtROMTotaldis);
            llayout.addView(prom);
            llayout.addView(v1);

            StatFs statROM = new StatFs(Environment.getRootDirectory().getAbsolutePath());
            FreeSto = (((double) statROM.getBlockSizeLong() * (double) statROM.getAvailableBlocksLong()) / 1024 / 1024 / 1024);
            TotalSto = ((double) statROM.getBlockSizeLong() * (double) statROM.getBlockCountLong()) / 1024 / 1024 / 1024;
            UsedSto = TotalSto - FreeSto;
            UsedPerc = ((UsedSto * 100) / TotalSto);

            String TROM = "Total\t\t\t\t\t" + String.format(Locale.US, "%.2f", TotalSto) + " GB";
            String AROM = "Free\t\t\t\t\t\t" + String.format(Locale.US, "%.2f", FreeSto) + " GB";
            String UROM = "Used\t\t\t\t\t" + String.format(Locale.US, "%.2f", UsedSto) + " GB";
            txtROMTotaldis.setText(TROM);
            txtROMUseddis.setText(UROM);
            txtROMFreedis.setText(AROM);
            prom.setProgress(UsedPerc.intValue());

            TextView txtInternalStorage = new TextView(getContext());
            TextView txtINFreedis = new TextView(getContext());
            TextView txtINUseddis = new TextView(getContext());
            TextView txtINTotaldis = new TextView(getContext());
            pinternal = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
            pinternal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            View v2 = new View(getContext());
            v2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v2.setBackgroundColor(Color.parseColor(LineColor));
            txtInternalStorage.setText(R.string.InternalStorage);
            txtInternalStorage.setTypeface(null, Typeface.BOLD);
            txtInternalStorage.setTextSize(16);
            txtInternalStorage.setPadding(0, 20, 0, 25);
            txtINFreedis.setPadding(0, 0, 0, 15);
            txtINFreedis.setTextColor(Color.parseColor(TextDisColor));
            txtINFreedis.setTextSize(16);
            txtINFreedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            txtINUseddis.setPadding(0, 0, 0, 15);
            txtINUseddis.setTextColor(Color.parseColor(TextDisColor));
            txtINUseddis.setTextSize(16);
            txtINUseddis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            txtINTotaldis.setPadding(0, 0, 0, 15);
            txtINTotaldis.setTextColor(Color.parseColor(TextDisColor));
            txtINTotaldis.setTextSize(16);
            txtINTotaldis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            txtInternalStorage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtInternalStorage);
            llayout.addView(txtINFreedis);
            llayout.addView(txtINUseddis);
            llayout.addView(txtINTotaldis);
            llayout.addView(pinternal);
            llayout.addView(v2);

            StatFs statIN = new StatFs(Environment.getExternalStorageDirectory().getPath());
            FreeSto = (((double) statIN.getBlockSizeLong() * (double) statIN.getAvailableBlocksLong()) / 1024 / 1024 / 1024);
            TotalSto = ((double) statIN.getBlockSizeLong() * (double) statIN.getBlockCountLong()) / 1024 / 1024 / 1024;
            UsedSto = TotalSto - FreeSto;
            UsedPerc = ((UsedSto * 100) / TotalSto);

            String TINS = "Total\t\t\t\t\t" + String.format(Locale.US, "%.2f", TotalSto) + " GB";
            String AINS = "Free\t\t\t\t\t\t" + String.format(Locale.US, "%.2f", FreeSto) + " GB";
            String UINS = "Used\t\t\t\t\t" + String.format(Locale.US, "%.2f", UsedSto) + " GB";
            txtINTotaldis.setText(TINS);
            txtINUseddis.setText(UINS);
            txtINFreedis.setText(AINS);
            pinternal.setProgress(UsedPerc.intValue());

            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                TextView txtExternalStorage = new TextView(getContext());
                TextView txtEXFreedis = new TextView(getContext());
                TextView txtEXUseddis = new TextView(getContext());
                TextView txtEXTotaldis = new TextView(getContext());
                pexternal = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
                pexternal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                View v3 = new View(getContext());
                v3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
                v3.setBackgroundColor(Color.parseColor(LineColor));
                txtExternalStorage.setText(R.string.ExternalStorage);
                txtExternalStorage.setTypeface(null, Typeface.BOLD);
                txtExternalStorage.setTextSize(16);
                txtExternalStorage.setPadding(0, 20, 0, 25);
                txtEXFreedis.setPadding(0, 0, 0, 15);
                txtEXFreedis.setTextColor(Color.parseColor(TextDisColor));
                txtEXFreedis.setTextSize(16);
                txtEXFreedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                txtEXUseddis.setPadding(0, 0, 0, 15);
                txtEXUseddis.setTextColor(Color.parseColor(TextDisColor));
                txtEXUseddis.setTextSize(16);
                txtEXUseddis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                txtEXTotaldis.setPadding(0, 0, 0, 15);
                txtEXTotaldis.setTextColor(Color.parseColor(TextDisColor));
                txtEXTotaldis.setTextSize(16);
                txtEXTotaldis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                txtExternalStorage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                llayout.addView(txtExternalStorage);
                llayout.addView(txtEXFreedis);
                llayout.addView(txtEXUseddis);
                llayout.addView(txtEXTotaldis);
                llayout.addView(pexternal);
                llayout.addView(v3);

                String paths[] = GetDetails.getStorageDirectories(Objects.requireNonNull(getContext()));

                StatFs statEX = new StatFs(paths[0]);
                FreeSto = (((double) statEX.getBlockSizeLong() * (double) statEX.getAvailableBlocksLong()) / 1024 / 1024 / 1024);
                TotalSto = ((double) statEX.getBlockSizeLong() * (double) statEX.getBlockCountLong()) / 1024 / 1024 / 1024;
                UsedSto = TotalSto - FreeSto;
                UsedPerc = ((UsedSto * 100) / TotalSto);

                String TEXS = "Total\t\t\t\t\t" + String.format(Locale.US, "%.2f", TotalSto) + " GB";
                String AEXS = "Free\t\t\t\t\t\t" + String.format(Locale.US, "%.2f", FreeSto) + " GB";
                String UEXS = "Used\t\t\t\t\t" + String.format(Locale.US, "%.2f", UsedSto) + " GB";
                txtEXTotaldis.setText(TEXS);
                txtEXUseddis.setText(UEXS);
                txtEXFreedis.setText(AEXS);
                pexternal.setProgress(UsedPerc.intValue());
                //pexternal.setProgressTintList(ColorStateList.valueOf(Color.RED));

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rootView;
    }
}
