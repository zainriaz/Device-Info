package com.ytheekshana.deviceinfo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.view.MenuItem;

import com.kizitonwose.colorpreference.ColorDialog;

public class SettingsActivity extends AppCompatPreferenceActivity implements ColorDialog.OnColorSelectedListener {

    private int themeColor;
    private int themeColorDark;
    static com.kizitonwose.colorpreference.ColorPreference theme_color;
    int themeBarValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        int themeId = sharedPrefs.getInt("ThemeBar", R.style.AppTheme);
        themeColor = sharedPrefs.getInt("accent_color_dialog", Color.parseColor("#2196f3"));
        themeColorDark = GetDetails.getDarkColor(this, themeColor);
        setTheme(themeId);

        if (sharedPrefs.getInt("ThemeBar", 0) != R.style.AppThemeDark) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(themeColor));
            getWindow().setStatusBarColor(themeColorDark);
        }
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(getString(R.string.app_name), icon, themeColor);
        setTaskDescription(taskDescription);

        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment {

        SwitchPreference dark_theme_Pref;
        SharedPreferences sharedPrefs;
        SharedPreferences.Editor shareEdit;
        Preference app_version_pref;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            shareEdit = sharedPrefs.edit();

            theme_color = (com.kizitonwose.colorpreference.ColorPreference) findPreference("accent_color_dialog");
            dark_theme_Pref = (SwitchPreference) findPreference("dark_theme_switch");
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
        theme_color.setValue(newColor);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor sharedEdit = sharedPref.edit();
        sharedEdit.putInt("accent_color_dialog", newColor);
        sharedEdit.apply();
        sharedEdit.commit();
        themeBarValue = sharedPref.getInt("ThemeBar", 0);

        ValueAnimator actionBarAnimator= ValueAnimator.ofObject(new ArgbEvaluator(), themeColor, newColor);
        actionBarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                if (themeBarValue != R.style.AppThemeDark) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable((Integer) animator.getAnimatedValue()));
                }
            }
        });
        ValueAnimator statusBarAnimator= ValueAnimator.ofObject(new ArgbEvaluator(), themeColorDark, GetDetails.getDarkColor(this, newColor));
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
    }
}