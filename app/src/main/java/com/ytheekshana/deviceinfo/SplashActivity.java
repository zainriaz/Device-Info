package com.ytheekshana.deviceinfo;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaredrummler.android.device.DeviceName;

import java.io.RandomAccessFile;
import java.util.Objects;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SplashActivity extends Activity implements GLSurfaceView.Renderer {
    public static String deviceName, wifiMac, bluetoothMac, usbHost, glVersion, androidRuntime, kernelVersion,
            selinuxMode, processorName, cpuABIs, processorHardware, cpuGovernor, gpuRenderer, gpuVendor, gpuVersion,
            batteryCapacity, displaySize, displayOrientation, displayPhysicalSize, romPath, internalStoragePath,
            externalStoragePath;

    public static boolean rootedStatus;
    public static double cpuMaxFreq, cpuMinFreq, displayDensity;
    public static int displayHeight, displayWidth, displayDensityDPI, numberOfInstalledApps, numberOfSensors;
    public static float diplayRefreshRate, fontSize;

    public static double totalRam, availableRam, usedRam, usedRamPercentage;
    public static double totalRom, availableRom, usedRom, usedRomPercentage;
    public static double totalInternalStorage, availableInternalStorage, usedInternalStorage, usedInternalPercentage;
    public static double totalExternalStorage, availableExternalStorage, usedExternalStorage, usedExternalPercentage;

    ProgressBar progressBarSplash;
    private GLSurfaceView glSurfaceView;
    TextView txtGPUsupport;
    int threadSleepAmount = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        txtGPUsupport = findViewById(R.id.txtGPUsupport);
        txtGPUsupport.setText(R.string.GPUVendor);
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setRenderer(this);
        ((ViewGroup) txtGPUsupport.getParent()).addView(glSurfaceView);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        int themeId = sharedPrefs.getInt("ThemeNoBar", R.style.AppTheme_NoActionBar);
        int themeColor = sharedPrefs.getInt("accent_color_dialog", Color.parseColor("#2196f3"));
        int themeColorDark = GetDetails.getDarkColor(this, themeColor);
        setTheme(themeId);

        getWindow().setStatusBarColor(themeColorDark);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(getString(R.string.app_name), icon, themeColor);
        setTaskDescription(taskDescription);

        RelativeLayout relativeLayout = findViewById(R.id.mainlayout_Splash);
        //relativeLayout.setBackgroundColor(themeColor);
        int abc[] = {themeColorDark, themeColor};
        GradientDrawable splashGradient = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, abc);
        relativeLayout.setBackground(splashGradient);

        progressBarSplash = findViewById(R.id.progressBar_Splash);
        TextView txtAppName = findViewById(R.id.txtAppName);
        ImageView imageLogoSplash1 = findViewById(R.id.imageLogoSplash1);
        ImageView imageLogoSplash2 = findViewById(R.id.imageLogoSplash2);
        ImageView imageLogoSplash3 = findViewById(R.id.imageLogoSplash3);
        ImageView imageLogoSplash4 = findViewById(R.id.imageLogoSplash4);

        Animation animLogoFromTop = AnimationUtils.loadAnimation(this, R.anim.logo_from_top);
        imageLogoSplash1.setAnimation(animLogoFromTop);
        Animation animLogoFromBottom = AnimationUtils.loadAnimation(this, R.anim.logo_from_bottom);
        imageLogoSplash2.setAnimation(animLogoFromBottom);
        Animation animLogoFromLeft = AnimationUtils.loadAnimation(this, R.anim.logo_from_left);
        imageLogoSplash3.setAnimation(animLogoFromLeft);
        Animation animLogoFromRight = AnimationUtils.loadAnimation(this, R.anim.logo_from_right);
        imageLogoSplash4.setAnimation(animLogoFromRight);

        Animation animpTextview = AnimationUtils.loadAnimation(this, R.anim.from_bottom);
        txtAppName.setAnimation(animpTextview);
        progressBarSplash.setAnimation(animpTextview);
        LoadDetails loadDetails = new LoadDetails(this, this);
        loadDetails.execute();

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        try {
            runOnUiThread(() -> glSurfaceView.setVisibility(View.GONE));
            gpuRenderer = gl.glGetString(GL10.GL_RENDERER);
            gpuVendor = gl.glGetString(GL10.GL_VENDOR);
            gpuVersion = gl.glGetString(GL10.GL_VERSION);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    class LoadDetails extends AsyncTask<Void, Integer, Void> {

        Context context;
        Activity activity;

        LoadDetails(Activity activity, Context context) {
            this.activity = activity;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //Device
                if (GetDetails.GetFromBuildProp("ro.semc.product.name").equals("")) {
                    DeviceName.with(context).request((info, error) -> deviceName = info.getName());
                } else {
                    deviceName = GetDetails.GetFromBuildProp("ro.semc.product.name");
                }
                wifiMac = GetDetails.getWifiMacAddress();
                bluetoothMac = GetDetails.getBluetoothMac(context);
                if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_USB_HOST)) {
                    usbHost = "Supported";
                } else {
                    usbHost = "Not Supported";
                }

                Thread.sleep(threadSleepAmount);
                publishProgress(10 * 10);

                //System
                rootedStatus = GetDetails.isRooted();
                ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                ConfigurationInfo info;
                info = Objects.requireNonNull(am).getDeviceConfigurationInfo();
                glVersion = info.getGlEsVersion();
                androidRuntime = System.getProperty("java.vm.version");
                kernelVersion = System.getProperty("os.version");
                selinuxMode = GetDetails.GetSELinuxMode();
                Thread.sleep(threadSleepAmount);
                publishProgress(20 * 10);

                //CPU
                RandomAccessFile readermax, readermin;
                readermax = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "r");
                readermin = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq", "r");
                String maxfreq = readermax.readLine();
                String minfreq = readermin.readLine();
                cpuMaxFreq = Double.parseDouble(maxfreq) / 1000;
                cpuMinFreq = Double.parseDouble(minfreq) / 1000;
                readermax.close();
                readermin.close();

                processorName = GetDetails.getProcessor();

                StringBuilder ABIs = new StringBuilder();
                for (int a = 0; a < Build.SUPPORTED_ABIS.length; a++) {
                    ABIs.append(Build.SUPPORTED_ABIS[a]).append(", ");
                }
                cpuABIs = ABIs.substring(0, ABIs.length() - 2);
                processorHardware = GetDetails.getProcessorHardware();
                cpuGovernor = GetDetails.getCPUGoverner();
                Thread.sleep(threadSleepAmount);
                publishProgress(30 * 10);

                //Battery
                batteryCapacity = String.valueOf(GetDetails.getBatteryCapacity(context));
                Thread.sleep(threadSleepAmount);
                publishProgress(40 * 10);

                //Display
                DisplayMetrics getDisplay = new DisplayMetrics();
                activity.getWindowManager().getDefaultDisplay().getMetrics(getDisplay);
                WindowManager windowManager = activity.getWindowManager();
                Display display1 = windowManager.getDefaultDisplay();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                display1.getMetrics(displayMetrics);
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(display1, realSize);
                displayHeight = realSize.y;
                displayWidth = realSize.x;
                displayDensityDPI = getDisplay.densityDpi;
                displayDensity = (double) getDisplay.density;
                displaySize = "";
                if (displayDensity >= 4.0) {
                    displaySize = "XXXHDPI";
                } else if (displayDensity >= 3.0) {
                    displaySize = "XXHDPI";
                } else if (displayDensity >= 2.0) {
                    displaySize = "XHDPI";
                } else if (displayDensity >= 1.5) {
                    displaySize = "HDPI";
                } else if (displayDensity >= 1.0) {
                    displaySize = "MDPI";
                } else if (displayDensity >= 0.75) {
                    displaySize = "LDPI";
                }
                Display display = ((WindowManager) Objects.requireNonNull(activity.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();
                diplayRefreshRate = display.getRefreshRate();
                int orie = activity.getResources().getConfiguration().orientation;
                if (orie == Configuration.ORIENTATION_PORTRAIT) {
                    displayOrientation = "Portrait";
                } else if (orie == Configuration.ORIENTATION_LANDSCAPE) {
                    displayOrientation = "Landscape";
                } else if (orie == Configuration.ORIENTATION_UNDEFINED) {
                    displayOrientation = "Undefined";
                }
                fontSize = getResources().getConfiguration().fontScale;
                displayPhysicalSize = GetDetails.getDisplaySize(activity);
                Thread.sleep(threadSleepAmount);
                publishProgress(50 * 10);


                PackageManager pm = activity.getPackageManager();
                numberOfInstalledApps = pm.getInstalledApplications(0).size();
                SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
                numberOfSensors = Objects.requireNonNull(mSensorManager).getSensorList(Sensor.TYPE_ALL).size();
                Thread.sleep(threadSleepAmount);
                publishProgress(60 * 10);

                //Memory
                MemoryInfo memoryInfo = new MemoryInfo(activity, context);
                memoryInfo.Ram();
                memoryInfo.Rom();
                memoryInfo.InternalStorage();
                memoryInfo.ExternalStorage();
                totalRam = memoryInfo.getTotalRam();
                availableRam = memoryInfo.getAvailableRam();
                usedRam = memoryInfo.getUsedRam();
                usedRamPercentage = memoryInfo.getUsedRamPercentage();
                totalRom = memoryInfo.getTotalRom();
                availableRom = memoryInfo.getAvailableRom();
                usedRom = memoryInfo.getUsedRom();
                usedRomPercentage = memoryInfo.getUsedRomPercentage();
                totalInternalStorage = memoryInfo.getTotalInternalStorage();
                availableInternalStorage = memoryInfo.getAvailableInternalStorage();
                usedInternalStorage = memoryInfo.getUsedInternalStorage();
                usedInternalPercentage = memoryInfo.getUsedInternalPercentage();
                totalExternalStorage = memoryInfo.getTotalExternalStorage();
                availableExternalStorage = memoryInfo.getAvailableExternalStorage();
                usedExternalStorage = memoryInfo.getUsedExternalStorage();
                usedExternalPercentage = memoryInfo.getUsedExternalPercentage();
                romPath = memoryInfo.getRomPath();
                internalStoragePath = memoryInfo.getInternalStoragePath();
                externalStoragePath = memoryInfo.getExternalStoragePath();
                Thread.sleep(threadSleepAmount);
                publishProgress(70 * 10);


            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            super.onProgressUpdate(value);
            animateProgressBar(progressBarSplash, progressBarSplash.getProgress(), value[0]);
            progressBarSplash.setProgress(value[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent loadMain = new Intent(context, MainActivity.class);
            startActivity(loadMain);
            ((Activity) context).finish();
            overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);

        }

        private void animateProgressBar(ProgressBar progressBar, int startValue, int endValue) {
            ObjectAnimator progressAnimatorCPU = ObjectAnimator.ofInt(progressBar, "progress", startValue, endValue);
            progressAnimatorCPU.setDuration(300);
            progressAnimatorCPU.start();
        }
    }
}


