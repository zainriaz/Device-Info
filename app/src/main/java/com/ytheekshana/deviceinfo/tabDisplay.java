package com.ytheekshana.deviceinfo;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;


public class tabDisplay extends Fragment {
    LinearLayout llayout;
    int TextDisColor, LineColor;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabdisplay, container, false);
        llayout = rootView.findViewById(R.id.llayout);
        try {
            TextDisColor = MainActivity.themeColor;
            LineColor = GetDetails.getThemeColor(Objects.requireNonNull(getContext()), R.attr.colorButtonNormal);

            TextView txtResolution = new TextView(getContext());
            TextView txtResolutiondis = new TextView(getContext());
            View v = new View(getContext());
            v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v.setBackgroundColor(LineColor);
            txtResolution.setText(R.string.Resolution);
            txtResolution.setTypeface(null, Typeface.BOLD);
            txtResolution.setTextSize(16);
            txtResolutiondis.setPadding(0, 0, 0, 15);
            txtResolutiondis.setTextColor(TextDisColor);
            txtResolutiondis.setTextSize(16);
            String Res = String.valueOf(SplashActivity.displayWidth) + " x " + String.valueOf(SplashActivity.displayHeight) + " Pixels";
            txtResolutiondis.setText(Res);
            txtResolutiondis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtResolution.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtResolution);
            llayout.addView(txtResolutiondis);
            llayout.addView(v);

            TextView txtDensity = new TextView(getContext());
            TextView txtDensitydis = new TextView(getContext());
            View v1 = new View(getContext());
            v1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v1.setBackgroundColor(LineColor);
            txtDensity.setText(R.string.Density);
            txtDensity.setTypeface(null, Typeface.BOLD);
            txtDensity.setTextSize(16);
            txtDensity.setPadding(0, 15, 0, 0);
            txtDensitydis.setPadding(0, 0, 0, 15);
            txtDensitydis.setTextColor(TextDisColor);
            txtDensitydis.setTextSize(16);
            String fdensity = String.valueOf(SplashActivity.displayDensityDPI) + " dpi (" + SplashActivity.displaySize + ")";
            txtDensitydis.setText(fdensity);
            txtDensitydis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtDensity.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtDensity);
            llayout.addView(txtDensitydis);
            llayout.addView(v1);

            TextView txtFontScale = new TextView(getContext());
            TextView txtFontScaledis = new TextView(getContext());
            View v2 = new View(getContext());
            v2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v2.setBackgroundColor(LineColor);
            txtFontScale.setText(R.string.FontScale);
            txtFontScale.setTypeface(null, Typeface.BOLD);
            txtFontScale.setTextSize(16);
            txtFontScale.setPadding(0, 15, 0, 0);
            txtFontScaledis.setPadding(0, 0, 0, 15);
            txtFontScaledis.setTextColor(TextDisColor);
            txtFontScaledis.setTextSize(16);
            String fontsize = String.valueOf(SplashActivity.fontSize);
            txtFontScaledis.setText(fontsize);
            txtFontScaledis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtFontScale.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtFontScale);
            llayout.addView(txtFontScaledis);
            llayout.addView(v2);

            TextView txtScreenPhysical = new TextView(getContext());
            TextView txtScreenPhysicaldis = new TextView(getContext());
            View v3 = new View(getContext());
            v3.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v3.setBackgroundColor(LineColor);
            txtScreenPhysical.setText(R.string.PhysicalSize);
            txtScreenPhysical.setTypeface(null, Typeface.BOLD);
            txtScreenPhysical.setTextSize(16);
            txtScreenPhysical.setPadding(0, 15, 0, 0);
            txtScreenPhysicaldis.setPadding(0, 0, 0, 15);
            txtScreenPhysicaldis.setTextColor(TextDisColor);
            txtScreenPhysicaldis.setTextSize(16);
            String physical_size = SplashActivity.displayPhysicalSize + " inches";
            txtScreenPhysicaldis.setText(physical_size);
            txtScreenPhysicaldis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtFontScale.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtScreenPhysical);
            llayout.addView(txtScreenPhysicaldis);
            llayout.addView(v3);

            TextView txtRefreshRate = new TextView(getContext());
            TextView txtRefreshRatedis = new TextView(getContext());
            View v4 = new View(getContext());
            v4.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v4.setBackgroundColor(LineColor);
            txtRefreshRate.setText(R.string.RefreshRate);
            txtRefreshRate.setTypeface(null, Typeface.BOLD);
            txtRefreshRate.setTextSize(16);
            txtRefreshRate.setPadding(0, 15, 0, 0);
            txtRefreshRatedis.setPadding(0, 0, 0, 15);
            txtRefreshRatedis.setTextColor(TextDisColor);
            txtRefreshRatedis.setTextSize(16);
            String rrate = String.valueOf(SplashActivity.diplayRefreshRate) + " Hz";
            txtRefreshRatedis.setText(rrate);
            txtRefreshRatedis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtRefreshRate.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtRefreshRate);
            llayout.addView(txtRefreshRatedis);
            llayout.addView(v4);

            TextView txtOrientation = new TextView(getContext());
            TextView txtOrientationdis = new TextView(getContext());
            View v5 = new View(getContext());
            v5.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
            v5.setBackgroundColor(LineColor);
            txtOrientation.setText(R.string.Orientation);
            txtOrientation.setTypeface(null, Typeface.BOLD);
            txtOrientation.setTextSize(16);
            txtOrientation.setPadding(0, 15, 0, 0);
            txtOrientationdis.setPadding(0, 0, 0, 15);
            txtOrientationdis.setTextColor(TextDisColor);
            txtOrientationdis.setTextSize(16);
            txtOrientationdis.setText(SplashActivity.displayOrientation);
            txtOrientationdis.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            txtOrientation.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            llayout.addView(txtOrientation);
            llayout.addView(txtOrientationdis);
            llayout.addView(v5);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rootView;
    }
}
