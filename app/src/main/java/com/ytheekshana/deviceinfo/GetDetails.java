package com.ytheekshana.deviceinfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import android.util.DisplayMetrics;
import android.util.Size;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class GetDetails {

    @ColorInt
    static int getThemeColor(@NonNull final Context context, @AttrRes final int attributeColor) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attributeColor, value, true);
        return value.data;
    }

    static String GetFromBuildProp(String PropKey) {
        Process p;
        String propvalue = "";
        try {
            p = new ProcessBuilder("/system/bin/getprop", PropKey).redirectErrorStream(true).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                propvalue = line;
            }
            p.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return propvalue;
    }

    static String GetOSNameAdvanced() {
        String OSName;
        switch (Build.VERSION.SDK_INT) {
            case 21:
                OSName = "Lollipop";
                break;
            case 22:
                OSName = "Lollipop MR1";
                break;
            case 23:
                OSName = "Marshmallow";
                break;
            case 24:
                OSName = "Nougat";
                break;
            case 25:
                OSName = "Nougat MR1";
                break;
            case 26:
                OSName = "Oreo";
                break;
            case 27:
                OSName = "Oreo MR1";
                break;
            case 28:
                OSName = "Android Pie";
                break;
            default:
                OSName = "Unknown";
        }
        return OSName;
    }

    static String GetOSReleaseDate() {
        String OSReleaseDate;
        switch (Build.VERSION.SDK_INT) {
            case 11:
            case 12:
            case 13:
                OSReleaseDate = "February 22, 2011";
                break;
            case 14:
            case 15:
                OSReleaseDate = "October 18, 2011";
                break;
            case 16:
            case 17:
            case 18:
                OSReleaseDate = "July 9, 2012";
                break;
            case 19:
                OSReleaseDate = "October 31, 2013";
                break;
            case 21:
            case 22:
                OSReleaseDate = "November 12, 2014";
                break;
            case 23:
                OSReleaseDate = "October 5, 2015";
                break;
            case 24:
            case 25:
                OSReleaseDate = "August 22, 2016";
                break;
            case 26:
            case 27:
                OSReleaseDate = "August 21, 2017";
                break;
            case 28:
                OSReleaseDate = "August 09, 2018";
                break;
            default:
                OSReleaseDate = "Unknown";
        }
        return OSReleaseDate;
    }

    static String GetOSName(int sdk) {
        String OSName;
        switch (sdk) {
            case 11:
            case 12:
            case 13:
                OSName = "HoneyComb";
                break;
            case 14:
            case 15:
                OSName = "Ice Cream Sandwich";
                break;
            case 16:
            case 17:
            case 18:
                OSName = "Jelly Bean";
                break;
            case 19:
                OSName = "KitKat";
                break;
            case 21:
            case 22:
                OSName = "Lollipop";
                break;
            case 23:
                OSName = "Marshmallow";
                break;
            case 24:
            case 25:
                OSName = "Nougat";
                break;
            case 26:
            case 27:
                OSName = "Oreo";
                break;
            case 28:
                OSName = "Pie";
                break;
            default:
                OSName = "Unknown";
        }
        return OSName;
    }

    static String getProcessor() {
        String Final = "";
        try {
            StringBuilder sb = new StringBuilder();
            if (new File("/proc/cpuinfo").exists()) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(new File("/proc/cpuinfo")));
                    String aLine;
                    while ((aLine = br.readLine()) != null) {
                        String _append = aLine + "ndeviceinfo";
                        sb.append(_append);
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String cpuinfo[] = sb.toString().split(":");
                for (int a = 0; a < cpuinfo.length; a++) {
                    if (cpuinfo[a].toLowerCase().contains("processor")) {
                        int getlastindex = cpuinfo[a + 1].indexOf("ndeviceinfo");
                        Final = cpuinfo[a + 1].substring(1, getlastindex);
                        break;
                    }
                }
                if (Final.equals("0") || Final.equals("")) {
                    for (int a = 0; a < cpuinfo.length; a++) {
                        if (cpuinfo[a].contains("model name")) {
                            int getlastindex = cpuinfo[a + 1].indexOf("ndeviceinfo");
                            Final = cpuinfo[a + 1].substring(1, getlastindex);
                            break;
                        }
                    }
                }
                if (Final.equals("") || Final.equals("0")) {
                    Final = "Unknown";
                }
            } else {
                Final = "Unknown";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Final;
    }

    static String getProcessorHardware() {
        String Final = "";
        try {
            StringBuilder sb = new StringBuilder();
            if (new File("/proc/cpuinfo").exists()) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(new File("/proc/cpuinfo")));
                    String aLine;
                    while ((aLine = br.readLine()) != null) {
                        String _append = aLine + "ndeviceinfo";
                        sb.append(_append);
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String cpuinfo[] = sb.toString().split(":");
                for (int a = 0; a < cpuinfo.length; a++) {
                    if (cpuinfo[a].toLowerCase().contains("hardware")) {
                        int getlastindex = cpuinfo[a + 1].indexOf("ndeviceinfo");
                        Final = cpuinfo[a + 1].substring(1, getlastindex);
                        break;
                    } else {
                        Final = "Unknown";
                    }
                }
            } else {
                Final = "Unknown";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Final;
    }

    static String getCPUGoverner() {
        String aLine = "";
        if (new File("/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor").exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File("/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor")));
                aLine = br.readLine();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return aLine;
    }

    static String getTime(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }
        return String.format(Locale.US, "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    static String getDisplaySize(Activity activity) {
        double x = 0, y = 0;
        int mWidthPixels, mHeightPixels;
        try {
            WindowManager windowManager = activity.getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getMetrics(displayMetrics);
            Point realSize = new Point();
            Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
            mWidthPixels = realSize.x;
            mHeightPixels = realSize.y;
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            x = Math.pow(mWidthPixels / dm.xdpi, 2);
            y = Math.pow(mHeightPixels / dm.ydpi, 2);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return String.format(Locale.US, "%.2f", Math.sqrt(x + y));
    }

    static String PhoneType(int gettype) {
        String Type = "";
        switch (gettype) {
            case TelephonyManager.PHONE_TYPE_CDMA:
                Type = "CDMA";
                break;
            case TelephonyManager.PHONE_TYPE_GSM:
                Type = "GSM";
                break;
            case TelephonyManager.PHONE_TYPE_NONE:
                Type = "None";
                break;
        }
        return Type;
    }

    static String NetworkType(int gettype) {
        String Type;
        switch (gettype) {
            case TelephonyManager.NETWORK_TYPE_CDMA:
                Type = "CDMA";
                break;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                Type = "EDGE";
                break;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                Type = "GPRS";
                break;
            case TelephonyManager.NETWORK_TYPE_GSM:
                Type = "GSM";
                break;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                Type = "HSDPA";
                break;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                Type = "HSPA";
                break;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                Type = "HSPAP";
                break;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                Type = "HSUPA";
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
                Type = "LTE";
                break;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                Type = "UMTS";
                break;
            default:
                Type = "Not Available";
                break;
        }
        return Type;
    }

    static boolean isRooted() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys") || canExecuteCommand("/system/xbin/which su") || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su");
    }

    private static boolean canExecuteCommand(String command) {
        try {
            int exitValue = Runtime.getRuntime().exec(command).waitFor();
            return exitValue == 0;
        } catch (Exception e) {
            return false;
        }
    }

    static String getWifiMacAddress() {
        try {
            String interfaceName = "wlan0";
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (!intf.getName().equalsIgnoreCase(interfaceName)) {
                    continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac == null) {
                    return "";
                }
                StringBuilder buf = new StringBuilder();
                for (byte aMac : mac) {
                    buf.append(String.format("%02X:", aMac));
                }
                if (buf.length() > 0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                return buf.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @SuppressLint("HardwareIds")
    static String getBluetoothMac(Context context) {
        String result = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                result = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        "bluetooth_address");
            } else {
                BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
                result = (bta != null) ? bta.getAddress() : "";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    static int getBatteryCapacity(Context context) {
        double batteryCapacity = 0;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";
        try {
            Object mPowerProfile = Class.forName(POWER_PROFILE_CLASS).getConstructor(Context.class).newInstance(context);
            batteryCapacity = (Double) Class.forName(POWER_PROFILE_CLASS).getMethod("getAveragePower", java.lang.String.class).invoke(mPowerProfile, "battery.capacity");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return (int) batteryCapacity;
    }

    static String[] getStorageDirectories(Context context) {
        String[] storageDirectories;
        String rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE");


        List<String> results = new ArrayList<>();
        File[] externalDirs = context.getExternalFilesDirs(null);
        for (File file : externalDirs) {
            String path = file.getPath().split("/Android")[0];
            if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Environment.isExternalStorageRemovable(file))
                    || rawSecondaryStoragesStr != null && rawSecondaryStoragesStr.contains(path)) {
                results.add(path);
            }
        }
        storageDirectories = results.toArray(new String[0]);

        return storageDirectories;
    }

    static Double getAndroidVersion(int sdk) {
        double Version;
        switch (sdk) {
            case 10:
                Version = 2.3;
                break;
            case 11:
                Version = 3.0;
                break;
            case 12:
                Version = 3.1;
                break;
            case 13:
                Version = 3.2;
                break;
            case 14:
            case 15:
                Version = 4.0;
                break;
            case 16:
                Version = 4.1;
                break;
            case 17:
                Version = 4.2;
                break;
            case 18:
                Version = 4.3;
                break;
            case 19:
                Version = 4.4;
                break;
            case 21:
                Version = 5.0;
                break;
            case 22:
                Version = 5.1;
                break;
            case 23:
                Version = 6.0;
                break;
            case 24:
                Version = 7.0;
                break;
            case 25:
                Version = 7.1;
                break;
            case 26:
                Version = 8.0;
                break;
            case 27:
                Version = 8.1;
                break;
            case 28:
                Version = 9.0;
                break;
            default:
                Version = 0.0;
                break;
        }
        return Version;
    }

    static String GetSELinuxMode() {
        StringBuilder output = new StringBuilder();
        Process p;
        try {
            p = Runtime.getRuntime().exec("getenforce");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Not Supported";
        }
        String response = output.toString();
        if ("Enforcing".equals(response)) {
            return "Enforcing";
        } else if ("Permissive".equals(response)) {
            return "Permissive";
        } else {
            return "Unable to determine";
        }
    }

    static String GetSensorType(int type) {
        String stype;
        switch (type) {
            case 1:
                stype = "ACCELEROMETER";
                break;
            case 2:
                stype = "MAGNETIC FIELD";
                break;
            case 3:
                stype = "ORIENTATION";
                break;
            case 4:
                stype = "GYROSCOPE";
                break;
            case 5:
                stype = "LIGHT";
                break;
            case 6:
                stype = "PRESSURE";
                break;
            case 7:
                stype = "TEMPERATURE";
                break;
            case 8:
                stype = "PROXIMITY";
                break;
            case 9:
                stype = "GRAVITY";
                break;
            case 10:
                stype = "LINEAR ACCELERATION";
                break;
            case 11:
                stype = "ROTATION VECTOR";
                break;
            case 12:
                stype = "RELATIVE HUMIDITY";
                break;
            case 13:
                stype = "AMBIENT TEMPERATURE";
                break;
            case 14:
                stype = "MAGNETIC FIELD UNCALIBRATED";
                break;
            case 15:
                stype = "GAME ROTATION VECTOR";
                break;
            case 16:
                stype = "GYROSCOPE UNCALIBRATED";
                break;
            case 17:
                stype = "SIGNIFICANT MOTION";
                break;
            case 18:
                stype = "STEP DETECTOR";
                break;
            case 19:
                stype = "STEP COUNTER";
                break;
            case 20:
                stype = "GEOMAGNETIC ROTATION VECTOR";
                break;
            case 21:
                stype = "HEART_RATE";
                break;
            case 22:
                stype = "TILT DETECTOR";
                break;
            case 23:
                stype = "WAKE GESTURE";
                break;
            case 24:
                stype = "GLANCE_GESTURE";
                break;
            case 25:
                stype = "PICK_UP_GESTURE";
                break;
            case 26:
                stype = "WRIST_TILT_GESTURE";
                break;
            case 27:
                stype = "DEVICE_ORIENTATION ";
                break;
            case 28:
                stype = "POSE 6DOF";
                break;
            case 29:
                stype = "STATIONARY DETECT";
                break;
            case 30:
                stype = "MOTION DETECT";
                break;
            case 31:
                stype = "HEART BEAT";
                break;
            case 32:
                stype = "DYNAMIC_SENSOR_META";
                break;
            case 33:
                stype = "ADDITIONAL_INFO";
                break;
            case 34:
                stype = "LOW LATENCY OFFBODY DETECT";
                break;
            case 35:
                stype = "ACCELEROMETER UNCALIBRATED";
                break;
            default:
                stype = "Unknown";
                break;
        }
        return stype;
    }

    public static int getDarkColor(Context context, int color) {
        List<String> colorThemeColor = Arrays.asList(context.getResources().getStringArray(R.array.accent_colors));
        List<String> colorThemeColorDark = Arrays.asList(context.getResources().getStringArray(R.array.accent_colors_700));
        String getHex = String.format("#%02x%02x%02x", Color.red(color), Color.green(color), Color.blue(color));
        return Color.parseColor(colorThemeColorDark.get(colorThemeColor.indexOf(getHex)));
    }

    static int getDarkColor2(Context context, int color) {
        List<String> colorThemeColor = Arrays.asList(context.getResources().getStringArray(R.array.accent_colors));
        List<String> colorThemeColor2 = Arrays.asList(context.getResources().getStringArray(R.array.accent_colors_2));
        String getHex = String.format("#%02x%02x%02x", Color.red(color), Color.green(color), Color.blue(color));
        return Color.parseColor(colorThemeColor2.get(colorThemeColor.indexOf(getHex)));
    }

    static void copy(File src, File dst) {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static String getKeyName(String name) {
        String keyName = "";
        switch (name) {
            case "android.colorCorrection.availableAberrationModes":
                keyName = "Aberration Modes";
                break;
            case "android.control.aeAvailableAntibandingModes":
                keyName = "Antibanding Modes";
                break;
            case "android.control.aeAvailableModes":
                keyName = "Auto Exposure Modes";
                break;
            case "android.control.aeAvailableTargetFpsRanges":
                keyName = "Target FPS Ranges";
                break;
            case "android.control.aeCompensationRange":
                keyName = "Compensation Range";
                break;
            case "android.control.aeCompensationStep":
                keyName = "Compensation Step";
                break;
            case "android.control.aeLockAvailable":
                keyName = "Auto Exposure Lock";
                break;
            case "android.control.afAvailableModes":
                keyName = "AutoFocus Modes";
                break;
            case "android.control.availableEffects":
                keyName = "Effects";
                break;
            case "android.control.availableModes":
                keyName = "Available Modes";
                break;
            case "android.control.availableSceneModes":
                keyName = "Scene Modes";
                break;
            case "android.control.availableVideoStabilizationModes":
                keyName = "Video Stabilization Modes";
                break;
            case "android.control.awbAvailableModes":
                keyName = "Auto White Balance Modes";
                break;
            case "android.control.awbLockAvailable":
                keyName = "Auto White Balance Lock";
                break;
            case "android.control.maxRegionsAe":
                keyName = "Max Auto Exposure Regions";
                break;
            case "android.control.maxRegionsAf":
                keyName = "Max Auto Focus Regions";
                break;
            case "android.control.maxRegionsAwb":
                keyName = "Max Auto White Balance Regions";
                break;
            case "android.edge.availableEdgeModes":
                keyName = "Edge Modes";
                break;
            case "android.flash.info.available":
                keyName = "Flash Available";
                break;
            case "android.hotPixel.availableHotPixelModes":
                keyName = "Hot Pixel Modes";
                break;
            case "android.info.supportedHardwareLevel":
                keyName = "Hardware Level";
                break;
            case "android.jpeg.availableThumbnailSizes":
                keyName = "Thumbnail Sizes";
                break;
            case "android.lens.facing":
                keyName = "Lens Placement";
                break;
            case "android.lens.info.availableApertures":
                keyName = "Apertures";
                break;
            case "android.lens.info.availableFilterDensities":
                keyName = "Filter Densities";
                break;
            case "android.lens.info.availableFocalLengths":
                keyName = "Focal Lengths";
                break;
            case "android.lens.info.availableOpticalStabilization":
                keyName = "Optical Stabilization";
                break;
            case "android.lens.info.focusDistanceCalibration":
                keyName = "Focus Distance Calibration";
                break;
            case "android.lens.info.hyperfocalDistance":
                keyName = "Hyperfocal Distance";
                break;
            case "android.lens.info.minimumFocusDistance":
                keyName = "Minimum Focus Distance";
                break;
            case "android.noiseReduction.availableNoiseReductionModes":
                keyName = "Noise Reduction Modes";
                break;
            case "android.request.availableCapabilities":
                keyName = "Camera Capabilities";
                break;
            case "android.request.maxNumInputStreams":
                keyName = "Maximum Input Streams";
                break;
            case "android.request.maxNumOutputProc":
                keyName = "Maximum Output Streams";
                break;
            case "android.request.maxNumOutputProcStalling":
                keyName = "Maximum Output Streams Stalling";
                break;
            case "android.request.maxNumOutputRaw":
                keyName = "Maximum RAW Output Streams";
                break;
            case "android.request.partialResultCount":
                keyName = "Partial Results";
                break;
            case "android.request.pipelineMaxDepth":
                keyName = "Maximum Pipeline Depth";
                break;
            case "android.scaler.availableMaxDigitalZoom":
                keyName = "Maximum Digital Zoom";
                break;
            case "android.scaler.croppingType":
                keyName = "Cropping Type";
                break;
            case "android.scaler.streamConfigurationMap":
                keyName = "Supported Resolutions";
                break;
            case "android.sensor.availableTestPatternModes":
                keyName = "Test Pattern Modes";
                break;
            case "android.sensor.blackLevelPattern":
                keyName = "Black Level Pattern";
                break;
            case "android.sensor.info.activeArraySize":
                keyName = "Active Array Size";
                break;
            case "android.sensor.info.colorFilterArrangement":
                keyName = "Color Filter Arrangement";
                break;
            case "android.sensor.info.exposureTimeRange":
                keyName = "Exposure Time Range";
                break;
            case "android.sensor.info.maxFrameDuration":
                keyName = "Maximum Frame Duration";
                break;
            case "android.sensor.info.physicalSize":
                keyName = "Sensor Size";
                break;
            case "android.sensor.info.pixelArraySize":
                keyName = "Pixel Array Size";
                break;
            case "android.sensor.info.preCorrectionActiveArraySize":
                keyName = "Pre Correction Active Array Size";
                break;
            case "android.sensor.info.sensitivityRange":
                keyName = "Sensitivity Range";
                break;
            case "android.sensor.info.timestampSource":
                keyName = "Timestamp Source";
                break;
            case "android.sensor.info.whiteLevel":
                keyName = "White Level";
                break;
            case "android.sensor.maxAnalogSensitivity":
                keyName = "Maximum Analog Sensitivity";
                break;
            case "android.sensor.orientation":
                keyName = "Orientation";
                break;
            case "android.shading.availableModes":
                keyName = "Shading Modes";
                break;
            case "android.statistics.info.availableFaceDetectModes":
                keyName = "Face Detection Modes";
                break;
            case "android.statistics.info.availableHotPixelMapModes":
                keyName = "Hot Pixel Map Modes";
                break;
            case "android.statistics.info.availableLensShadingMapModes":
                keyName = "Lens Shading Map Modes";
                break;
            case "android.statistics.info.maxFaceCount":
                keyName = "Maximum Faces Detectable";
                break;
            case "android.sync.maxLatency":
                keyName = "Maximum Latency";
                break;
            case "android.tonemap.availableToneMapModes":
                keyName = "Tone Map Modes";
                break;
            case "android.tonemap.maxCurvePoints":
                keyName = "Maximum Curve Points";
                break;
            default:
                keyName = name;
                break;
        }
        return keyName;
    }

    static String getCameraMP(Size[] size) {
        Size first = size[0];
        if (size.length > 1) {
            Size second = size[size.length - 1];
            if (first.getWidth() > second.getWidth()) {
                return getMP(first, 1);
            } else {
                return getMP(second, 1);
            }
        } else {
            return getMP(first, 1);
        }
    }

    static String getCameraResolution(Size[] size) {
        Size first = size[0];
        if (size.length > 1) {
            Size second = size[size.length - 1];
            if (first.getWidth() > second.getWidth()) {
                return first.getWidth() + "x" + first.getHeight();
            } else {
                return second.getWidth() + "x" + second.getHeight();
            }
        } else {
            return first.getWidth() + "x" + first.getHeight();
        }
    }

    static String getMP(Size size, int decimalPlaces) {
        float mp = (size.getWidth() * size.getHeight()) / 1000000f;
        if (decimalPlaces == 1) {
            return String.format(Locale.US, "%.1f", mp) + " MP";
        } else if (decimalPlaces == 2) {
            return String.format(Locale.US, "%.2f", mp) + " MP";
        } else {
            return String.format(Locale.US, "%.2f", mp) + " MP";
        }
    }

    static String getFormattedTemp(String zoneValue){
        double finalTemp;
        int val = Integer.parseInt(zoneValue.trim());
        if(val>=10000){
            finalTemp = val/1000.0;
        }else if (val>=1000){
            finalTemp = val/100.0;
        }else if(val>100){
            finalTemp = val/10.0;
        }else{
            finalTemp = val;
        }
        return new DecimalFormat("##.#").format(finalTemp)+" \u2103";
    }
}
