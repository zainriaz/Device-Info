package com.ytheekshana.deviceinfo;

import android.app.ActivityManager;
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

public class WifiTestActivity extends AppCompatActivity {
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editPrefs;
    Context context;
    ImageView imgWifiImage;
    TextView txtWifiStatus;
    Button btnDone;
    WifiManager wifiManager;
    int wifiStatus = 2;

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
            setContentView(R.layout.activity_test_wifi);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

            context = this;
            imgWifiImage = findViewById(R.id.imgWifiImage);
            txtWifiStatus = findViewById(R.id.txtWifiStatus);
            btnDone = findViewById(R.id.btnDone);
            sharedPrefs = getSharedPreferences("tests", MODE_PRIVATE);
            editPrefs = sharedPrefs.edit();
            this.registerReceiver(mWifiStateChangedReceiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
            wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            Thread startWifi = new Thread() {
                @Override
                public void run() {
                    if (wifiManager.isWifiEnabled()) {
                        wifiManager.setWifiEnabled(false);
                    } else {
                        wifiManager.setWifiEnabled(true);
                    }
                    txtWifiStatus.post(new Runnable() {
                        @Override
                        public void run() {
                            txtWifiStatus.setText(R.string.wifi_test_start);
                        }
                    });
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (wifiManager.isWifiEnabled()) {
                        wifiStatus = 1;
                        wifiManager.setWifiEnabled(false);
                    } else {
                        wifiStatus = 0;
                        wifiManager.setWifiEnabled(true);
                    }
                }
            };
            startWifi.start();
            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        } catch (Exception ex) {
            imgWifiImage.setImageResource(R.drawable.ic_wifi_failed);
            txtWifiStatus.setText(R.string.test_failed);
            editPrefs.putInt("wifi_test_status", 0);
            editPrefs.apply();
            editPrefs.commit();
            ex.printStackTrace();
        }
    }

    private BroadcastReceiver mWifiStateChangedReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            switch (extraWifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    if (wifiStatus == 1) {
                        imgWifiImage.setImageResource(R.drawable.ic_wifi_passed);
                        txtWifiStatus.setText(R.string.test_passed);
                        btnDone.setVisibility(View.VISIBLE);
                        editPrefs.putInt("wifi_test_status", 1);
                        editPrefs.apply();
                        editPrefs.commit();
                        context.unregisterReceiver(this);
                    } else {
                        imgWifiImage.setImageResource(R.drawable.ic_wifi_image);
                        btnDone.setVisibility(View.GONE);
                    }
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    imgWifiImage.setImageResource(R.drawable.ic_wifi_image);
                    btnDone.setVisibility(View.GONE);
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    if (wifiStatus == 0) {
                        imgWifiImage.setImageResource(R.drawable.ic_wifi_passed);
                        txtWifiStatus.setText(R.string.test_passed);
                        btnDone.setVisibility(View.VISIBLE);
                        editPrefs.putInt("wifi_test_status", 1);
                        editPrefs.apply();
                        editPrefs.commit();
                        context.unregisterReceiver(this);
                    } else {
                        imgWifiImage.setImageResource(R.drawable.ic_wifi_image);
                        btnDone.setVisibility(View.GONE);
                    }
                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    imgWifiImage.setImageResource(R.drawable.ic_wifi_image);
                    btnDone.setVisibility(View.GONE);
                    txtWifiStatus.setText(R.string.wifi_test_start);
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:
                    imgWifiImage.setImageResource(R.drawable.ic_wifi_failed);
                    btnDone.setVisibility(View.GONE);
                    txtWifiStatus.setText(R.string.test_failed);
                    editPrefs.putInt("wifi_test_status", 0);
                    editPrefs.apply();
                    editPrefs.commit();
                    context.unregisterReceiver(this);
                    break;
            }
        }
    };
}
