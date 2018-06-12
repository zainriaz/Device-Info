package com.ytheekshana.deviceinfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences shpre = PreferenceManager.getDefaultSharedPreferences(this);
        MainActivity.themeId = shpre.getInt("ThemeBar",0);
        setTheme(MainActivity.themeId);

        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
            final SharedPreferences sharedprefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            final SharedPreferences.Editor shareEdit = sharedprefs.edit();

            sharedprefs.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                    boolean darkt = sharedPreferences.getBoolean("dark_theme",false);
                    if(darkt){
                        shareEdit.putInt("ThemeNoBar",R.style.AppThemeDark_NoActionBar);
                        shareEdit.putInt("ThemeBar",R.style.AppThemeDark);
                    }else{
                        shareEdit.putInt("ThemeNoBar",R.style.AppTheme_NoActionBar);
                        shareEdit.putInt("ThemeBar",R.style.AppTheme);
                    }
                    shareEdit.apply();
                    shareEdit.commit();
                    if(getActivity()!=null){
                        getActivity().recreate();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
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