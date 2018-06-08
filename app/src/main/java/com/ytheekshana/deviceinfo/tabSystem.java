package com.ytheekshana.deviceinfo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class tabSystem extends Fragment {
    LinearLayout llayout;
    String LineColor = "#B3B3B3", TextDisColor = "#023071";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabsystem, container, false);
        llayout = rootView.findViewById(R.id.llayout);
        ImageView imgAndroidLogo = rootView.findViewById(R.id.imgAndroidLogo);
        TextView txtAndroidVersionBig = rootView.findViewById(R.id.txtAndroidVersionBig);
        try {
            switch (Build.VERSION.SDK_INT) {
                case 21:
                case 22:
                    imgAndroidLogo.setImageResource(R.drawable.lollipop);
                    break;
                case 23:
                    imgAndroidLogo.setImageResource(R.drawable.marshmallow);
                    break;
                case 24:
                case 25:
                    imgAndroidLogo.setImageResource(R.drawable.nougat);
                    break;
                case 26:
                case 27:
                    imgAndroidLogo.setImageResource(R.drawable.oreo);
                    break;
            }
            String Aversion = "Android " + Build.VERSION.RELEASE + " " + GetDetails.GetOSName(Build.VERSION.SDK_INT);
            txtAndroidVersionBig.setText(Aversion);

            txtAndroidVersionBig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            ActivityManager am = (ActivityManager) Objects.requireNonNull(getActivity()).getSystemService(Context.ACTIVITY_SERVICE);
            ConfigurationInfo info = null;
            if (am != null) {
                info = am.getDeviceConfigurationInfo();
            }
            String GLVersion = null;
            if (info != null) {
                GLVersion = info.getGlEsVersion();
            }

            TextView txtAndroidName = new TextView(getContext());
            TextView txtAndroidNamedis = new TextView(getContext());
            View v1 = new View(getContext());
            v1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v1.setBackgroundColor(Color.parseColor(LineColor));
            txtAndroidName.setText(R.string.CodeName);
            txtAndroidName.setTypeface(null, Typeface.BOLD);
            txtAndroidName.setTextSize(16);
            txtAndroidName.setPadding(0, 15, 0, 0);
            txtAndroidNamedis.setPadding(0, 0, 0, 15);
            txtAndroidNamedis.setTextColor(Color.parseColor(TextDisColor));
            txtAndroidNamedis.setTextSize(16);
            txtAndroidNamedis.setText(GetDetails.GetOSNameAdvanced());
            txtAndroidNamedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtAndroidName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtAndroidName);
            llayout.addView(txtAndroidNamedis);
            llayout.addView(v1);

            TextView txtAPILevel = new TextView(getContext());
            TextView txtAPILeveldis = new TextView(getContext());
            View v2 = new View(getContext());
            v2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v2.setBackgroundColor(Color.parseColor(LineColor));
            txtAPILevel.setText(R.string.APILevel);
            txtAPILevel.setTypeface(null, Typeface.BOLD);
            txtAPILevel.setTextSize(16);
            txtAPILevel.setPadding(0, 15, 0, 0);
            txtAPILeveldis.setPadding(0, 0, 0, 15);
            txtAPILeveldis.setTextColor(Color.parseColor(TextDisColor));
            txtAPILeveldis.setTextSize(16);
            txtAPILeveldis.setText(String.valueOf(Build.VERSION.SDK_INT));
            txtAPILeveldis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtAPILevel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtAPILevel);
            llayout.addView(txtAPILeveldis);
            llayout.addView(v2);

            if (Build.VERSION.SDK_INT >= 23) {
                TextView txtSecurityPatch = new TextView(getContext());
                TextView txtSecurityPatchdis = new TextView(getContext());
                View v3 = new View(getContext());
                v3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
                v3.setBackgroundColor(Color.parseColor(LineColor));
                txtSecurityPatch.setText(R.string.SecurityPatchLevel);
                txtSecurityPatch.setTypeface(null, Typeface.BOLD);
                txtSecurityPatch.setTextSize(16);
                txtSecurityPatch.setPadding(0, 15, 0, 0);
                txtSecurityPatchdis.setPadding(0, 0, 0, 15);
                txtSecurityPatchdis.setTextColor(Color.parseColor(TextDisColor));
                txtSecurityPatchdis.setTextSize(16);
                txtSecurityPatchdis.setText(Build.VERSION.SECURITY_PATCH);
                txtSecurityPatchdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                txtSecurityPatch.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                llayout.addView(txtSecurityPatch);
                llayout.addView(txtSecurityPatchdis);
                llayout.addView(v3);
            }

            TextView txtBootloader = new TextView(getContext());
            TextView txtBootloaderdis = new TextView(getContext());
            View v4 = new View(getContext());
            v4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v4.setBackgroundColor(Color.parseColor(LineColor));
            txtBootloader.setText(R.string.Bootloader);
            txtBootloader.setTypeface(null, Typeface.BOLD);
            txtBootloader.setTextSize(16);
            txtBootloader.setPadding(0, 15, 0, 0);
            txtBootloaderdis.setPadding(0, 0, 0, 15);
            txtBootloaderdis.setTextColor(Color.parseColor(TextDisColor));
            txtBootloaderdis.setTextSize(16);
            txtBootloaderdis.setText(Build.BOOTLOADER);
            txtBootloaderdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtBootloader.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtBootloader);
            llayout.addView(txtBootloaderdis);
            llayout.addView(v4);

            TextView txtBuildNumber = new TextView(getContext());
            TextView txtBuildNumberdis = new TextView(getContext());
            View v5 = new View(getContext());
            v5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v5.setBackgroundColor(Color.parseColor(LineColor));
            txtBuildNumber.setText(R.string.BuildNumber);
            txtBuildNumber.setTypeface(null, Typeface.BOLD);
            txtBuildNumber.setTextSize(16);
            txtBuildNumber.setPadding(0, 15, 0, 0);
            txtBuildNumberdis.setPadding(0, 0, 0, 15);
            txtBuildNumberdis.setTextColor(Color.parseColor(TextDisColor));
            txtBuildNumberdis.setTextSize(16);
            txtBuildNumberdis.setText(Build.DISPLAY);
            txtBuildNumberdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtBuildNumber.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtBuildNumber);
            llayout.addView(txtBuildNumberdis);
            llayout.addView(v5);

            TextView txtRadioVersion = new TextView(getContext());
            TextView txtRadioVersiondis = new TextView(getContext());
            View v6 = new View(getContext());
            v6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v6.setBackgroundColor(Color.parseColor(LineColor));
            txtRadioVersion.setText(R.string.Baseband);
            txtRadioVersion.setTypeface(null, Typeface.BOLD);
            txtRadioVersion.setTextSize(16);
            txtRadioVersion.setPadding(0, 15, 0, 0);
            txtRadioVersiondis.setPadding(0, 0, 0, 15);
            txtRadioVersiondis.setTextColor(Color.parseColor(TextDisColor));
            txtRadioVersiondis.setTextSize(16);
            txtRadioVersiondis.setText(Build.getRadioVersion());
            txtRadioVersiondis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtRadioVersion.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtRadioVersion);
            llayout.addView(txtRadioVersiondis);
            llayout.addView(v6);

            TextView txtAndroidRuntime = new TextView(getContext());
            TextView txtAndroidRuntimedis = new TextView(getContext());
            View v7 = new View(getContext());
            v7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v7.setBackgroundColor(Color.parseColor(LineColor));
            txtAndroidRuntime.setText(R.string.AndroidRuntime);
            txtAndroidRuntime.setTypeface(null, Typeface.BOLD);
            txtAndroidRuntime.setTextSize(16);
            txtAndroidRuntime.setPadding(0, 15, 0, 0);
            txtAndroidRuntimedis.setPadding(0, 0, 0, 15);
            txtAndroidRuntimedis.setTextColor(Color.parseColor(TextDisColor));
            txtAndroidRuntimedis.setTextSize(16);
            txtAndroidRuntimedis.setText(System.getProperty("java.vm.version"));
            txtAndroidRuntimedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtAndroidRuntime.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtAndroidRuntime);
            llayout.addView(txtAndroidRuntimedis);
            llayout.addView(v7);

            TextView txtKernelVersion = new TextView(getContext());
            TextView txtKernelVersiondis = new TextView(getContext());
            View v8 = new View(getContext());
            v8.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v8.setBackgroundColor(Color.parseColor(LineColor));
            txtKernelVersion.setText(R.string.Kernel);
            txtKernelVersion.setTypeface(null, Typeface.BOLD);
            txtKernelVersion.setTextSize(16);
            txtKernelVersion.setPadding(0, 15, 0, 0);
            txtKernelVersiondis.setPadding(0, 0, 0, 15);
            txtKernelVersiondis.setTextColor(Color.parseColor(TextDisColor));
            txtKernelVersiondis.setTextSize(16);
            txtKernelVersiondis.setText(System.getProperty("os.version"));
            txtKernelVersiondis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtKernelVersion.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtKernelVersion);
            llayout.addView(txtKernelVersiondis);
            llayout.addView(v8);

            TextView txtOpenGLES = new TextView(getContext());
            TextView txtOpenGLESdis = new TextView(getContext());
            View v9 = new View(getContext());
            v9.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v9.setBackgroundColor(Color.parseColor(LineColor));
            txtOpenGLES.setText(R.string.OpenGL);
            txtOpenGLES.setTypeface(null, Typeface.BOLD);
            txtOpenGLES.setTextSize(16);
            txtOpenGLES.setPadding(0, 15, 0, 0);
            txtOpenGLESdis.setPadding(0, 0, 0, 15);
            txtOpenGLESdis.setTextColor(Color.parseColor(TextDisColor));
            txtOpenGLESdis.setTextSize(16);
            txtOpenGLESdis.setText(GLVersion);
            txtOpenGLESdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtOpenGLES.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtOpenGLES);
            llayout.addView(txtOpenGLESdis);
            llayout.addView(v9);

            TextView txtRootAccess = new TextView(getContext());
            TextView txtRootAccessdis = new TextView(getContext());
            View v12 = new View(getContext());
            v12.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v12.setBackgroundColor(Color.parseColor(LineColor));
            txtRootAccess.setText(R.string.RootAccess);
            txtRootAccess.setTypeface(null, Typeface.BOLD);
            txtRootAccess.setTextSize(16);
            txtRootAccess.setPadding(0, 15, 0, 0);
            txtRootAccessdis.setPadding(0, 0, 0, 15);
            txtRootAccessdis.setTextColor(Color.parseColor(TextDisColor));
            txtRootAccessdis.setTextSize(16);
            txtRootAccessdis.setText(GetDetails.isRooted());
            txtRootAccessdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtRootAccess.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtRootAccess);
            llayout.addView(txtRootAccessdis);
            llayout.addView(v12);

            TextView txtSelinux = new TextView(getContext());
            TextView txtSelinuxdis = new TextView(getContext());
            View v14 = new View(getContext());
            v14.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v14.setBackgroundColor(Color.parseColor(LineColor));
            txtSelinux.setText(R.string.SELinux);
            txtSelinux.setTypeface(null, Typeface.BOLD);
            txtSelinux.setTextSize(16);
            txtSelinux.setPadding(0, 15, 0, 0);
            txtSelinuxdis.setPadding(0, 0, 0, 15);
            txtSelinuxdis.setTextColor(Color.parseColor(TextDisColor));
            txtSelinuxdis.setTextSize(16);
            txtSelinuxdis.setText(GetDetails.GetSELinuxMode());
            txtSelinuxdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtSelinux.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtSelinux);
            llayout.addView(txtSelinuxdis);
            llayout.addView(v14);

            TextView txtSystemUptime = new TextView(getContext());
            final TextView txtSystemUptimedis = new TextView(getContext());
            View v13 = new View(getContext());
            v13.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v13.setBackgroundColor(Color.parseColor(LineColor));
            txtSystemUptime.setText(R.string.Uptime);
            txtSystemUptime.setTypeface(null, Typeface.BOLD);
            txtSystemUptime.setTextSize(16);
            txtSystemUptime.setPadding(0, 15, 0, 0);
            txtSystemUptimedis.setPadding(0, 0, 0, 15);
            txtSystemUptimedis.setTextColor(Color.parseColor(TextDisColor));
            txtSystemUptimedis.setTextSize(16);
            txtSystemUptimedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtSystemUptime.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtSystemUptime);
            llayout.addView(txtSystemUptimedis);
            llayout.addView(v13);

            final Handler h = new Handler();
            h.postDelayed(new Runnable() {
                Long uptime;

                @Override
                public void run() {
                    uptime = SystemClock.elapsedRealtime();
                    txtSystemUptimedis.setText(GetDetails.getTime(uptime));
                    h.postDelayed(this, 1000);
                }
            }, 1000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rootView;
    }
}

