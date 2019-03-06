package com.ytheekshana.deviceinfo;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class BluetoothTestActivity extends AppCompatActivity {
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editPrefs;
    Context context;
    ImageView imgBluetoothImage;
    TextView txtBluetoothStatus;
    Button btnDone;
    BluetoothAdapter bluetoothAdapter;
    int bluetoothStatus = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            int themeId = sharedPrefs.getInt("ThemeBar", R.style.AppTheme);
            int themeColor = sharedPrefs.getInt("accent_color_dialog", Color.parseColor("#2196f3"));
            int themeColorDark = GetDetails.getDarkColor(this, themeColor);
            setTheme(themeId);

            if (sharedPrefs.getInt("ThemeBar", 0) != R.style.AppThemeDark) {
                Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(themeColor));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getWindow().setStatusBarColor(themeColorDark);
            }
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
            ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(getString(R.string.app_name), icon, themeColor);
            setTaskDescription(taskDescription);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_test_bluetooth);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

            context = this;
            imgBluetoothImage = findViewById(R.id.imgBluetoothImage);
            txtBluetoothStatus = findViewById(R.id.txtBluetoothStatus);
            btnDone = findViewById(R.id.btnDone);
            sharedPrefs = getSharedPreferences("tests", MODE_PRIVATE);
            editPrefs = sharedPrefs.edit();
            this.registerReceiver(mBluetoothStateChangedReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            Thread startBluetooth = new Thread() {
                @Override
                public void run() {
                    if (bluetoothAdapter.isEnabled()) {
                        bluetoothAdapter.disable();
                    } else {
                        bluetoothAdapter.enable();
                    }
                    txtBluetoothStatus.post(() -> txtBluetoothStatus.setText(R.string.bluetooth_test_start));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (bluetoothAdapter.isEnabled()) {
                        bluetoothStatus = 1;
                        bluetoothAdapter.disable();
                    } else {
                        bluetoothStatus = 0;
                        bluetoothAdapter.enable();
                    }
                }
            };
            startBluetooth.start();
            btnDone.setOnClickListener(view -> finish());

        } catch (Exception ex) {
            imgBluetoothImage.setImageResource(R.drawable.ic_bluetooth_failed);
            txtBluetoothStatus.setText(R.string.test_failed);
            editPrefs.putInt("bluetooth_test_status", 0);
            editPrefs.apply();
            editPrefs.commit();
            ex.printStackTrace();
        }
    }

    private BroadcastReceiver mBluetoothStateChangedReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            int extraBluetoothState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (extraBluetoothState) {
                case BluetoothAdapter.STATE_OFF:
                    if (bluetoothStatus == 1) {
                        imgBluetoothImage.setImageResource(R.drawable.ic_bluetooth_passed);
                        txtBluetoothStatus.setText(R.string.test_passed);
                        btnDone.setVisibility(View.VISIBLE);
                        editPrefs.putInt("bluetooth_test_status", 1);
                        editPrefs.apply();
                        editPrefs.commit();
                        context.unregisterReceiver(this);
                    } else {
                        imgBluetoothImage.setImageResource(R.drawable.ic_bluetooth_image);
                        btnDone.setVisibility(View.GONE);
                    }
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    imgBluetoothImage.setImageResource(R.drawable.ic_bluetooth_image);
                    btnDone.setVisibility(View.GONE);
                    break;
                case BluetoothAdapter.STATE_ON:
                    if (bluetoothStatus == 0) {
                        imgBluetoothImage.setImageResource(R.drawable.ic_bluetooth_passed);
                        txtBluetoothStatus.setText(R.string.test_passed);
                        btnDone.setVisibility(View.VISIBLE);
                        editPrefs.putInt("bluetooth_test_status", 1);
                        editPrefs.apply();
                        editPrefs.commit();
                        context.unregisterReceiver(this);
                    } else {
                        imgBluetoothImage.setImageResource(R.drawable.ic_bluetooth_image);
                        btnDone.setVisibility(View.GONE);
                    }
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    imgBluetoothImage.setImageResource(R.drawable.ic_bluetooth_image);
                    btnDone.setVisibility(View.GONE);
                    txtBluetoothStatus.setText(R.string.bluetooth_test_start);
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:
                    imgBluetoothImage.setImageResource(R.drawable.ic_bluetooth_failed);
                    btnDone.setVisibility(View.GONE);
                    txtBluetoothStatus.setText(R.string.test_failed);
                    editPrefs.putInt("bluetooth_test_status", 0);
                    editPrefs.apply();
                    editPrefs.commit();
                    context.unregisterReceiver(this);
                    break;
            }
        }
    };
}
