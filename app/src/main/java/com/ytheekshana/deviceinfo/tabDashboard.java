package com.ytheekshana.deviceinfo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class tabDashboard extends Fragment {
    TextView txtRamPerce, txtRamStatus, txtBatteryPerce, txtBatteryStatus, txtInStoragePerce, txtInStorageStatus, txtExStoragePerce, txtExStorageStatus, txtCPUPerce, txtCPUStatus, txtRomPerce, txtRomStatus, txtSensorCount, txtAppCount;
    RoundCornerProgressBar ProgressBarRam, ProgressBarBattery, ProgressBarInStorage, ProgressBarExStorage, ProgressBarCPU, ProgressBarRom;
    Context BatteryContext;
    int a, e, startROM, startRAM, startInStorage, startExStorage, startBattery, startCPU, usagecpu;
    CPUUsage cu2;
    String cUsage;
    Timer timercUsage;
    int colorProgressBackground;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabdashboard, container, false);

        try {
            colorProgressBackground = ColorUtils.setAlphaComponent(MainActivity.themeColor, 70);

            ImageView imgRAM = rootView.findViewById(R.id.imageRam);
            ImageView imgROM = rootView.findViewById(R.id.imageROM);
            ImageView imgInStorage = rootView.findViewById(R.id.imageInStorage);
            ImageView imgExStorage = rootView.findViewById(R.id.imageExStorage);
            ImageView imgBattery = rootView.findViewById(R.id.imageBattery);
            ImageView imgCPU = rootView.findViewById(R.id.imageCPU);
            ImageView imgSensor = rootView.findViewById(R.id.imageSensor);
            ImageView imgApps = rootView.findViewById(R.id.imageApps);

            ColorFilter accentFilter = new LightingColorFilter(Color.BLACK, MainActivity.themeColor);
            imgRAM.setColorFilter(accentFilter);
            imgROM.setColorFilter(accentFilter);
            imgInStorage.setColorFilter(accentFilter);
            imgExStorage.setColorFilter(accentFilter);
            imgBattery.setColorFilter(accentFilter);
            imgCPU.setColorFilter(accentFilter);
            imgSensor.setColorFilter(accentFilter);
            imgApps.setColorFilter(accentFilter);

            IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            BatteryContext = Objects.requireNonNull(getActivity()).getApplicationContext();
            BatteryContext.registerReceiver(batteryBroadcastReceiver, batteryIntentFilter);
            cu2 = new CPUUsage();
            final CardView cardRam = rootView.findViewById(R.id.cardviewRam);
            final CardView cardRom = rootView.findViewById(R.id.cardviewRom);
            final CardView cardInternalStorage = rootView.findViewById(R.id.cardviewInStorage);
            final CardView cardExternalStorage = rootView.findViewById(R.id.cardviewExStorage);
            final CardView cardBattery = rootView.findViewById(R.id.cardviewBattery);
            final CardView cardCpu = rootView.findViewById(R.id.cardviewCPU);
            final CardView cardSensor = rootView.findViewById(R.id.cardviewSensor);
            final CardView cardApps = rootView.findViewById(R.id.cardviewApp);

            txtRamPerce = rootView.findViewById(R.id.txtRamPerc);
            txtRamStatus = rootView.findViewById(R.id.txtRamStatus);
            txtRomPerce = rootView.findViewById(R.id.txtROMPerc);
            txtRomStatus = rootView.findViewById(R.id.txtROMStatus);
            txtBatteryPerce = rootView.findViewById(R.id.txtBatteryPerc);
            txtBatteryStatus = rootView.findViewById(R.id.txtBatteryStatus);
            txtInStoragePerce = rootView.findViewById(R.id.txtInStoragePerc);
            txtInStorageStatus = rootView.findViewById(R.id.txtInStorageStatus);
            txtExStoragePerce = rootView.findViewById(R.id.txtExStoragePerc);
            txtExStorageStatus = rootView.findViewById(R.id.txtExStorageStatus);
            txtCPUPerce = rootView.findViewById(R.id.txtCPUPerc);
            txtCPUStatus = rootView.findViewById(R.id.txtCPUStatus);
            txtSensorCount = rootView.findViewById(R.id.txtSensorCount);
            txtAppCount = rootView.findViewById(R.id.txtAppCount);

            ProgressBarRam = rootView.findViewById(R.id.progressRam);
            ProgressBarRam.setProgressColor(MainActivity.themeColor);
            ProgressBarRam.setProgressBackgroundColor(colorProgressBackground);

            ProgressBarRom = rootView.findViewById(R.id.progressRom);
            ProgressBarRom.setProgressColor(MainActivity.themeColor);
            ProgressBarRom.setProgressBackgroundColor(colorProgressBackground);

            ProgressBarBattery = rootView.findViewById(R.id.progressBattery);
            ProgressBarBattery.setProgressColor(MainActivity.themeColor);
            ProgressBarBattery.setProgressBackgroundColor(colorProgressBackground);

            ProgressBarInStorage = rootView.findViewById(R.id.progressInStorage);
            ProgressBarInStorage.setProgressColor(MainActivity.themeColor);
            ProgressBarInStorage.setProgressBackgroundColor(colorProgressBackground);

            ProgressBarExStorage = rootView.findViewById(R.id.progressExStorage);
            ProgressBarExStorage.setProgressColor(MainActivity.themeColor);
            ProgressBarExStorage.setProgressBackgroundColor(colorProgressBackground);

            ProgressBarCPU = rootView.findViewById(R.id.progressCPU);
            ProgressBarCPU.setProgressColor(MainActivity.themeColor);
            ProgressBarCPU.setProgressBackgroundColor(colorProgressBackground);

            startCPU = cu2.getTotalCpuUsage();

            animateTextView(0, SplashActivity.numberOfInstalledApps, txtAppCount);
            animateTextView(0, SplashActivity.numberOfSensors, txtSensorCount);

            startRAM = (int) SplashActivity.usedRamPercentage;
            String setRam = "Free:" + String.format(Locale.US, "%.2f", SplashActivity.availableRam / 1024) + " GB, Total:" + String.format(Locale.US, "%.2f", SplashActivity.totalRam / 1024) + " GB";
            txtRamStatus.setText(setRam);
            String ram_percentage = String.valueOf((int) SplashActivity.usedRamPercentage) + "%";
            txtRamPerce.setText(ram_percentage);

            startROM = (int) SplashActivity.usedRomPercentage;
            String setRom = "Free:" + String.format(Locale.US, "%.1f", SplashActivity.availableRom) + " GB, Total:" + String.format(Locale.US, "%.1f", SplashActivity.totalRom) + " GB";
            txtRomStatus.setText(setRom);
            String storage_percentageRom = String.valueOf((int) SplashActivity.usedRomPercentage) + "%";
            txtRomPerce.setText(storage_percentageRom);

            startInStorage = (int) SplashActivity.usedInternalPercentage;
            String setInStorage = "Free:" + String.format(Locale.US, "%.1f", SplashActivity.availableInternalStorage) + " GB, Total:" + String.format(Locale.US, "%.1f", SplashActivity.totalInternalStorage) + " GB";
            txtInStorageStatus.setText(setInStorage);
            String in_storage_percentage = String.valueOf((int) SplashActivity.usedInternalPercentage) + "%";
            txtInStoragePerce.setText(in_storage_percentage);

            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED) && ContextCompat.getExternalFilesDirs(Objects.requireNonNull(getContext()), null).length >= 2) {
                cardExternalStorage.setVisibility(View.VISIBLE);
                startExStorage = (int) SplashActivity.usedExternalPercentage;
                String setExStorage = "Free:" + String.format(Locale.US, "%.1f", SplashActivity.availableExternalStorage) + " GB, Total:" + String.format(Locale.US, "%.1f", SplashActivity.totalExternalStorage) + " GB";
                txtExStorageStatus.setText(setExStorage);
                String ex_storage_percentage = String.valueOf((int) SplashActivity.usedExternalPercentage) + "%";
                txtExStoragePerce.setText(ex_storage_percentage);
            } else {
                cardExternalStorage.setVisibility(View.GONE);
            }

            final MemoryInfo memoryInfo = new MemoryInfo(getActivity(),getContext());
            final Handler updateRam = new Handler();
            Runnable runnable = new Runnable() {
                public void run() {
                    memoryInfo.Ram();
                    ProgressBarRam.setProgress((int) memoryInfo.getUsedRamPercentage());
                    String ram_percentage = (int) memoryInfo.getUsedRamPercentage() + "%";
                    txtRamPerce.setText(ram_percentage);
                    String setRam = "Free:" + String.format(Locale.US, "%.2f", memoryInfo.getAvailableRam() / 1024) + " GB, Total:" + String.format(Locale.US, "%.2f", memoryInfo.getTotalRam() / 1024) + " GB";
                    txtRamStatus.setText(setRam);
                    updateRam.postDelayed(this, 1000);
                }
            };
            updateRam.postDelayed(runnable, 1000);

            timercUsage = new Timer();
            timercUsage.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    usagecpu = cu2.getTotalCpuUsage();
                    cUsage = String.valueOf(usagecpu) + " %";
                    txtCPUPerce.post(new Runnable() {
                        @Override
                        public void run() {
                            txtCPUPerce.setText(cUsage);
                        }
                    });
                    ProgressBarCPU.post(new Runnable() {
                        @Override
                        public void run() {
                            ProgressBarCPU.setProgress(usagecpu);
                        }
                    });

                }
            }, 1000, 1000);

            ObjectAnimator progressAnimatorRAM = ObjectAnimator.ofFloat(ProgressBarRam, "progress", 0.0f, (float) startRAM);
            progressAnimatorRAM.setDuration(800);
            progressAnimatorRAM.start();

            ObjectAnimator progressAnimatorROM = ObjectAnimator.ofFloat(ProgressBarRom, "progress", 0.0f, (float) startROM);
            progressAnimatorROM.setDuration(800);
            progressAnimatorROM.start();

            ObjectAnimator progressAnimatorInStorage = ObjectAnimator.ofFloat(ProgressBarInStorage, "progress", 0.0f, (float) startInStorage);
            progressAnimatorInStorage.setDuration(800);
            progressAnimatorInStorage.start();

            ObjectAnimator progressAnimatorExStorage = ObjectAnimator.ofFloat(ProgressBarExStorage, "progress", 0.0f, (float) startExStorage);
            progressAnimatorExStorage.setDuration(800);
            progressAnimatorExStorage.start();

            ObjectAnimator progressAnimatorCPU = ObjectAnimator.ofFloat(ProgressBarCPU, "progress", 0.0f, (float) startCPU);
            progressAnimatorCPU.setDuration(800);
            progressAnimatorCPU.start();


            final com.ytheekshana.deviceinfo.BounceInterpolator bounceInterpolator = new com.ytheekshana.deviceinfo.BounceInterpolator(0.2, 20);
            cardRam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animRam = AnimationUtils.loadAnimation(getContext(), R.anim.bounce_dash);
                    animRam.setInterpolator(bounceInterpolator);
                    cardRam.startAnimation(animRam);
                }
            });
            cardRom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animRom = AnimationUtils.loadAnimation(getContext(), R.anim.bounce_dash);
                    animRom.setInterpolator(bounceInterpolator);
                    cardRom.startAnimation(animRom);
                }
            });
            cardInternalStorage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animInSto = AnimationUtils.loadAnimation(getContext(), R.anim.bounce_dash);
                    animInSto.setInterpolator(bounceInterpolator);
                    cardInternalStorage.startAnimation(animInSto);
                }
            });
            cardExternalStorage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animExSto = AnimationUtils.loadAnimation(getContext(), R.anim.bounce_dash);
                    animExSto.setInterpolator(bounceInterpolator);
                    cardExternalStorage.startAnimation(animExSto);
                }
            });
            cardBattery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animBattery = AnimationUtils.loadAnimation(getContext(), R.anim.bounce_dash);
                    animBattery.setInterpolator(bounceInterpolator);
                    cardBattery.startAnimation(animBattery);
                }
            });
            cardCpu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animCpu = AnimationUtils.loadAnimation(getContext(), R.anim.bounce_dash);
                    animCpu.setInterpolator(bounceInterpolator);
                    cardCpu.startAnimation(animCpu);
                }
            });
            cardSensor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animSensor = AnimationUtils.loadAnimation(getContext(), R.anim.bounce_dash);
                    animSensor.setInterpolator(bounceInterpolator);
                    cardSensor.startAnimation(animSensor);
                }
            });
            cardApps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animApps = AnimationUtils.loadAnimation(getContext(), R.anim.bounce_dash);
                    animApps.setInterpolator(bounceInterpolator);
                    cardApps.startAnimation(animApps);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            timercUsage.cancel();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private BroadcastReceiver batteryBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final int batlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            startBattery = batlevel;
            int voltage = intent.getIntExtra("voltage", 0);
            int temperature = (intent.getIntExtra("temperature", 0)) / 10;
            String setBattery = "Voltage: " + voltage + "mV, Temperature: " + temperature + " \u2103";
            txtBatteryStatus.setText(setBattery);
            String battery_percentage = Integer.toString(batlevel) + "%";
            txtBatteryPerce.setText(battery_percentage);

            ObjectAnimator progressAnimatorBattery = ObjectAnimator.ofFloat(ProgressBarBattery, "progress", 0.0f, (float) startBattery);
            progressAnimatorBattery.setDuration(800);
            progressAnimatorBattery.start();

            ProgressBarBattery.setProgress(batlevel);
        }
    };

    public void animateTextView(int initialValue, int finalValue, final TextView textview) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(800);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textview.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        valueAnimator.start();
    }
}


