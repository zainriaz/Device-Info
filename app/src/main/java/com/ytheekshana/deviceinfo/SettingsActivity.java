package com.ytheekshana.deviceinfo;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        int themeId = sharedPrefs.getInt("ThemeBar", R.style.AppTheme);
        themeColor = sharedPrefs.getInt("accent_color_dialog", Color.parseColor("#2196f3"));
        themeColorDark = GetDetails.getDarkColor(this,themeColor);
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

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            shareEdit = sharedPrefs.edit();

            theme_color = (com.kizitonwose.colorpreference.ColorPreference) findPreference("accent_color_dialog");
            dark_theme_Pref = (SwitchPreference) findPreference("dark_theme_switch");
            dark_theme_Pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {

                    if (dark_theme_Pref.isChecked()) {
                        shareEdit.putInt("ThemeNoBar", R.style.AppThemeDark_NoActionBar);
                        shareEdit.putInt("ThemeBar", R.style.AppThemeDark);
                    } else {
                        shareEdit.putInt("ThemeNoBar", R.style.AppTheme_NoActionBar);
                        shareEdit.putInt("ThemeBar", R.style.AppTheme);
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
            } else {
                dark_theme_Pref.setChecked(false);
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
        theme_color.setValue(newColor);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor sharedEdit = sharedPref.edit();
        sharedEdit.putInt("accent_color_dialog", newColor);
        sharedEdit.apply();
        sharedEdit.commit();
        themeColor = newColor;
        themeColorDark = GetDetails.getDarkColor(this,newColor);
        this.recreate();
    }
}