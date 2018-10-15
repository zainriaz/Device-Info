package com.ytheekshana.deviceinfo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class FingerprintTestActivity extends AppCompatActivity {
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editPrefs;
    Context context;
    ImageView imgFingerprintImage, imgIsHardwareAvailable, imgIsEnrolled;
    TextView txtFingerprintStatus;
    Button btnDone;
    FingerprintManagerCompat fingerprintManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            int themeId = sharedPrefs.getInt("ThemeBar", R.style.AppTheme);
            int themeColor = sharedPrefs.getInt("accent_color_dialog", Color.parseColor("#2196f3"));
            int themeColorDark = GetDetails.getDarkColor(this, themeColor);
            setTheme(themeId);

            if (sharedPrefs.getInt("ThemeBar", 0) != R.style.AppThemeDark) {
                Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(themeColor));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getWindow().setStatusBarColor(themeColorDark);
            }
            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
            ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(getString(R.string.app_name), icon, themeColor);
            setTaskDescription(taskDescription);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_test_fingerprint);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

            context = this;
            imgFingerprintImage = findViewById(R.id.imgFingerprintImage);
            txtFingerprintStatus = findViewById(R.id.txtFingerprintStatus);
            imgIsHardwareAvailable = findViewById(R.id.imgIsHardwareAvailable);
            imgIsEnrolled = findViewById(R.id.imgIsEnrolled);
            btnDone = findViewById(R.id.btnDone);
            sharedPrefs = getSharedPreferences("tests", MODE_PRIVATE);
            editPrefs = sharedPrefs.edit();

            fingerprintManagerCompat = FingerprintManagerCompat.from(context);
            Thread startFingerprint = new Thread() {
                @Override
                public void run() {
                    txtFingerprintStatus.post(new Runnable() {
                        @Override
                        public void run() {
                            txtFingerprintStatus.setText(R.string.fingerprint_test_start);
                        }
                    });
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!fingerprintManagerCompat.isHardwareDetected()) {
                        imgIsHardwareAvailable.post(new Runnable() {
                            @Override
                            public void run() {
                                imgIsHardwareAvailable.setImageResource(R.drawable.test_failed_alt);
                                imgIsHardwareAvailable.setVisibility(View.VISIBLE);
                            }
                        });
                        txtFingerprintStatus.post(new Runnable() {
                            @Override
                            public void run() {
                                txtFingerprintStatus.setText(R.string.fingerprint_test_failed_no_hardware);
                            }
                        });
                        btnDone.post(new Runnable() {
                            @Override
                            public void run() {
                                btnDone.setVisibility(View.VISIBLE);
                            }
                        });
                        editPrefs.putInt("fingerprint_test_status", 0);
                        editPrefs.apply();
                        editPrefs.commit();
                    } else if (!fingerprintManagerCompat.hasEnrolledFingerprints()) {
                        imgIsHardwareAvailable.post(new Runnable() {
                            @Override
                            public void run() {
                                imgIsEnrolled.setImageResource(R.drawable.test_failed_alt);
                                imgIsEnrolled.setVisibility(View.VISIBLE);
                            }
                        });
                        txtFingerprintStatus.post(new Runnable() {
                            @Override
                            public void run() {
                                txtFingerprintStatus.setText(R.string.fingerprint_test_failed_not_enrolled);
                            }
                        });
                        btnDone.post(new Runnable() {
                            @Override
                            public void run() {
                                btnDone.setVisibility(View.VISIBLE);
                            }
                        });
                        editPrefs.putInt("fingerprint_test_status", 0);
                        editPrefs.apply();
                        editPrefs.commit();
                    } else {
                        imgIsHardwareAvailable.post(new Runnable() {
                            @Override
                            public void run() {
                                imgIsHardwareAvailable.setImageResource(R.drawable.test_success_alt);
                                imgIsHardwareAvailable.setVisibility(View.VISIBLE);
                            }
                        });
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        imgIsEnrolled.post(new Runnable() {
                            @Override
                            public void run() {
                                imgIsEnrolled.setImageResource(R.drawable.test_success_alt);
                                imgIsEnrolled.setVisibility(View.VISIBLE);
                            }
                        });
                        editPrefs.putInt("fingerprint_test_status", 1);
                        editPrefs.apply();
                        editPrefs.commit();
                        txtFingerprintStatus.post(new Runnable() {
                            @Override
                            public void run() {
                                txtFingerprintStatus.setText(R.string.test_passed);
                            }
                        });
                        btnDone.post(new Runnable() {
                            @Override
                            public void run() {
                                btnDone.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            };
            startFingerprint.start();
            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        } catch (Exception ex) {
            txtFingerprintStatus.setText(R.string.test_failed);
            editPrefs.putInt("fingerprint_test_status", 0);
            editPrefs.apply();
            editPrefs.commit();
            ex.printStackTrace();
        }
    }
}
