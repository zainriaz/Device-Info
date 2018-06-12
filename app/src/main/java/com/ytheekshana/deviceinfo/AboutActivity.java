package com.ytheekshana.deviceinfo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences shpre = PreferenceManager.getDefaultSharedPreferences(this);
        MainActivity.themeId = shpre.getInt("ThemeNoBar",0);
        setTheme(MainActivity.themeId);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView txtVersion = findViewById(R.id.txtVersion);
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
