package com.ytheekshana.deviceinfo;

import android.content.pm.ActivityInfo;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class DisplayTestFullScreen extends AppCompatActivity {
    RelativeLayout relativeMain;
    int color = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_test_full_screen);

        View mDecorView = getWindow().getDecorView();
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        relativeMain = findViewById(R.id.relativeMain);
        relativeMain.setBackgroundColor(Color.BLACK);
        relativeMain.setOnClickListener(v -> {
            if (color == 4) {
                finish();
            }
            color++;
            DisplayColor();
        });
    }

    public void DisplayColor() {
        switch (color) {
            case 0:
                relativeMain.setBackgroundColor(Color.BLACK);
                break;
            case 1:
                relativeMain.setBackgroundColor(Color.WHITE);
                break;
            case 2:
                relativeMain.setBackgroundColor(Color.RED);
                break;
            case 3:
                relativeMain.setBackgroundColor(Color.GREEN);
                break;
            case 4:
                relativeMain.setBackgroundColor(Color.BLUE);
                break;
        }
    }
}
