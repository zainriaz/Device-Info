package com.ytheekshana.deviceinfo;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.Objects;

public class BottomSheetEnjoy extends BottomSheetDialogFragment {
    static BottomSheetEnjoy newInstance() {
        return new BottomSheetEnjoy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.enjoy_app_bottom, container, false);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int themeColor = sharedPrefs.getInt("accent_color_dialog", Color.parseColor("#2196f3"));
        RelativeLayout relativeLayout = view.findViewById(R.id.bottommain);
        relativeLayout.setBackgroundColor(themeColor);
        Button btnok = view.findViewById(R.id.btnEnjoyYes);
        btnok.setTextColor(themeColor);

        Button btnno = view.findViewById(R.id.btnEnjoyNo);
        GradientDrawable gradientDrawable = (GradientDrawable) btnno.getBackground();
        gradientDrawable.setColor(themeColor);

        btnok.setOnClickListener(view12 -> {
            BottomSheetReview bottomSheetReview = BottomSheetReview.newInstance();
            bottomSheetReview.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "ReviewAppFragment");
            dismiss();
        });

        btnno.setOnClickListener(view1 -> dismiss());

        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        if (getActivity() != null) {
            getActivity().finish();
        }
        super.onDismiss(dialog);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogThemeTransparent);
    }
}
