package com.ytheekshana.deviceinfo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.Objects;

public class VibrationTestActivity extends AppCompatActivity {

    Vibrator vibrator;
    Context context;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editPrefs;

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
            setContentView(R.layout.activity_test_vibration);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

            context = this;
            sharedPrefs = getSharedPreferences("tests", MODE_PRIVATE);
            editPrefs = sharedPrefs.edit();

            ImageButton imgbtn_failed = findViewById(R.id.imgbtn_failed);
            ImageButton imgbtn_success = findViewById(R.id.imgbtn_success);
            imgbtn_failed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editPrefs.putInt("vibration_test_status", 0);
                    editPrefs.apply();
                    editPrefs.commit();
                    finish();
                }
            });
            imgbtn_success.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editPrefs.putInt("vibration_test_status", 1);
                    editPrefs.apply();
                    editPrefs.commit();
                    finish();
                }
            });
            vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            if(Objects.requireNonNull(vibrator).hasVibrator()){
                long[] pattern = {0, 1000, 0};
                vibrator.vibrate(pattern, 0);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        vibrator.cancel();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        vibrator.cancel();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        vibrator.cancel();
        super.onPause();
    }
}
