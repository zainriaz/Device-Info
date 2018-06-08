package com.ytheekshana.deviceinfo;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.view.MenuItem;
import android.widget.Toast;

public class SettingsActivity extends AppCompatPreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }
    public static class MainPreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
            SwitchPreference darktheme = (SwitchPreference) findPreference("dark_theme");
            darktheme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){

                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(newValue.toString().equals("dark_theme")){
                        boolean darkt = preference.getSharedPreferences().getBoolean("dark_theme",false);
                        if(darkt){
                            Toast.makeText(getContext(), "Enabled", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Disabled", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return false;
                }
            });

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}