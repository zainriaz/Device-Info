package com.ytheekshana.deviceinfo;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

import android.view.MenuItem;
import android.widget.Toast;

import com.kizitonwose.colorpreference.ColorDialog;
import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;
import java.util.Objects;

@SuppressLint("StaticFieldLeak")
public class SettingsActivity extends AppCompatActivity implements ColorDialog.OnColorSelectedListener {

    private int themeColor;
    private int themeColorDark;
    private static com.kizitonwose.colorpreferencecompat.ColorPreferenceCompat theme_color;
    int themeBarValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            int themeId = sharedPrefs.getInt("ThemeBar", R.style.AppTheme);
            themeColor = sharedPrefs.getInt("accent_color_dialog", Color.parseColor("#2196f3"));
            themeColorDark = GetDetails.getDarkColor(this, themeColor);
            setTheme(themeId);

            if (sharedPrefs.getInt("ThemeBar", 0) != R.style.AppThemeDark) {
                Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(themeColor));
                getWindow().setStatusBarColor(themeColorDark);
            }
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
            ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(getString(R.string.app_name), icon, themeColor);
            setTaskDescription(taskDescription);

            super.onCreate(savedInstanceState);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        SwitchPreference dark_theme_Pref;
        SharedPreferences sharedPrefs;
        SharedPreferences.Editor shareEdit;
        Preference app_version_pref, pref_rate_us, pref_donate, pref_extract_location;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            try {


                setPreferencesFromResource(R.xml.settings, rootKey);
                sharedPrefs = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()));
                shareEdit = sharedPrefs.edit();

                theme_color = findPreference("accent_color_dialog");
                dark_theme_Pref = findPreference("dark_theme_switch");
                app_version_pref = findPreference("app_version_pref");
                dark_theme_Pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    public boolean onPreferenceClick(Preference preference) {

                        if (dark_theme_Pref.isChecked()) {
                            shareEdit.putInt("ThemeNoBar", R.style.AppThemeDark_NoActionBar);
                            shareEdit.putInt("ThemeBar", R.style.AppThemeDark);
                            dark_theme_Pref.setSummary("Disable Dark Theme");
                        } else {
                            shareEdit.putInt("ThemeNoBar", R.style.AppTheme_NoActionBar);
                            shareEdit.putInt("ThemeBar", R.style.AppTheme);
                            dark_theme_Pref.setSummary("Enable Dark Theme");
                        }
                        shareEdit.apply();
                        shareEdit.commit();
                        if (getActivity() != null) {
                            getActivity().recreate();
                        }
                        return true;
                    }
                });

                if (sharedPrefs.getInt("ThemeBar", 0) == R.style.AppThemeDark) {
                    dark_theme_Pref.setChecked(true);
                    dark_theme_Pref.setSummary("Disable Dark Theme");
                } else {
                    dark_theme_Pref.setChecked(false);
                    dark_theme_Pref.setSummary("Enable Dark Theme");
                }
                app_version_pref.setSummary(BuildConfig.VERSION_NAME);
                pref_rate_us = findPreference("pref_rate_us");
                pref_rate_us.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ytheekshana.deviceinfo"));
                        intent.setPackage("com.android.vending");
                        if(intent.resolveActivity(Objects.requireNonNull(getContext()).getPackageManager())!=null){
                            startActivity(intent);
                        }else{
                            Toast.makeText(getContext(), "Google Play Store not found", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                pref_donate = findPreference("pref_donate");
                pref_donate.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        Intent intent = new Intent(getActivity(), DonateActivity.class);
                        startActivity(intent);
                        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
                        return true;
                    }
                });
                pref_extract_location = findPreference("pref_extract_location");
                String getExtractpath = sharedPrefs.getString("extract_location", "/storage/emulated/0/DeviceInfo");
                pref_extract_location.setSummary(getExtractpath);
                pref_extract_location.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {

                        ChooserDialog chooseLocation = new ChooserDialog(getActivity());
                        chooseLocation.enableOptions(true);
                        chooseLocation.withResources(R.string.file_chooser_title, R.string.file_chooser_choose, R.string.file_chooser_cancel);
                        chooseLocation.withFilter(true, false);
                        chooseLocation.withStartFile(Environment.getExternalStorageDirectory().getAbsolutePath());
                        chooseLocation.withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File pathFile) {
                                shareEdit.putString("extract_location", path);
                                shareEdit.apply();
                                shareEdit.commit();
                                pref_extract_location.setSummary(path);
                            }
                        });
                        if (dark_theme_Pref.isChecked()) {
                            chooseLocation.withRowLayoutView(R.layout.file_chooser_layout_dark);
                        } else {
                            chooseLocation.withRowLayoutView(R.layout.file_chooser_layout_light);
                        }
                        chooseLocation.build();
                        chooseLocation.show();
                        return true;
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        this.finish();
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onColorSelected(int newColor, String s) {
        try {


            theme_color.setValue(newColor);
            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor sharedEdit = sharedPref.edit();
            sharedEdit.putInt("accent_color_dialog", newColor);
            sharedEdit.apply();
            sharedEdit.commit();
            themeBarValue = sharedPref.getInt("ThemeBar", 0);

            ValueAnimator actionBarAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), themeColor, newColor);
            actionBarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    if (themeBarValue != R.style.AppThemeDark) {
                        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable((Integer) animator.getAnimatedValue()));
                    }
                }
            });
            ValueAnimator statusBarAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), themeColorDark, GetDetails.getDarkColor(this, newColor));
            statusBarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    if (themeBarValue != R.style.AppThemeDark) {
                        getWindow().setStatusBarColor((Integer) animator.getAnimatedValue());
                    }
                }
            });
            actionBarAnimator.setDuration(800);
            actionBarAnimator.setStartDelay(0);
            actionBarAnimator.start();
            statusBarAnimator.setDuration(800);
            statusBarAnimator.setStartDelay(0);
            statusBarAnimator.start();

            themeColor = newColor;
            themeColorDark = GetDetails.getDarkColor(this, newColor);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}