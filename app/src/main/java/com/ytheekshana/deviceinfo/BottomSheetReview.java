package com.ytheekshana.deviceinfo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
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

public class BottomSheetReview extends BottomSheetDialogFragment {
    static BottomSheetReview newInstance() {
        return new BottomSheetReview();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.review_app_bottom, container, false);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int themeColor = sharedPrefs.getInt("accent_color_dialog", Color.parseColor("#2196f3"));
        RelativeLayout relativeLayout = view.findViewById(R.id.bottommain);
        relativeLayout.setBackgroundColor(themeColor);
        Button btnok = view.findViewById(R.id.btnReviewYes);
        btnok.setTextColor(themeColor);

        Button btnno = view.findViewById(R.id.btnReviewNo);
        GradientDrawable gradientDrawable = (GradientDrawable) btnno.getBackground();
        gradientDrawable.setColor(themeColor);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.ytheekshana.deviceinfo"));
                intent.setPackage("com.android.vending");
                startActivity(intent);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);

            }
        });

        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
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
