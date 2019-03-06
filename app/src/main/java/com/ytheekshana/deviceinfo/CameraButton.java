package com.ytheekshana.deviceinfo;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.nex3z.togglebuttongroup.button.CompoundToggleButton;

import java.util.Objects;

import androidx.cardview.widget.CardView;

public class CameraButton extends CompoundToggleButton {
    String mp, resolution, flength, cameraId;
    TextView txtMp, txtResolution, txtFlength, txtCameraId;
    CardView cardViewButton, cardViewActivated;

    public CameraButton(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Objects.requireNonNull(inflater).inflate(R.layout.camerabutton, this, true);
        txtMp = findViewById(R.id.txtMP);
        txtResolution = findViewById(R.id.txtResolution);
        txtFlength = findViewById(R.id.txtFlength);
        txtCameraId = findViewById(R.id.txtCameraId);
        cardViewButton = findViewById(R.id.cardviewCamera);
        cardViewActivated = findViewById(R.id.cardViewActivated);
        cardViewButton.setCardBackgroundColor(MainActivity.themeColor);
        cardViewActivated.setCardBackgroundColor(MainActivity.themeColor);
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (checked) {
            if (MainActivity.isDarkmode) {
                animateButtonBar(MainActivity.themeColor, Color.parseColor("#EEEEEE"));
            } else {
                animateButtonBar(MainActivity.themeColor, Color.parseColor("#2F2F2F"));
            }
        } else {
            if (MainActivity.isDarkmode) {
                animateButtonBar(Color.parseColor("#EEEEEE"), MainActivity.themeColor);
            } else {
                animateButtonBar(Color.parseColor("#2F2F2F"), MainActivity.themeColor);
            }
        }
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
        txtResolution.setText(resolution);
    }

    public void setMp(String mp) {
        this.mp = mp;
        txtMp.setText(mp);
    }

    public void setFlength(String flength) {
        this.flength = flength;
        txtFlength.setText(flength);
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
        txtCameraId.setText(cameraId);
    }

    private void animateButtonBar(int fromColor, int toColor) {
        final ValueAnimator animateButton = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        animateButton.addUpdateListener(animator -> cardViewActivated.setCardBackgroundColor((int) animator.getAnimatedValue()));
        animateButton.setDuration(300);
        animateButton.setStartDelay(0);
        animateButton.start();
    }
}
