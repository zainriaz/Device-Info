package com.ytheekshana.deviceinfo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Objects;

public class DisplayTestActivity extends AppCompatActivity {
    Context context;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_test_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        context = this;

        final TextView txtDisplayText = findViewById(R.id.txtDisplayText);
        final Button btnNext = findViewById(R.id.btnNext);
        final ImageButton imgbtn_failed = findViewById(R.id.imgbtn_failed);
        final ImageButton imgbtn_success = findViewById(R.id.imgbtn_success);
        btnNext.setOnClickListener(v -> {
            Intent loadFullScreenDisplayTest = new Intent(context, DisplayTestFullScreen.class);
            startActivity(loadFullScreenDisplayTest);
            imgbtn_failed.setVisibility(View.VISIBLE);
            imgbtn_success.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
            txtDisplayText.setText(getResources().getString(R.string.display_test_question));
        });

        sharedPrefs = getSharedPreferences("tests", MODE_PRIVATE);
        editPrefs = sharedPrefs.edit();

        imgbtn_failed.setOnClickListener(v -> {
            editPrefs.putInt("display_test_status", 0);
            editPrefs.apply();
            editPrefs.commit();
            finish();
        });
        imgbtn_success.setOnClickListener(v -> {
            editPrefs.putInt("display_test_status", 1);
            editPrefs.apply();
            editPrefs.commit();
            finish();
        });
    }
}
