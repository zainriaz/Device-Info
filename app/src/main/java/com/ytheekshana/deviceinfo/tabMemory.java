package com.ytheekshana.deviceinfo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class tabMemory extends Fragment {
    private Context context;
    private TextView txtRAMStatus;
    private ProgressBar progressRam;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabmemory, container, false);
        TextView txtROMPath,txtROMStatus,txtInStoragePath,txtInStorageStatus,txtExStoragePath,
                txtExStorageStatus;



        try {
            txtRAMStatus = rootView.findViewById(R.id.txtRAMStatus);
            progressRam = rootView.findViewById(R.id.progressRam);

            txtROMPath = rootView.findViewById(R.id.txtROMPath);
            txtROMStatus = rootView.findViewById(R.id.txtROMStatus);
            ProgressBar progressRom = rootView.findViewById(R.id.progressRom);

            txtInStoragePath = rootView.findViewById(R.id.txtInStoragePath);
            txtInStorageStatus = rootView.findViewById(R.id.txtInStorageStatus);
            ProgressBar progressInStorage = rootView.findViewById(R.id.progressInStorage);

            txtExStoragePath = rootView.findViewById(R.id.txtExStoragePath);
            txtExStorageStatus = rootView.findViewById(R.id.txtExStorageStatus);
            ProgressBar progressExStorage = rootView.findViewById(R.id.progressExStorage);
            CardView cardExStorage = rootView.findViewById(R.id.cardExStorageInfo);

            DrawableCompat.setTint(progressRam.getProgressDrawable(), getResources().getColor(R.color.progress_ram));
            DrawableCompat.setTint(progressRom.getProgressDrawable(), getResources().getColor(R.color.progress_rom));
            DrawableCompat.setTint(progressInStorage.getProgressDrawable(), getResources().getColor(R.color.progress_insto));
            DrawableCompat.setTint(progressExStorage.getProgressDrawable(), getResources().getColor(R.color.progress_exsto));

            final MemoryInfo memoryInfo = new MemoryInfo(getActivity(), getContext());
            final Handler updateRam = new Handler();
            Runnable runnable = new Runnable() {
                public void run() {
                    memoryInfo.Ram();
                    String ramStatus = Double.toString(memoryInfo.getUsedRam()) + "MB used of " + Double.toString(memoryInfo.getTotalRam())+"MB";
                    txtRAMStatus.setText(ramStatus);
                    progressRam.setProgress((int) memoryInfo.getUsedRamPercentage());
                    updateRam.postDelayed(this, 1000);
                }
            };
            updateRam.postDelayed(runnable, 0);

            String romStatus = String.format(Locale.US, "%.2f", SplashActivity.usedRom) + "GB used of " + String.format(Locale.US, "%.2f", SplashActivity.totalRom) + "GB";
            txtROMStatus.setText(romStatus);
            txtROMPath.setText(SplashActivity.romPath);
            progressRom.setProgress((int) SplashActivity.usedRomPercentage);

            String internalStorageStatus = String.format(Locale.US, "%.2f", SplashActivity.usedInternalStorage) + "GB used of " + String.format(Locale.US, "%.2f", SplashActivity.totalInternalStorage) + "GB";
            txtInStorageStatus.setText(internalStorageStatus);
            txtInStoragePath.setText(SplashActivity.internalStoragePath);
            progressInStorage.setProgress((int) SplashActivity.usedInternalPercentage);


            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED) && ContextCompat.getExternalFilesDirs(context, null).length >= 2) {
                cardExStorage.setVisibility(View.VISIBLE);

                String externalStorageStatus = String.format(Locale.US, "%.2f", SplashActivity.usedExternalStorage) + "GB used of " + String.format(Locale.US, "%.2f", SplashActivity.totalExternalStorage) + "GB";
                txtExStorageStatus.setText(externalStorageStatus);
                txtExStoragePath.setText(SplashActivity.externalStoragePath);
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
