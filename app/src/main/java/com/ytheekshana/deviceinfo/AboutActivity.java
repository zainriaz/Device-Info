package com.ytheekshana.deviceinfo;

import android.app.ActivityManager;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences shpre = PreferenceManager.getDefaultSharedPreferences(this);
        int themeId = shpre.getInt("ThemeBar", R.style.AppTheme);
        int themeColor = shpre.getInt("accent_color_dialog", Color.parseColor("#2196f3"));
        int themeColorDark = GetDetails.getDarkColor(this, themeColor);
        setTheme(themeId);

        if (shpre.getInt("ThemeBar", 0) != R.style.AppThemeDark) {
            Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(themeColor));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getWindow().setStatusBarColor(themeColorDark);
        }
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(getString(R.string.app_name), icon, themeColor);
        setTaskDescription(taskDescription);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView txtVersion = findViewById(R.id.txtVersion);
        TextView txtPackageName = findViewById(R.id.txtPackageName);
        txtPackageName.setText(getApplicationContext().getPackageName());
        txtVersion.setText(BuildConfig.VERSION_NAME);
        CircleImageView imgAuthor = findViewById(R.id.circleimgAuthor);

        imgAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Created by Ytheekshana", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
