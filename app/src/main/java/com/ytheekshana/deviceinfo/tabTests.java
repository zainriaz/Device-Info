package com.ytheekshana.deviceinfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class tabTests extends Fragment {

    private ImageView imgFlashlightTest, imgDisplayTest, imgLoudSpeakerTest, imgEarSpeakerTest, imgEarProximityTest, imgLightSensorTest, imgVibrationTest,
            imgWifiTest, imgBluetoothTest, imgFingerprintTest, imgVolumeUpTest, imgVolumeDownTest;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        CardView cardviewFlashlight, cardviewDisplay, cardviewLoudSpeaker, cardviewEarSpeaker, cardviewEarProximity, cardviewLightSensor, cardviewVibration,
                cardviewWifi, cardviewBluetooth, cardviewFingerprint, cardviewVolumeUp, cardviewVolumeDown;

        View rootView = inflater.inflate(R.layout.tabtests, container, false);
        imgFlashlightTest = rootView.findViewById(R.id.imgFlashlightTest);
        imgDisplayTest = rootView.findViewById(R.id.imgDisplayTest);
        imgLoudSpeakerTest = rootView.findViewById(R.id.imgLoudSpeakerTest);
        imgEarSpeakerTest = rootView.findViewById(R.id.imgEarSpeakerTest);
        imgEarProximityTest = rootView.findViewById(R.id.imgEarProximityTest);
        imgLightSensorTest = rootView.findViewById(R.id.imgLightSensorTest);
        imgVibrationTest = rootView.findViewById(R.id.imgVibrationTest);
        imgWifiTest = rootView.findViewById(R.id.imgWifiTest);
        imgBluetoothTest = rootView.findViewById(R.id.imgBluetoothTest);
        imgFingerprintTest = rootView.findViewById(R.id.imgFingerprintTest);
        imgVolumeUpTest = rootView.findViewById(R.id.imgVolumeUpTest);
        imgVolumeDownTest = rootView.findViewById(R.id.imgVolumeDownTest);

        cardviewFlashlight = rootView.findViewById(R.id.cardviewFlashlight);
        cardviewDisplay = rootView.findViewById(R.id.cardviewDisplay);
        cardviewLoudSpeaker = rootView.findViewById(R.id.cardviewLoudSpeaker);
        cardviewEarSpeaker = rootView.findViewById(R.id.cardviewEarSpeaker);
        cardviewEarProximity = rootView.findViewById(R.id.cardviewEarProximity);
        cardviewLightSensor = rootView.findViewById(R.id.cardviewLightSensor);
        cardviewVibration = rootView.findViewById(R.id.cardviewVibration);
        cardviewWifi = rootView.findViewById(R.id.cardviewWifi);
        cardviewBluetooth = rootView.findViewById(R.id.cardviewBluetooth);
        cardviewFingerprint = rootView.findViewById(R.id.cardviewFingerprint);
        cardviewVolumeUp = rootView.findViewById(R.id.cardviewVolumeUp);
        cardviewVolumeDown = rootView.findViewById(R.id.cardviewVolumeDown);

        if (!Objects.requireNonNull(getActivity()).getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            cardviewFlashlight.setVisibility(View.GONE);
        }
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (Objects.requireNonNull(sensorManager).getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) == null) {
            cardviewLightSensor.setVisibility(View.GONE);
        }
        if (!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_WIFI)) {
            cardviewWifi.setVisibility(View.GONE);
        }
        if (!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)) {
            cardviewBluetooth.setVisibility(View.GONE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
                cardviewFingerprint.setVisibility(View.GONE);
            }
        }


        cardviewFlashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadFlashlight = new Intent(getContext(), FlashlightTestActivity.class);
                startActivity(loadFlashlight);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadDisplay = new Intent(getContext(), DisplayTestActivity.class);
                startActivity(loadDisplay);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewLoudSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadLoudSpeaker = new Intent(getContext(), LoudSpeakerTestActivity.class);
                startActivity(loadLoudSpeaker);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewEarSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadEarSpeaker = new Intent(getContext(), EarSpeakerTestActivity.class);
                startActivity(loadEarSpeaker);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewEarProximity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadEarProximity = new Intent(getContext(), EarProximityTestActivity.class);
                startActivity(loadEarProximity);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewLightSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadLightSensor = new Intent(getContext(), LightSensorTestActivity.class);
                startActivity(loadLightSensor);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadVibration = new Intent(getContext(), VibrationTestActivity.class);
                startActivity(loadVibration);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadWifi = new Intent(getContext(), WifiTestActivity.class);
                startActivity(loadWifi);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadBluetooth = new Intent(getContext(), BluetoothTestActivity.class);
                startActivity(loadBluetooth);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadFingerprint = new Intent(getContext(), FingerprintTestActivity.class);
                startActivity(loadFingerprint);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewVolumeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadVolumeUp = new Intent(getContext(), VolumeUpTestActivity.class);
                startActivity(loadVolumeUp);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewVolumeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadVolumeDown = new Intent(getContext(), VolumeDownTestActivity.class);
                startActivity(loadVolumeDown);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        updateTestData();
        super.onResume();
    }

    private void updateTestData() {
        SharedPreferences sharedPref = Objects.requireNonNull(getContext()).getSharedPreferences("tests", MODE_PRIVATE);
        int flashstatus = sharedPref.getInt("flashlight_test_status", 2);
        int displaystatus = sharedPref.getInt("display_test_status", 2);
        int loudspeakerstatus = sharedPref.getInt("loudspeaker_test_status", 2);
        int earspeakerstatus = sharedPref.getInt("earspeaker_test_status", 2);
        int earproximitystatus = sharedPref.getInt("earproximity_test_status", 2);
        int lightsensorstatus = sharedPref.getInt("light_sensor_test_status", 2);
        int vibrationstatus = sharedPref.getInt("vibration_test_status", 2);
        int wifistatus = sharedPref.getInt("wifi_test_status", 2);
        int bluetoothstatus = sharedPref.getInt("bluetooth_test_status", 2);
        int fingerprintstatus = sharedPref.getInt("fingerprint_test_status", 2);
        int volumeup_test_status = sharedPref.getInt("volumeup_test_status", 2);
        int volumedown_test_status = sharedPref.getInt("volumedown_test_status", 2);

        updateImageView(imgFlashlightTest,flashstatus);
        updateImageView(imgDisplayTest,displaystatus);
        updateImageView(imgLoudSpeakerTest,loudspeakerstatus);
        updateImageView(imgEarSpeakerTest,earspeakerstatus);
        updateImageView(imgEarProximityTest,earproximitystatus);
        updateImageView(imgLightSensorTest,lightsensorstatus);
        updateImageView(imgVibrationTest,vibrationstatus);
        updateImageView(imgWifiTest,wifistatus);
        updateImageView(imgBluetoothTest,bluetoothstatus);
        updateImageView(imgFingerprintTest,fingerprintstatus);
        updateImageView(imgVolumeUpTest,volumeup_test_status);
        updateImageView(imgVolumeDownTest,volumedown_test_status);
    }

    private void updateImageView(ImageView imageView, int status){
        if (status == 0) {
            imageView.setImageResource(R.drawable.test_failed);
            imageView.setColorFilter(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.test_failed));
        } else if (status == 1) {
            imageView.setImageResource(R.drawable.test_success);
            imageView.setColorFilter(ContextCompat.getColor(Objects.requireNonNull(getContext()),R.color.test_success));
        } else if (status == 2) {
            imageView.setImageResource(R.drawable.test_default);
            imageView.setColorFilter(ContextCompat.getColor(Objects.requireNonNull(getContext()),R.color.test_default));
        }
    }
}