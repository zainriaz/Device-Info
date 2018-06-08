package com.ytheekshana.deviceinfo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class tabDevice extends Fragment {
    LinearLayout llayout;
    String LineColor = "#B3B3B3", TextDisColor = "#023071";
    public static final int REQUEST_PHONE_STATE = 1;
    TextView txtDeviceTypedis, txtIMEIdis, txtSIMSerialdis, txtSIMSubscriberdis, txtNetworkOperatordis, txtNetworkTypedis,txtWIFIMACAddressdis,txtBluetoothMACAddressdis,txtBuildFingerPrintdis;
    TelephonyManager tm;

    @SuppressLint("HardwareIds")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabdevice, container, false);
        llayout = rootView.findViewById(R.id.llayout);
        try {
            tm = (TelephonyManager) Objects.requireNonNull(getActivity()).getSystemService(Context.TELEPHONY_SERVICE);
            //BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
            //String deviceName = myDevice.getName();


            String deviceName = GetDetails.GetFromBuildProp("ro.semc.product.name");

            TextView txtName = new TextView(getContext());
            TextView txtNamedis = new TextView(getContext());
            View v20 = new View(getContext());
            v20.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v20.setBackgroundColor(Color.parseColor(LineColor));
            txtName.setText(R.string.DeviceName);
            txtName.setTypeface(null, Typeface.BOLD);
            txtName.setTextSize(16);
            txtNamedis.setPadding(0, 0, 0, 15);
            txtNamedis.setTextColor(Color.parseColor(TextDisColor));
            txtNamedis.setTextSize(16);
            txtNamedis.setText(deviceName);
            txtNamedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtName);
            llayout.addView(txtNamedis);
            llayout.addView(v20);

            TextView txtModel = new TextView(getContext());
            TextView txtModeldis = new TextView(getContext());
            View v = new View(getContext());
            v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v.setBackgroundColor(Color.parseColor(LineColor));
            txtModel.setText(R.string.Model);
            txtModel.setTypeface(null, Typeface.BOLD);
            txtModel.setTextSize(16);
            txtModel.setPadding(0, 15, 0, 0);
            txtModeldis.setPadding(0, 0, 0, 15);
            txtModeldis.setTextColor(Color.parseColor(TextDisColor));
            txtModeldis.setTextSize(16);
            txtModeldis.setText(Build.MODEL);
            txtModeldis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtModel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtModel);
            llayout.addView(txtModeldis);
            llayout.addView(v);

            TextView txtManufacturer = new TextView(getContext());
            TextView txtManufacturerdis = new TextView(getContext());
            View v1 = new View(getContext());
            v1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v1.setBackgroundColor(Color.parseColor(LineColor));
            txtManufacturer.setText(R.string.Manufacturer);
            txtManufacturer.setTypeface(null, Typeface.BOLD);
            txtManufacturer.setTextSize(16);
            txtManufacturer.setPadding(0, 15, 0, 0);
            txtManufacturerdis.setPadding(0, 0, 0, 15);
            txtManufacturerdis.setTextColor(Color.parseColor(TextDisColor));
            txtManufacturerdis.setTextSize(16);
            txtManufacturerdis.setText(Build.MANUFACTURER);
            txtManufacturerdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtManufacturer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtManufacturer);
            llayout.addView(txtManufacturerdis);
            llayout.addView(v1);

            TextView txtDevice = new TextView(getContext());
            TextView txtDevicedis = new TextView(getContext());
            View v2 = new View(getContext());
            v2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v2.setBackgroundColor(Color.parseColor(LineColor));
            txtDevice.setText(R.string.Device);
            txtDevice.setTypeface(null, Typeface.BOLD);
            txtDevice.setTextSize(16);
            txtDevice.setPadding(0, 15, 0, 0);
            txtDevicedis.setPadding(0, 0, 0, 15);
            txtDevicedis.setTextColor(Color.parseColor(TextDisColor));
            txtDevicedis.setTextSize(16);
            txtDevicedis.setText(Build.MANUFACTURER);
            txtDevicedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtDevice.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtDevice);
            llayout.addView(txtDevicedis);
            llayout.addView(v2);

            TextView txtBoard = new TextView(getContext());
            TextView txtBoarddis = new TextView(getContext());
            View v3 = new View(getContext());
            v3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v3.setBackgroundColor(Color.parseColor(LineColor));
            txtBoard.setText(R.string.Board);
            txtBoard.setTypeface(null, Typeface.BOLD);
            txtBoard.setTextSize(16);
            txtBoard.setPadding(0, 15, 0, 0);
            txtBoarddis.setPadding(0, 0, 0, 15);
            txtBoarddis.setTextColor(Color.parseColor(TextDisColor));
            txtBoarddis.setTextSize(16);
            txtBoarddis.setText(Build.BOARD);
            txtBoarddis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtBoard.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtBoard);
            llayout.addView(txtBoarddis);
            llayout.addView(v3);

            TextView txtHardware = new TextView(getContext());
            TextView txtHardwaredis = new TextView(getContext());
            View v4 = new View(getContext());
            v4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v4.setBackgroundColor(Color.parseColor(LineColor));
            txtHardware.setText(R.string.Hardware);
            txtHardware.setTypeface(null, Typeface.BOLD);
            txtHardware.setTextSize(16);
            txtHardware.setPadding(0, 15, 0, 0);
            txtHardwaredis.setPadding(0, 0, 0, 15);
            txtHardwaredis.setTextColor(Color.parseColor(TextDisColor));
            txtHardwaredis.setTextSize(16);
            txtHardwaredis.setText(Build.HARDWARE);
            txtHardwaredis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtHardware.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtHardware);
            llayout.addView(txtHardwaredis);
            llayout.addView(v4);

            TextView txtBrand = new TextView(getContext());
            TextView txtBranddis = new TextView(getContext());
            View v5 = new View(getContext());
            v5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v5.setBackgroundColor(Color.parseColor(LineColor));
            txtBrand.setText(R.string.Brand);
            txtBrand.setTypeface(null, Typeface.BOLD);
            txtBrand.setTextSize(16);
            txtBrand.setPadding(0, 15, 0, 0);
            txtBranddis.setPadding(0, 0, 0, 15);
            txtBranddis.setTextColor(Color.parseColor(TextDisColor));
            txtBranddis.setTextSize(16);
            txtBranddis.setText(Build.BRAND);
            txtBranddis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtBrand.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtBrand);
            llayout.addView(txtBranddis);
            llayout.addView(v5);

            TextView txtDeviceType = new TextView(getContext());
            txtDeviceTypedis = new TextView(getContext());
            View v6 = new View(getContext());
            v6.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v6.setBackgroundColor(Color.parseColor(LineColor));
            txtDeviceType.setText(R.string.DeviceType);
            txtDeviceType.setTypeface(null, Typeface.BOLD);
            txtDeviceType.setTextSize(16);
            txtDeviceType.setPadding(0, 15, 0, 0);
            txtDeviceTypedis.setClickable(true);
            txtDeviceTypedis.setPadding(0, 0, 0, 15);
            txtDeviceTypedis.setTextColor(Color.parseColor(TextDisColor));
            txtDeviceTypedis.setTextSize(16);
            txtDeviceTypedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtDeviceType.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtDeviceType);
            llayout.addView(txtDeviceTypedis);
            llayout.addView(v6);

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                txtDeviceTypedis.setText(R.string.GrantPermission);
                txtDeviceTypedis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
                    }
                });
            } else {
                txtDeviceTypedis.setText(GetDetails.PhoneType(tm.getPhoneType()));
            }

            TextView txtDeviceID = new TextView(getContext());
            TextView txtDeviceIDdis = new TextView(getContext());
            View v7 = new View(getContext());
            v7.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v7.setBackgroundColor(Color.parseColor(LineColor));
            txtDeviceID.setText(R.string.AndroidDeviceID);
            txtDeviceID.setTypeface(null, Typeface.BOLD);
            txtDeviceID.setTextSize(16);
            txtDeviceID.setPadding(0, 15, 0, 0);
            txtDeviceIDdis.setPadding(0, 0, 0, 15);
            txtDeviceIDdis.setTextColor(Color.parseColor(TextDisColor));
            txtDeviceIDdis.setTextSize(16);
            txtDeviceIDdis.setText(Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID));
            txtDeviceIDdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtDeviceID.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtDeviceID);
            llayout.addView(txtDeviceIDdis);
            llayout.addView(v7);

            TextView txtIMEI = new TextView(getContext());
            txtIMEIdis = new TextView(getContext());
            View v8 = new View(getContext());
            v8.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v8.setBackgroundColor(Color.parseColor(LineColor));
            txtIMEI.setText(R.string.IMEI);
            txtIMEI.setTypeface(null, Typeface.BOLD);
            txtIMEI.setTextSize(16);
            txtIMEI.setPadding(0, 15, 0, 0);
            txtIMEIdis.setClickable(true);
            txtIMEIdis.setPadding(0, 0, 0, 15);
            txtIMEIdis.setTextColor(Color.parseColor(TextDisColor));
            txtIMEIdis.setTextSize(16);
            txtIMEIdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtIMEI.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtIMEI);
            llayout.addView(txtIMEIdis);
            llayout.addView(v8);

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                txtIMEIdis.setText(R.string.GrantPermission);
                txtIMEIdis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
                    }
                });
            } else {
                txtIMEIdis.setText(tm.getDeviceId());
            }

            TextView txtHardwareSerial = new TextView(getContext());
            TextView txtHardwareSerialdis = new TextView(getContext());
            View v9 = new View(getContext());
            v9.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v9.setBackgroundColor(Color.parseColor(LineColor));
            txtHardwareSerial.setText(R.string.HardwareSerial);
            txtHardwareSerial.setTypeface(null, Typeface.BOLD);
            txtHardwareSerial.setTextSize(16);
            txtHardwareSerial.setPadding(0, 15, 0, 0);
            txtHardwareSerialdis.setPadding(0, 0, 0, 15);
            txtHardwareSerialdis.setTextColor(Color.parseColor(TextDisColor));
            txtHardwareSerialdis.setTextSize(16);
            txtHardwareSerialdis.setText(Build.SERIAL);
            txtHardwareSerialdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtHardwareSerial.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtHardwareSerial);
            llayout.addView(txtHardwareSerialdis);
            llayout.addView(v9);

            TextView txtSIMSerial = new TextView(getContext());
            txtSIMSerialdis = new TextView(getContext());
            View v10 = new View(getContext());
            v10.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v10.setBackgroundColor(Color.parseColor(LineColor));
            txtSIMSerial.setText(R.string.SIMSerial);
            txtSIMSerial.setTypeface(null, Typeface.BOLD);
            txtSIMSerial.setTextSize(16);
            txtSIMSerial.setPadding(0, 15, 0, 0);
            txtSIMSerialdis.setClickable(true);
            txtSIMSerialdis.setPadding(0, 0, 0, 15);
            txtSIMSerialdis.setTextColor(Color.parseColor(TextDisColor));
            txtSIMSerialdis.setTextSize(16);
            txtSIMSerialdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtSIMSerial.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtSIMSerial);
            llayout.addView(txtSIMSerialdis);
            llayout.addView(v10);

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                txtSIMSerialdis.setText(R.string.GrantPermission);
                txtSIMSerialdis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
                    }
                });
            } else {
                txtSIMSerialdis.setText(tm.getSimSerialNumber());
            }

            TextView txtSIMSubscriber = new TextView(getContext());
            txtSIMSubscriberdis = new TextView(getContext());
            View v11 = new View(getContext());
            v11.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v11.setBackgroundColor(Color.parseColor(LineColor));
            txtSIMSubscriber.setText(R.string.SIMSubscriber);
            txtSIMSubscriber.setTypeface(null, Typeface.BOLD);
            txtSIMSubscriber.setTextSize(16);
            txtSIMSubscriber.setPadding(0, 15, 0, 0);
            txtSIMSubscriberdis.setClickable(true);
            txtSIMSubscriberdis.setPadding(0, 0, 0, 15);
            txtSIMSubscriberdis.setTextColor(Color.parseColor(TextDisColor));
            txtSIMSubscriberdis.setTextSize(16);
            txtSIMSubscriberdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtSIMSubscriber.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtSIMSubscriber);
            llayout.addView(txtSIMSubscriberdis);
            llayout.addView(v11);

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                txtSIMSubscriberdis.setText(R.string.GrantPermission);
                txtSIMSubscriberdis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
                    }
                });
            } else {
                txtSIMSubscriberdis.setText(tm.getSubscriberId());
            }

            TextView txtNetworkOperator = new TextView(getContext());
            txtNetworkOperatordis = new TextView(getContext());
            View v12 = new View(getContext());
            v12.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v12.setBackgroundColor(Color.parseColor(LineColor));
            txtNetworkOperator.setText(R.string.NetworkOperator);
            txtNetworkOperator.setTypeface(null, Typeface.BOLD);
            txtNetworkOperator.setTextSize(16);
            txtNetworkOperator.setPadding(0, 15, 0, 0);
            txtNetworkOperatordis.setClickable(true);
            txtNetworkOperatordis.setPadding(0, 0, 0, 15);
            txtNetworkOperatordis.setTextColor(Color.parseColor(TextDisColor));
            txtNetworkOperatordis.setTextSize(16);
            txtNetworkOperatordis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtNetworkOperator.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtNetworkOperator);
            llayout.addView(txtNetworkOperatordis);
            llayout.addView(v12);

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                txtNetworkOperatordis.setText(R.string.GrantPermission);
                txtNetworkOperatordis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
                    }
                });
            } else {
                txtNetworkOperatordis.setText(tm.getNetworkOperatorName());
            }

            TextView txtNetworkType = new TextView(getContext());
            txtNetworkTypedis = new TextView(getContext());
            View v13 = new View(getContext());
            v13.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v13.setBackgroundColor(Color.parseColor(LineColor));
            txtNetworkType.setText(R.string.NetworkType);
            txtNetworkType.setTypeface(null, Typeface.BOLD);
            txtNetworkType.setTextSize(16);
            txtNetworkType.setPadding(0, 15, 0, 0);
            txtNetworkTypedis.setClickable(true);
            txtNetworkTypedis.setPadding(0, 0, 0, 15);
            txtNetworkTypedis.setTextColor(Color.parseColor(TextDisColor));
            txtNetworkTypedis.setTextSize(16);
            txtNetworkTypedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtNetworkType.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtNetworkType);
            llayout.addView(txtNetworkTypedis);
            llayout.addView(v13);

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                txtNetworkTypedis.setText(R.string.GrantPermission);
                txtNetworkTypedis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE_STATE);
                    }
                });
            } else {
                txtNetworkTypedis.setText(GetDetails.NetworkType(tm.getNetworkType()));
            }

            TextView txtWIFIMACAddress = new TextView(getContext());
            txtWIFIMACAddressdis = new TextView(getContext());
            View v14 = new View(getContext());
            v14.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v14.setBackgroundColor(Color.parseColor(LineColor));
            txtWIFIMACAddress.setText(R.string.WiFiMac);
            txtWIFIMACAddress.setTypeface(null, Typeface.BOLD);
            txtWIFIMACAddress.setTextSize(16);
            txtWIFIMACAddress.setPadding(0, 15, 0, 0);
            txtWIFIMACAddressdis.setClickable(true);
            txtWIFIMACAddressdis.setPadding(0, 0, 0, 15);
            txtWIFIMACAddressdis.setTextColor(Color.parseColor(TextDisColor));
            txtWIFIMACAddressdis.setTextSize(16);
            txtWIFIMACAddressdis.setText(GetDetails.getWifiMacAddress());
            txtWIFIMACAddressdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtWIFIMACAddress.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtWIFIMACAddress);
            llayout.addView(txtWIFIMACAddressdis);
            llayout.addView(v14);

            TextView txtBluetoothMACAddress = new TextView(getContext());
            txtBluetoothMACAddressdis = new TextView(getContext());
            View v15 = new View(getContext());
            v15.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v15.setBackgroundColor(Color.parseColor(LineColor));
            txtBluetoothMACAddress.setText(R.string.BluetoothMac);
            txtBluetoothMACAddress.setTypeface(null, Typeface.BOLD);
            txtBluetoothMACAddress.setTextSize(16);
            txtBluetoothMACAddress.setPadding(0, 15, 0, 0);
            txtBluetoothMACAddressdis.setClickable(true);
            txtBluetoothMACAddressdis.setPadding(0, 0, 0, 15);
            txtBluetoothMACAddressdis.setTextColor(Color.parseColor(TextDisColor));
            txtBluetoothMACAddressdis.setTextSize(16);
            txtBluetoothMACAddressdis.setText(GetDetails.getBluetoothMac(getContext()));
            txtBluetoothMACAddressdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtBluetoothMACAddress.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtBluetoothMACAddress);
            llayout.addView(txtBluetoothMACAddressdis);
            llayout.addView(v15);

            TextView txtBuildFingerPrint = new TextView(getContext());
            txtBuildFingerPrintdis = new TextView(getContext());
            View v16 = new View(getContext());
            v16.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v16.setBackgroundColor(Color.parseColor(LineColor));
            txtBuildFingerPrint.setText(R.string.BuildFingerprint);
            txtBuildFingerPrint.setTypeface(null, Typeface.BOLD);
            txtBuildFingerPrint.setTextSize(16);
            txtBuildFingerPrint.setPadding(0, 15, 0, 0);
            txtBuildFingerPrintdis.setClickable(true);
            txtBuildFingerPrintdis.setPadding(0, 0, 0, 15);
            txtBuildFingerPrintdis.setTextColor(Color.parseColor(TextDisColor));
            txtBuildFingerPrintdis.setTextSize(16);
            txtBuildFingerPrintdis.setText(Build.FINGERPRINT);
            txtBuildFingerPrintdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtBuildFingerPrint.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtBuildFingerPrint);
            llayout.addView(txtBuildFingerPrintdis);
            llayout.addView(v16);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rootView;
    }

    @SuppressLint("HardwareIds")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        try {
            switch (requestCode) {
                case REQUEST_PHONE_STATE: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        txtDeviceTypedis.setText(GetDetails.PhoneType(tm.getPhoneType()));
                        txtIMEIdis.setText(tm.getDeviceId());
                        txtSIMSerialdis.setText(tm.getSimSerialNumber());
                        txtSIMSubscriberdis.setText(tm.getSubscriberId());
                        txtNetworkOperatordis.setText(tm.getNetworkOperatorName());
                        txtNetworkTypedis.setText(GetDetails.NetworkType(tm.getNetworkType()));
                    } else {
                        Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }catch (SecurityException ex){
            ex.printStackTrace();
        }
    }
}
