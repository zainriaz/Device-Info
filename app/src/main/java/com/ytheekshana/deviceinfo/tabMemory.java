package com.ytheekshana.deviceinfo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.Locale;

public class tabMemory extends Fragment {
    private Context context;
    int TextDisColor, LineColor;
    TextView txtRamTotal, txtRamFree, txtRamUsed, txtRomTotal, txtRomFree, txtRomUsed,
            txtInStorageTotal, txtInStorageFree, txtInStorageUsed,
            txtExStorageTotal, txtExStorageFree, txtExStorageUsed;
    DonutProgress progressRam, progressRom, progressInStorage, progressExStorage;
    CardView cardExStorage;
    ImageView imgRam, imgRom, imgInSto, imgExSto;
    RelativeLayout rlayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabmemory, container, false);

        TextDisColor = MainActivity.themeColor;
        LineColor = GetDetails.getThemeColor(context, R.attr.colorButtonNormal);
        try {

            rlayout = rootView.findViewById(R.id.rlayout);
            txtRamTotal = rootView.findViewById(R.id.txtRamTotal);
            txtRamTotal.setTextColor(TextDisColor);
            txtRamFree = rootView.findViewById(R.id.txtRamFree);
            txtRamFree.setTextColor(TextDisColor);
            txtRamUsed = rootView.findViewById(R.id.txtRamUsed);
            txtRamUsed.setTextColor(TextDisColor);
            progressRam = rootView.findViewById(R.id.progressRam);

            txtRomTotal = rootView.findViewById(R.id.txtRomTotal);
            txtRomTotal.setTextColor(TextDisColor);
            txtRomFree = rootView.findViewById(R.id.txtRomFree);
            txtRomFree.setTextColor(TextDisColor);
            txtRomUsed = rootView.findViewById(R.id.txtRomUsed);
            txtRomUsed.setTextColor(TextDisColor);
            progressRom = rootView.findViewById(R.id.progressRom);

            txtInStorageTotal = rootView.findViewById(R.id.txtInStorageTotal);
            txtInStorageTotal.setTextColor(TextDisColor);
            txtInStorageFree = rootView.findViewById(R.id.txtInStorageFree);
            txtInStorageFree.setTextColor(TextDisColor);
            txtInStorageUsed = rootView.findViewById(R.id.txtInStorageUsed);
            txtInStorageUsed.setTextColor(TextDisColor);
            progressInStorage = rootView.findViewById(R.id.progressInStorage);

            txtExStorageTotal = rootView.findViewById(R.id.txtExStorageTotal);
            txtExStorageTotal.setTextColor(TextDisColor);
            txtExStorageFree = rootView.findViewById(R.id.txtExStorageFree);
            txtExStorageFree.setTextColor(TextDisColor);
            txtExStorageUsed = rootView.findViewById(R.id.txtExStorageUsed);
            txtExStorageUsed.setTextColor(TextDisColor);
            progressExStorage = rootView.findViewById(R.id.progressExStorage);
            cardExStorage = rootView.findViewById(R.id.cardExStorageInfo);

            imgRam = rootView.findViewById(R.id.imageRam);
            imgRom = rootView.findViewById(R.id.imageRom);
            imgInSto = rootView.findViewById(R.id.imageInStorage);
            imgExSto = rootView.findViewById(R.id.imageExStorage);

            ColorFilter clfRam = new LightingColorFilter(Color.BLACK, getResources().getColor(R.color.progress_ram));
            imgRam.setColorFilter(clfRam);
            ColorFilter clfRom = new LightingColorFilter(Color.BLACK, getResources().getColor(R.color.progress_rom));
            imgRom.setColorFilter(clfRom);
            ColorFilter clfInSto = new LightingColorFilter(Color.BLACK, getResources().getColor(R.color.progress_insto));
            imgInSto.setColorFilter(clfInSto);
            ColorFilter clfExSto = new LightingColorFilter(Color.BLACK, getResources().getColor(R.color.progress_exsto));
            imgExSto.setColorFilter(clfExSto);

            final com.ytheekshana.deviceinfo.BounceInterpolator bounceInterpolator = new com.ytheekshana.deviceinfo.BounceInterpolator(0.2, 20);

            imgRam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animRam = AnimationUtils.loadAnimation(context, R.anim.bounce);
                    animRam.setInterpolator(bounceInterpolator);
                    imgRam.startAnimation(animRam);
                }
            });
            imgRom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animRom = AnimationUtils.loadAnimation(context, R.anim.bounce);
                    animRom.setInterpolator(bounceInterpolator);
                    imgRom.startAnimation(animRom);
                }
            });
            imgInSto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animInSto = AnimationUtils.loadAnimation(context, R.anim.bounce);
                    animInSto.setInterpolator(bounceInterpolator);
                    imgInSto.startAnimation(animInSto);
                }
            });
            imgExSto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animExSto = AnimationUtils.loadAnimation(context, R.anim.bounce);
                    animExSto.setInterpolator(bounceInterpolator);
                    imgExSto.startAnimation(animExSto);
                }
            });

            final MemoryInfo memoryInfo = new MemoryInfo(getActivity(), getContext());
            final Handler updateRam = new Handler();
            Runnable runnable = new Runnable() {
                public void run() {
                    memoryInfo.Ram();
                    String TRAM = "Total\t\t\t\t\t" + Double.toString(memoryInfo.getTotalRam()) + " MB";
                    String ARAM = "Free\t\t\t\t\t" + Double.toString(memoryInfo.getAvailableRam()) + " MB";
                    String URAM = "Used\t\t\t\t\t" + Double.toString(memoryInfo.getUsedRam()) + " MB";
                    txtRamTotal.setText(TRAM);
                    txtRamUsed.setText(URAM);
                    txtRamFree.setText(ARAM);
                    progressRam.setProgress((int) memoryInfo.getUsedRamPercentage());
                    updateRam.postDelayed(this, 1000);
                }
            };
            updateRam.postDelayed(runnable, 0);

            String TROM = "Total\t\t\t\t\t" + String.format(Locale.US, "%.2f", SplashActivity.totalRom) + " GB";
            String AROM = "Free\t\t\t\t\t" + String.format(Locale.US, "%.2f", SplashActivity.availableRom) + " GB";
            String UROM = "Used\t\t\t\t\t" + String.format(Locale.US, "%.2f", SplashActivity.usedRom) + " GB";
            txtRomTotal.setText(TROM);
            txtRomFree.setText(UROM);
            txtRomUsed.setText(AROM);
            progressRom.setProgress((int) SplashActivity.usedRomPercentage);

            String TINS = "Total\t\t\t\t\t" + String.format(Locale.US, "%.2f", SplashActivity.totalInternalStorage) + " GB";
            String AINS = "Free\t\t\t\t\t" + String.format(Locale.US, "%.2f", SplashActivity.availableInternalStorage) + " GB";
            String UINS = "Used\t\t\t\t\t" + String.format(Locale.US, "%.2f", SplashActivity.usedInternalStorage) + " GB";
            txtInStorageTotal.setText(TINS);
            txtInStorageUsed.setText(UINS);
            txtInStorageFree.setText(AINS);
            progressInStorage.setProgress((int) SplashActivity.usedInternalPercentage);

            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED) && ContextCompat.getExternalFilesDirs(context, null).length >= 2) {
                cardExStorage.setVisibility(View.VISIBLE);

                String TEXS = "Total\t\t\t\t\t" + String.format(Locale.US, "%.2f", SplashActivity.totalExternalStorage) + " GB";
                String AEXS = "Free\t\t\t\t\t" + String.format(Locale.US, "%.2f", SplashActivity.availableExternalStorage) + " GB";
                String UEXS = "Used\t\t\t\t\t" + String.format(Locale.US, "%.2f", SplashActivity.usedExternalStorage) + " GB";
                txtExStorageTotal.setText(TEXS);
                txtExStorageUsed.setText(UEXS);
                txtExStorageFree.setText(AEXS);
                progressExStorage.setProgress((int) SplashActivity.usedExternalPercentage);
            } else {
                cardExStorage.setVisibility(View.GONE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rootView;
    }
}
