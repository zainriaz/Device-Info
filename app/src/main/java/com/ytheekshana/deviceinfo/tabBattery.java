package com.ytheekshana.deviceinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.BatteryManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

public class tabBattery extends Fragment {
    LinearLayout llayout;
    String battechno;
    int TextDisColor, LineColor;
    int batlevel, batvoltage, battemperature, batstatus, batpowersource, bathealth;
    TextView txtBatteryLeveldis, txtBatteryStatusdis, txtPowerSourcedis, txtBatteryHealthdis, txtTechnologydis, txtTemperaturedis, txtBatteryVoltagedis, txtBatteryCapacitydis;
    BatteryManager mBatteryManager;
    Context BatteryContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabbattery, container, false);
        llayout = rootView.findViewById(R.id.llayout);

        try {
            TextDisColor = MainActivity.themeColor;
            LineColor = GetDetails.getThemeColor(Objects.requireNonNull(getContext()), R.attr.colorButtonNormal);

            IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            BatteryContext = Objects.requireNonNull(getActivity()).getApplicationContext();
            BatteryContext.registerReceiver(mBroadcastReceiver, iFilter);
            mBatteryManager = (BatteryManager) getContext().getSystemService(Context.BATTERY_SERVICE);

            TextView txtBatteryHealth = new TextView(getContext());
            txtBatteryHealthdis = new TextView(getContext());
            View v = new View(getContext());
            v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v.setBackgroundColor(LineColor);
            txtBatteryHealth.setText(R.string.Health);
            txtBatteryHealth.setTypeface(null, Typeface.BOLD);
            txtBatteryHealth.setTextSize(16);
            txtBatteryHealthdis.setPadding(0, 0, 0, 15);
            txtBatteryHealthdis.setTextColor(TextDisColor);
            txtBatteryHealthdis.setTextSize(16);
            txtBatteryHealthdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtBatteryHealth.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtBatteryHealth);
            llayout.addView(txtBatteryHealthdis);
            llayout.addView(v);

            TextView txtBatteryLevel = new TextView(getContext());
            txtBatteryLeveldis = new TextView(getContext());
            View v1 = new View(getContext());
            v1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v1.setBackgroundColor(LineColor);
            txtBatteryLevel.setText(R.string.Level);
            txtBatteryLevel.setTypeface(null, Typeface.BOLD);
            txtBatteryLevel.setTextSize(16);
            txtBatteryLeveldis.setPadding(0, 0, 0, 15);
            txtBatteryLeveldis.setTextColor(TextDisColor);
            txtBatteryLeveldis.setTextSize(16);
            txtBatteryLeveldis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtBatteryLevel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtBatteryLevel);
            llayout.addView(txtBatteryLeveldis);
            llayout.addView(v1);

            TextView txtBatteryStatus = new TextView(getContext());
            txtBatteryStatusdis = new TextView(getContext());
            View v2 = new View(getContext());
            v2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v2.setBackgroundColor(LineColor);
            txtBatteryStatus.setText(R.string.Status);
            txtBatteryStatus.setTypeface(null, Typeface.BOLD);
            txtBatteryStatus.setTextSize(16);
            txtBatteryStatus.setPadding(0, 15, 0, 0);
            txtBatteryStatusdis.setPadding(0, 0, 0, 15);
            txtBatteryStatusdis.setTextColor(TextDisColor);
            txtBatteryStatusdis.setTextSize(16);
            txtBatteryStatusdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtBatteryStatus.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtBatteryStatus);
            llayout.addView(txtBatteryStatusdis);
            llayout.addView(v2);

            TextView txtPowerSource = new TextView(getContext());
            txtPowerSourcedis = new TextView(getContext());
            View v3 = new View(getContext());
            v3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v3.setBackgroundColor(LineColor);
            txtPowerSource.setText(R.string.PowerSource);
            txtPowerSource.setTypeface(null, Typeface.BOLD);
            txtPowerSource.setTextSize(16);
            txtPowerSource.setPadding(0, 15, 0, 0);
            txtPowerSourcedis.setPadding(0, 0, 0, 15);
            txtPowerSourcedis.setTextColor(TextDisColor);
            txtPowerSourcedis.setTextSize(16);
            txtPowerSourcedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtBatteryStatus.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtPowerSource);
            llayout.addView(txtPowerSourcedis);
            llayout.addView(v3);

            TextView txtTechnology = new TextView(getContext());
            txtTechnologydis = new TextView(getContext());
            View v4 = new View(getContext());
            v4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v4.setBackgroundColor(LineColor);
            txtTechnology.setText(R.string.Technology);
            txtTechnology.setTypeface(null, Typeface.BOLD);
            txtTechnology.setTextSize(16);
            txtTechnology.setPadding(0, 15, 0, 0);
            txtTechnologydis.setPadding(0, 0, 0, 15);
            txtTechnologydis.setTextColor(TextDisColor);
            txtTechnologydis.setTextSize(16);
            txtTechnologydis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtTechnology.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtTechnology);
            llayout.addView(txtTechnologydis);
            llayout.addView(v4);

            TextView txtTemperature = new TextView(getContext());
            txtTemperaturedis = new TextView(getContext());
            View v5 = new View(getContext());
            v5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v5.setBackgroundColor(LineColor);
            txtTemperature.setText(R.string.Temperature);
            txtTemperature.setTypeface(null, Typeface.BOLD);
            txtTemperature.setTextSize(16);
            txtTemperature.setPadding(0, 15, 0, 0);
            txtTemperaturedis.setPadding(0, 0, 0, 15);
            txtTemperaturedis.setTextColor(TextDisColor);
            txtTemperaturedis.setTextSize(16);
            txtTemperaturedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtTemperature.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtTemperature);
            llayout.addView(txtTemperaturedis);
            llayout.addView(v5);

            TextView txtBatteryVoltage = new TextView(getContext());
            txtBatteryVoltagedis = new TextView(getContext());
            View v6 = new View(getContext());
            v6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v6.setBackgroundColor(LineColor);
            txtBatteryVoltage.setText(R.string.Voltage);
            txtBatteryVoltage.setTypeface(null, Typeface.BOLD);
            txtBatteryVoltage.setTextSize(16);
            txtBatteryVoltage.setPadding(0, 15, 0, 0);
            txtBatteryVoltagedis.setPadding(0, 0, 0, 15);
            txtBatteryVoltagedis.setTextColor(TextDisColor);
            txtBatteryVoltagedis.setTextSize(16);
            txtBatteryVoltagedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtBatteryVoltage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtBatteryVoltage);
            llayout.addView(txtBatteryVoltagedis);
            llayout.addView(v6);

            TextView txtBatteryCapacity = new TextView(getContext());
            txtBatteryCapacitydis = new TextView(getContext());
            View v7 = new View(getContext());
            v7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v7.setBackgroundColor(LineColor);
            txtBatteryCapacity.setText(R.string.Capacity);
            txtBatteryCapacity.setTypeface(null, Typeface.BOLD);
            txtBatteryCapacity.setTextSize(16);
            txtBatteryCapacity.setPadding(0, 15, 0, 0);
            txtBatteryCapacitydis.setPadding(0, 0, 0, 15);
            txtBatteryCapacitydis.setTextColor(TextDisColor);
            txtBatteryCapacitydis.setTextSize(16);
            String BatCap = SplashActivity.batteryCapacity + " mAh";
            txtBatteryCapacitydis.setText(BatCap);
            txtBatteryCapacitydis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtBatteryCapacity.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtBatteryCapacity);
            llayout.addView(txtBatteryCapacitydis);
            llayout.addView(v7);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rootView;
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String batstatusdis = "", batpowersourcedis, bathealthdis = "";
                batlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                batvoltage = intent.getIntExtra("voltage", 0);
                battemperature = (intent.getIntExtra("temperature", 0)) / 10;
                batstatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                batpowersource = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                bathealth = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
                battechno = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);

                if (batstatus == BatteryManager.BATTERY_STATUS_CHARGING) {
                    batstatusdis = "Charging";
                } else if (batstatus == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                    batstatusdis = "Discharging";
                } else if (batstatus == BatteryManager.BATTERY_STATUS_FULL) {
                    batstatusdis = "Battery Full";
                } else if (batstatus == BatteryManager.BATTERY_STATUS_UNKNOWN) {
                    batstatusdis = "Unknown";
                } else if (batstatus == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                    batstatusdis = "Not Charging";
                }

                if (batpowersource == BatteryManager.BATTERY_PLUGGED_USB) {
                    batpowersourcedis = "USB Port";
                } else if (batpowersource == BatteryManager.BATTERY_PLUGGED_AC) {
                    batpowersourcedis = "AC";
                } else {
                    batpowersourcedis = "Battery";
                }

                if (bathealth == BatteryManager.BATTERY_HEALTH_COLD) {
                    bathealthdis = "Cold";
                } else if (bathealth == BatteryManager.BATTERY_HEALTH_DEAD) {
                    bathealthdis = "Dead";
                } else if (bathealth == BatteryManager.BATTERY_HEALTH_GOOD) {
                    bathealthdis = "Good";
                } else if (bathealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                    bathealthdis = "Over Voltage";
                } else if (bathealth == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                    bathealthdis = "Overheat";
                } else if (bathealth == BatteryManager.BATTERY_HEALTH_UNKNOWN) {
                    bathealthdis = "Unknown";
                } else if (bathealth == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
                    bathealthdis = "Failure";
                }

                String battery_level = String.valueOf(batlevel) + "%";
                txtBatteryLeveldis.setText(battery_level);
                txtBatteryStatusdis.setText(batstatusdis);
                txtPowerSourcedis.setText(batpowersourcedis);
                txtBatteryHealthdis.setText(bathealthdis);
                txtTechnologydis.setText(battechno);

                String batvol = String.valueOf(battemperature) + " \u2103";
                String battemp = String.valueOf(batvoltage) + " mV";
                txtTemperaturedis.setText(batvol);
                txtBatteryVoltagedis.setText(battemp);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };
}
