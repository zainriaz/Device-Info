package com.ytheekshana.deviceinfo;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.google.android.material.appbar.AppBarLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    NotificationCompat.Builder mBuilder;
    public static int themeColor, themeColor2, themeColorDark, requestReviewCount;
    public static boolean isDarkmode;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        int themeId = sharedPrefs.getInt("ThemeNoBar", R.style.AppTheme_NoActionBar);
        requestReviewCount = sharedPrefs.getInt("requestReviewCount", 0);
        themeColor = sharedPrefs.getInt("accent_color_dialog", Color.parseColor("#2196f3"));
        themeColorDark = GetDetails.getDarkColor(this, themeColor);
        themeColor2 = GetDetails.getDarkColor2(this, themeColor);
        setTheme(themeId);

        super.onCreate(savedInstanceState);
        /*View mDecorView = getWindow().getDecorView();
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);*/
        setContentView(R.layout.activity_main);
        AppBarLayout appbar = findViewById(R.id.appbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        /*final TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);*/
        final SmartTabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setSelectedIndicatorColors(themeColorDark);
        tabLayout.setViewPager(mViewPager);

        if (sharedPrefs.getInt("ThemeNoBar", 0) != R.style.AppThemeDark_NoActionBar) {
            appbar.setBackgroundColor(themeColor);
            toolbar.setBackgroundColor(themeColor);
            tabLayout.setBackgroundColor(themeColor);
            getWindow().setStatusBarColor(themeColorDark);
            isDarkmode = false;
        } else {
            isDarkmode = true;
        }
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(getString(R.string.app_name), icon, themeColor);
        setTaskDescription(taskDescription);

        /*mBuilder = new NotificationCompat.Builder(this, "1")
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.cpu)
                .setContentTitle("Device Info")
                .setContentText("Gathering Data Completed");

        int mNotificationId = 1;
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (mNotifyMgr != null) {
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPrefs.edit();
        boolean requestReview = sharedPrefs.getBoolean("RequestReview", false);
        if (!requestReview) {
            BottomSheetEnjoy bottomSheetEnjoy = BottomSheetEnjoy.newInstance();
            bottomSheetEnjoy.show(getSupportFragmentManager(), "EnjoyAppFragment");
            editor.putBoolean("RequestReview", true);
            editor.apply();
            editor.commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about: {
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.action_donate: {
                Intent intent = new Intent(this, DonateActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.action_rate: {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ytheekshana.deviceinfo"));
                    intent.setPackage("com.android.vending");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Google Play Store not found", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                } catch (ActivityNotFoundException ex) {
                    ex.printStackTrace();
                    Toast.makeText(this, "Intall Google Play Services", Toast.LENGTH_SHORT).show();
                }

            }
            case R.id.action_settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                this.finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new tabDashboard();
                case 1:
                    return new tabDevice();
                case 2:
                    return new tabSystem();
                case 3:
                    return new tabCPU();
                case 4:
                    return new tabBattery();
                case 5:
                    return new tabDisplay();
                case 6:
                    return new tabMemory();
                case 7:
                    return new tabCamera();
                case 8:
                    return new tabThermal();
                case 9:
                    return new tabSensor();
                case 10:
                    return new tabApps();
                case 11:
                    return new tabTests();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 12;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Dashboard";
                case 1:
                    return "Device";
                case 2:
                    return "System";
                case 3:
                    return "CPU";
                case 4:
                    return "Battery";
                case 5:
                    return "Display";
                case 6:
                    return "Memory";
                case 7:
                    return "Camera";
                case 8:
                    return "Thermal";
                case 9:
                    return "Sensors";
                case 10:
                    return "Apps";
                case 11:
                    return "Tests";
            }
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
