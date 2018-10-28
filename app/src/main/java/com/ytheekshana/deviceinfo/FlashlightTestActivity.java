package com.ytheekshana.deviceinfo;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Objects;

import github.nisrulz.lantern.Lantern;

public class FlashlightTestActivity extends AppCompatActivity {

    public static final int REQUEST_CAMERA = 1;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editPrefs;
    Context context;
    Lantern lantern;

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
            setContentView(R.layout.activity_test_flashlight);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

            context = this;
            sharedPrefs = getSharedPreferences("tests", MODE_PRIVATE);
            editPrefs = sharedPrefs.edit();

            ImageButton imgbtn_failed = findViewById(R.id.imgbtn_failed);
            ImageButton imgbtn_success = findViewById(R.id.imgbtn_success);
            imgbtn_failed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editPrefs.putInt("flashlight_test_status", 0);
                    editPrefs.apply();
                    editPrefs.commit();
                    finish();
                }
            });
            imgbtn_success.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editPrefs.putInt("flashlight_test_status", 1);
                    editPrefs.apply();
                    editPrefs.commit();
                    finish();
                }
            });

            lantern = new Lantern(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                } else {
                    lantern.initTorch();
                    lantern.enableTorchMode(true);
                }
            } else {
                lantern.initTorch();
                lantern.enableTorchMode(true);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        try {
            switch (requestCode) {
                case REQUEST_CAMERA: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        lantern.initTorch();
                        lantern.enableTorchMode(true);
                    } else {
                        Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            lantern.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        try {
            lantern.enableTorchMode(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        try {
            lantern.enableTorchMode(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }
}

