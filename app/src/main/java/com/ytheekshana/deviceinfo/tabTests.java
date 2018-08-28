package com.ytheekshana.deviceinfo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class tabTests extends Fragment {
    LinearLayout llayout;
    CardView cardviewFlashlight, cardviewDisplay, cardviewLoudSpeaker, cardviewEarSpeaker, cardviewEarProximity, cardviewVibration,
            cardviewVolumeUp, cardviewVolumeDown;
    ImageView imgFlashlightTest, imgDisplayTest, imgLoudSpeakerTest, imgEarSpeakerTest, imgEarProximityTest, imgVibrationTest,
            imgVolumeUpTest, imgVolumeDownTest;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabtests, container, false);
        imgFlashlightTest = rootView.findViewById(R.id.imgFlashlightTest);
        imgDisplayTest = rootView.findViewById(R.id.imgDisplayTest);
        imgLoudSpeakerTest = rootView.findViewById(R.id.imgLoudSpeakerTest);
        imgEarSpeakerTest = rootView.findViewById(R.id.imgEarSpeakerTest);
        imgEarProximityTest = rootView.findViewById(R.id.imgEarProximityTest);
        imgVibrationTest = rootView.findViewById(R.id.imgVibrationTest);
        imgVolumeUpTest = rootView.findViewById(R.id.imgVolumeUpTest);
        imgVolumeDownTest = rootView.findViewById(R.id.imgVolumeDownTest);

        cardviewFlashlight = rootView.findViewById(R.id.cardviewFlashlight);
        cardviewDisplay = rootView.findViewById(R.id.cardviewDisplay);
        cardviewLoudSpeaker = rootView.findViewById(R.id.cardviewLoudSpeaker);
        cardviewEarSpeaker = rootView.findViewById(R.id.cardviewEarSpeaker);
        cardviewEarProximity = rootView.findViewById(R.id.cardviewEarProximity);
        cardviewVibration = rootView.findViewById(R.id.cardviewVibration);
        cardviewVolumeUp = rootView.findViewById(R.id.cardviewVolumeUp);
        cardviewVolumeDown = rootView.findViewById(R.id.cardviewVolumeDown);

        if (!Objects.requireNonNull(getActivity()).getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            cardviewFlashlight.setVisibility(View.GONE);
        }
        cardviewFlashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadFlashlight = new Intent(getContext(), FlashlightTestActivity.class);
                startActivity(loadFlashlight);
                getActivity().overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadDisplay = new Intent(getContext(), DisplayTestActivity.class);
                startActivity(loadDisplay);
                getActivity().overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewLoudSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadLoudSpeaker = new Intent(getContext(), LoudSpeakerTestActivity.class);
                startActivity(loadLoudSpeaker);
                getActivity().overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewEarSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadEarSpeaker = new Intent(getContext(), EarSpeakerTestActivity.class);
                startActivity(loadEarSpeaker);
                getActivity().overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewEarProximity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadEarProximity = new Intent(getContext(), EarProximityTestActivity.class);
                startActivity(loadEarProximity);
                getActivity().overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadVibration = new Intent(getContext(), VibrationTestActivity.class);
                startActivity(loadVibration);
                getActivity().overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewVolumeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadVolumeUp = new Intent(getContext(), VolumeUpTestActivity.class);
                startActivity(loadVolumeUp);
                getActivity().overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });
        cardviewVolumeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadVolumeDown = new Intent(getContext(), VolumeDownTestActivity.class);
                startActivity(loadVolumeDown);
                getActivity().overridePendingTransition(R.anim.slide_activity_enter, R.anim.slide_activity_exit);
            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        updateTestData();
        super.onResume();
    }

    public void updateTestData() {
        SharedPreferences sharedPref = Objects.requireNonNull(getContext()).getSharedPreferences("tests", MODE_PRIVATE);
        int flashstatus = sharedPref.getInt("flashlight_test_status", 2);
        int displaystatus = sharedPref.getInt("display_test_status", 2);
        int loudspeakerstatus = sharedPref.getInt("loudspeaker_test_status", 2);
        int earspeakerstatus = sharedPref.getInt("earspeaker_test_status", 2);
        int earproximitystatus = sharedPref.getInt("earproximity_test_status", 2);
        int vibrationstatus = sharedPref.getInt("vibration_test_status", 2);
        int volumeup_test_status = sharedPref.getInt("volumeup_test_status", 2);
        int volumedown_test_status = sharedPref.getInt("volumedown_test_status", 2);

        if (flashstatus == 0) {
            imgFlashlightTest.setImageResource(R.drawable.test_failed);
            imgFlashlightTest.setColorFilter(getResources().getColor(R.color.test_failed));
        } else if (flashstatus == 1) {
            imgFlashlightTest.setImageResource(R.drawable.test_success);
            imgFlashlightTest.setColorFilter(getResources().getColor(R.color.test_success));
        } else if (flashstatus == 2) {
            imgFlashlightTest.setImageResource(R.drawable.test_default);
            imgFlashlightTest.setColorFilter(getResources().getColor(R.color.test_default));
        }

        if (displaystatus == 0) {
            imgDisplayTest.setImageResource(R.drawable.test_failed);
            imgDisplayTest.setColorFilter(getResources().getColor(R.color.test_failed));
        } else if (displaystatus == 1) {
            imgDisplayTest.setImageResource(R.drawable.test_success);
            imgDisplayTest.setColorFilter(getResources().getColor(R.color.test_success));
        } else if (displaystatus == 2) {
            imgDisplayTest.setImageResource(R.drawable.test_default);
            imgDisplayTest.setColorFilter(getResources().getColor(R.color.test_default));
        }

        if (loudspeakerstatus == 0) {
            imgLoudSpeakerTest.setImageResource(R.drawable.test_failed);
            imgLoudSpeakerTest.setColorFilter(getResources().getColor(R.color.test_failed));
        } else if (loudspeakerstatus == 1) {
            imgLoudSpeakerTest.setImageResource(R.drawable.test_success);
            imgLoudSpeakerTest.setColorFilter(getResources().getColor(R.color.test_success));
        } else if (loudspeakerstatus == 2) {
            imgLoudSpeakerTest.setImageResource(R.drawable.test_default);
            imgLoudSpeakerTest.setColorFilter(getResources().getColor(R.color.test_default));
        }

        if (earspeakerstatus == 0) {
            imgEarSpeakerTest.setImageResource(R.drawable.test_failed);
            imgEarSpeakerTest.setColorFilter(getResources().getColor(R.color.test_failed));
        } else if (earspeakerstatus == 1) {
            imgEarSpeakerTest.setImageResource(R.drawable.test_success);
            imgEarSpeakerTest.setColorFilter(getResources().getColor(R.color.test_success));
        } else if (earspeakerstatus == 2) {
            imgEarSpeakerTest.setImageResource(R.drawable.test_default);
            imgEarSpeakerTest.setColorFilter(getResources().getColor(R.color.test_default));
        }

        if (earproximitystatus == 0) {
            imgEarProximityTest.setImageResource(R.drawable.test_failed);
            imgEarProximityTest.setColorFilter(getResources().getColor(R.color.test_failed));
        } else if (earproximitystatus == 1) {
            imgEarProximityTest.setImageResource(R.drawable.test_success);
            imgEarProximityTest.setColorFilter(getResources().getColor(R.color.test_success));
        } else if (earproximitystatus == 2) {
            imgEarProximityTest.setImageResource(R.drawable.test_default);
            imgEarProximityTest.setColorFilter(getResources().getColor(R.color.test_default));
        }

        if (vibrationstatus == 0) {
            imgVibrationTest.setImageResource(R.drawable.test_failed);
            imgVibrationTest.setColorFilter(getResources().getColor(R.color.test_failed));
        } else if (vibrationstatus == 1) {
            imgVibrationTest.setImageResource(R.drawable.test_success);
            imgVibrationTest.setColorFilter(getResources().getColor(R.color.test_success));
        } else if (vibrationstatus == 2) {
            imgVibrationTest.setImageResource(R.drawable.test_default);
            imgVibrationTest.setColorFilter(getResources().getColor(R.color.test_default));
        }

        if (volumeup_test_status == 0) {
            imgVolumeUpTest.setImageResource(R.drawable.test_failed);
            imgVolumeUpTest.setColorFilter(getResources().getColor(R.color.test_failed));
        } else if (volumeup_test_status == 1) {
            imgVolumeUpTest.setImageResource(R.drawable.test_success);
            imgVolumeUpTest.setColorFilter(getResources().getColor(R.color.test_success));
        } else if (volumeup_test_status == 2) {
            imgVolumeUpTest.setImageResource(R.drawable.test_default);
            imgVolumeUpTest.setColorFilter(getResources().getColor(R.color.test_default));
        }

        if (volumedown_test_status == 0) {
            imgVolumeDownTest.setImageResource(R.drawable.test_failed);
            imgVolumeDownTest.setColorFilter(getResources().getColor(R.color.test_failed));
        } else if (volumedown_test_status == 1) {
            imgVolumeDownTest.setImageResource(R.drawable.test_success);
            imgVolumeDownTest.setColorFilter(getResources().getColor(R.color.test_success));
        } else if (volumedown_test_status == 2) {
            imgVolumeDownTest.setImageResource(R.drawable.test_default);
            imgVolumeDownTest.setColorFilter(getResources().getColor(R.color.test_default));
        }
    }
}