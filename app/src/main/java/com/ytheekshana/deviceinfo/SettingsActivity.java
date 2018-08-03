package com.ytheekshana.deviceinfo;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.view.MenuItem;

import com.kizitonwose.colorpreference.ColorDialog;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences shpre = PreferenceManager.getDefaultSharedPreferences(this);
        MainActivity.themeId = shpre.getInt("ThemeBar", R.style.AppTheme);
        setTheme(MainActivity.themeId);

        if (shpre.getInt("ThemeBar", 0) != R.style.AppThemeDark) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(MainActivity.themeColor));
            getWindow().setStatusBarColor(MainActivity.themeColorDark);
        }
        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.icon);
        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(getString(R.string.app_name),icon , MainActivity.themeColor);
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
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}