package com.ytheekshana.deviceinfo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class tabDashboard extends Fragment {
    TextView txtRamPerce, txtRamStatus, txtBatteryPerce, txtBatteryStatus, txtInStoragePerce, txtInStorageStatus, txtExStoragePerce, txtExStorageStatus, txtCPUPerce, txtCPUStatus, txtRomPerce, txtRomStatus, txtSensorCount, txtAppCount;
    RoundCornerProgressBar ProgressBarRam, ProgressBarBattery, ProgressBarInStorage, ProgressBarExStorage, ProgressBarCPU, ProgressBarRom;
    double ARam, TRam, URam, UPerc, AvailableInSto, TotalInSto, UsedPercInSto, AvailableExSto, TotalExSto, UsedPercExSto, AvailableStoRom, TotalStoRom, UsedPercRom;
    Context BatteryContext;
    int a, e, startROM, startRAM, startInStorage, startExStorage, startBattery, startCPU, battery_progress_status = 0, usagecpu;
    CPUUsage cu2;
    String cUsage;
    Timer timercUsage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabdashboard, container, false);
        //006bff
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
        ProgressBarRam.setProgressColor(Color.parseColor("#0059d4"));
        ProgressBarRam.setProgressBackgroundColor(Color.parseColor("#c2dbfd"));
        ProgressBarRam.setMax(100);
        ProgressBarRam.setRadius(0);

        ProgressBarRom = rootView.findViewById(R.id.progressRom);
        ProgressBarRom.setProgressColor(Color.parseColor("#0059d4"));
        ProgressBarRom.setProgressBackgroundColor(Color.parseColor("#c2dbfd"));
        ProgressBarRom.setMax(100);
        ProgressBarRom.setRadius(0);

        ProgressBarBattery = rootView.findViewById(R.id.progressBattery);
        ProgressBarBattery.setProgressColor(Color.parseColor("#0059d4"));
        ProgressBarBattery.setProgressBackgroundColor(Color.parseColor("#c2dbfd"));
        ProgressBarBattery.setMax(100);
        ProgressBarBattery.setRadius(0);

        ProgressBarInStorage = rootView.findViewById(R.id.progressInStorage);
        ProgressBarInStorage.setProgressColor(Color.parseColor("#0059d4"));
        ProgressBarInStorage.setProgressBackgroundColor(Color.parseColor("#c2dbfd"));
        ProgressBarInStorage.setMax(100);
        ProgressBarInStorage.setRadius(0);

        ProgressBarExStorage = rootView.findViewById(R.id.progressExStorage);
        ProgressBarExStorage.setProgressColor(Color.parseColor("#0059d4"));
        ProgressBarExStorage.setProgressBackgroundColor(Color.parseColor("#c2dbfd"));
        ProgressBarExStorage.setMax(100);
        ProgressBarExStorage.setRadius(0);

        ProgressBarCPU = rootView.findViewById(R.id.progressCPU);
        ProgressBarCPU.setProgressColor(Color.parseColor("#0059d4"));
        ProgressBarCPU.setProgressBackgroundColor(Color.parseColor("#c2dbfd"));
        ProgressBarCPU.setMax(100);
        ProgressBarCPU.setRadius(0);

        startCPU = cu2.getTotalCpuUsage();
        PackageManager pm = Objects.requireNonNull(getActivity()).getPackageManager();
        int numberOfInstalledApps = pm.getInstalledApplications(0).size();
        //txtAppCount.setText(numberOfInstalledApps);
        SensorManager mSensorManager = (SensorManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SENSOR_SERVICE);
        int numberOfSensors = Objects.requireNonNull(mSensorManager).getSensorList(Sensor.TYPE_ALL).size();
       // txtSensorCount.setText(numberOfSensors);
        animateTextView(0,numberOfInstalledApps,txtAppCount);
        animateTextView(0,numberOfSensors,txtSensorCount);

        GetRam();
        startRAM = (int) UPerc;
        String setRam = "Free:" + String.format(Locale.US, "%.2f", ARam / 1024) + " GB, Total:" + String.format(Locale.US, "%.2f", TRam / 1024) + " GB";
        txtRamStatus.setText(setRam);
        String ram_percentage = String.valueOf((int) UPerc) + "%";
        txtRamPerce.setText(ram_percentage);

        GetRom();
        startROM = (int) UsedPercRom;
        String setRom = "Free:" + String.format(Locale.US, "%.1f", AvailableStoRom) + " GB, Total:" + String.format(Locale.US, "%.1f", TotalStoRom) + " GB";
        txtRomStatus.setText(setRom);
        String storage_percentageRom = String.valueOf((int) UsedPercRom) + "%";
        txtRomPerce.setText(storage_percentageRom);

        GetInStorage();
        startInStorage = (int) UsedPercInSto;
        String setInStorage = "Free:" + String.format(Locale.US, "%.1f", AvailableInSto) + " GB, Total:" + String.format(Locale.US, "%.1f", TotalInSto) + " GB";
        txtInStorageStatus.setText(setInStorage);
        String in_storage_percentage = String.valueOf((int) UsedPercInSto) + "%";
        txtInStoragePerce.setText(in_storage_percentage);

        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED) && ContextCompat.getExternalFilesDirs(Objects.requireNonNull(getContext()), null).length >= 2) {
            cardExternalStorage.setVisibility(View.VISIBLE);
            GetExStorage();
            startExStorage = (int) UsedPercExSto;
            String setExStorage = "Free:" + String.format(Locale.US, "%.1f", AvailableExSto) + " GB, Total:" + String.format(Locale.US, "%.1f", TotalExSto) + " GB";
            txtExStorageStatus.setText(setExStorage);
            String ex_storage_percentage = String.valueOf((int) UsedPercExSto) + "%";
            txtExStoragePerce.setText(ex_storage_percentage);
        } else {
            cardExternalStorage.setVisibility(View.GONE);
        }

        final Handler updateRam = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                GetRam();
                ProgressBarRam.setProgress((int) UPerc);
                String ram_percentage = (int) UPerc + "%";
                txtRamPerce.setText(ram_percentage);
                String setRam = "Free:" + String.format(Locale.US, "%.2f", ARam / 1024) + " GB, Total:" + String.format(Locale.US, "%.2f", TRam / 1024) + " GB";
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


        /*Thread LoadStartRam = new Thread() {
            @Override
            public void run() {
                try {
                    for (a = 1; a <= startRAM; a++) {
                        Thread.sleep(10);
                        ProgressBarBattery.post(new Runnable() {
                            @Override
                            public void run() {
                                ProgressBarRam.setProgress(a);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        LoadStartRam.start();*/

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

    private void GetRam() {
        try {
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.ACTIVITY_SERVICE);
            assert activityManager != null;
            activityManager.getMemoryInfo(mi);
            ARam = (double) (mi.availMem / 1024 / 1024);
            TRam = (double) (mi.totalMem / 1024 / 1024);
            URam = TRam - ARam;
            UPerc = (((URam) * 100) / TRam);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void GetInStorage() {
        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
            AvailableInSto = (((double) stat.getBlockSizeLong() * (double) stat.getAvailableBlocksLong()) / 1024 / 1024 / 1024);
            TotalInSto = ((double) stat.getBlockSizeLong() * (double) stat.getBlockCountLong()) / 1024 / 1024 / 1024;
            UsedPercInSto = (double) ((((stat.getBlockSizeLong() * stat.getBlockCountLong()) - (stat.getBlockSizeLong() * stat.getAvailableBlocksLong())) * 100) / (stat.getBlockSizeLong() * stat.getBlockCountLong()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void GetExStorage() {
        try {
            StatFs stat = new StatFs(GetDetails.getStorageDirectories(Objects.requireNonNull(getContext()))[0]);
            AvailableExSto = (((double) stat.getBlockSizeLong() * (double) stat.getAvailableBlocksLong()) / 1024 / 1024 / 1024);
            TotalExSto = ((double) stat.getBlockSizeLong() * (double) stat.getBlockCountLong()) / 1024 / 1024 / 1024;
            UsedPercExSto = (double) ((((stat.getBlockSizeLong() * stat.getBlockCountLong()) - (stat.getBlockSizeLong() * stat.getAvailableBlocksLong())) * 100) / (stat.getBlockSizeLong() * stat.getBlockCountLong()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void GetRom() {
        try {
            StatFs stat = new StatFs(Environment.getRootDirectory().getAbsolutePath());
            AvailableStoRom = (((double) stat.getBlockSizeLong() * (double) stat.getAvailableBlocksLong()) / 1024 / 1024 / 1024);
            TotalStoRom = ((double) stat.getBlockSizeLong() * (double) stat.getBlockCountLong()) / 1024 / 1024 / 1024;
            UsedPercRom = (double) ((((stat.getBlockSizeLong() * stat.getBlockCountLong()) - (stat.getBlockSizeLong() * stat.getAvailableBlocksLong())) * 100) / (stat.getBlockSizeLong() * stat.getBlockCountLong()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

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


