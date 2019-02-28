package com.ytheekshana.deviceinfo;

import android.Manifest;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import android.util.SizeF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class tabCamera extends Fragment {
    Context context;
    private RecyclerView recyclerCamera;
    private RecyclerView.Adapter cameraAdapter;
    private ArrayList<CameraInfo> featureList2;
    private CameraManager cameraManager;
    private SingleSelectToggleGroup cameraButtonGroup;
    private int textDisColor, lineColor;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        context = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tabcamera, container, false);

        try {
            textDisColor = MainActivity.themeColor;
            lineColor = GetDetails.getThemeColor(Objects.requireNonNull(getContext()), R.attr.colorButtonNormal);

            HorizontalScrollView scrollViewCamera = rootView.findViewById(R.id.scrollViewCamera);
            cameraButtonGroup = rootView.findViewById(R.id.cameraButtonGroup);
            recyclerCamera = rootView.findViewById(R.id.recyclerCamera);
            Button btnCameraPermission = rootView.findViewById(R.id.btnCameraPermission);
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                btnCameraPermission.setVisibility(View.VISIBLE);
                scrollViewCamera.setVisibility(View.GONE);
                recyclerCamera.setVisibility(View.GONE);
            } else {
                btnCameraPermission.setVisibility(View.GONE);
                scrollViewCamera.setVisibility(View.VISIBLE);
                recyclerCamera.setVisibility(View.VISIBLE);
                loadCameraAll();
            }
            btnCameraPermission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Permissions.check(context, Manifest.permission.CAMERA, null, new PermissionHandler() {
                        @Override
                        public void onGranted() {
                            Objects.requireNonNull(getFragmentManager()).beginTransaction().detach(tabCamera.this).attach(tabCamera.this).commit();
                        }

                        @Override
                        public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                        }
                    });
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rootView;
    }

    private void loadCameraAll() {
        ArrayList<CameraInfo> featureList = new ArrayList<>();
        getCameraMetaData();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        cameraAdapter = new CameraAdapter(context, lineColor, textDisColor, featureList);
        recyclerCamera.setLayoutManager(layoutManager);
        recyclerCamera.setAdapter(cameraAdapter);

        featureList2 = new ArrayList<>();
        cameraButtonGroup.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {
                final CameraButton cameraButton = group.findViewById(group.getCheckedId());
                getCameraDetails(String.valueOf(cameraButton.getCameraId()), featureList2);
                ((CameraAdapter) cameraAdapter).addData(featureList2);
                cameraAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getCameraMetaData() {
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {

            int a = 0;
            for (final String cameraId : Objects.requireNonNull(cameraManager).getCameraIdList()) {

                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);

                StreamConfigurationMap streamConfigurationMap = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                Size[] sizes = Objects.requireNonNull(streamConfigurationMap).getOutputSizes(ImageFormat.JPEG);
                Integer lensFacing = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                String lens = "";
                if (lensFacing != null) {
                    switch (lensFacing) {
                        case CameraCharacteristics.LENS_FACING_BACK:
                            lens = "Back";
                            break;
                        case CameraCharacteristics.LENS_FACING_FRONT:
                            lens = "Front";
                            break;
                        case CameraCharacteristics.LENS_FACING_EXTERNAL:
                            lens = "External";
                            break;
                        default:
                            lens = "Unknown";
                            break;
                    }
                }
                float[] flenths = cameraCharacteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
                CameraButton cameraButton = new CameraButton(context);
                cameraButton.setMp(GetDetails.getCameraMP(sizes) + " - " + lens);
                cameraButton.setResolution(GetDetails.getCameraResolution(sizes));
                cameraButton.setFlength(String.valueOf(Objects.requireNonNull(flenths)[0]) + "mm");
                cameraButton.setCameraId(cameraId);
                if (a == 0) {
                    cameraButton.setChecked(true);
                }
                a++;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 0, 8, 0);
                cameraButton.setLayoutParams(params);
                cameraButtonGroup.addView(cameraButton);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void getCameraDetails(String cameraId, ArrayList<CameraInfo> list) {
        try {
            CameraCharacteristics camchar = cameraManager.getCameraCharacteristics(cameraId);
            list.clear();
            list.add(new CameraInfo(getString(R.string.cameraNoteTitle), getString(R.string.cameraNote)));
            for (CameraCharacteristics.Key key : camchar.getKeys()) {

                String featureValue = getCameraFeatureValue(key, camchar);
                if (!featureValue.trim().equals("")) {
                    list.add(new CameraInfo(GetDetails.getKeyName(key.getName()), featureValue));
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private String getCameraFeatureValue(CameraCharacteristics.Key key, CameraCharacteristics characteristics) {
        List<String> values = new ArrayList<>();

        if (key == CameraCharacteristics.COLOR_CORRECTION_AVAILABLE_ABERRATION_MODES) {
            int[] intChar = (int[]) characteristics.get(key);
            for (int character : intChar != null ? intChar : new int[0]) {
                if (character == CameraCharacteristics.COLOR_CORRECTION_ABERRATION_MODE_OFF) {
                    values.add("Off");
                } else if (character == CameraCharacteristics.COLOR_CORRECTION_ABERRATION_MODE_FAST) {
                    values.add("Fast");
                } else if (character == CameraCharacteristics.COLOR_CORRECTION_ABERRATION_MODE_HIGH_QUALITY) {
                    values.add("High Quality");
                }
            }

        } else if (key == CameraCharacteristics.CONTROL_AE_AVAILABLE_ANTIBANDING_MODES) {
            int[] intChar = (int[]) characteristics.get(key);
            for (int character : intChar != null ? intChar : new int[0]) {
                if (character == CameraCharacteristics.CONTROL_AE_ANTIBANDING_MODE_OFF) {
                    values.add("Off");
                } else if (character == CameraCharacteristics.CONTROL_AE_ANTIBANDING_MODE_AUTO) {
                    values.add("Auto");
                } else if (character == CameraCharacteristics.CONTROL_AE_ANTIBANDING_MODE_50HZ) {
                    values.add("50Hz");
                } else if (character == CameraCharacteristics.CONTROL_AE_ANTIBANDING_MODE_60HZ) {
                    values.add("60Hz");
                }
            }
        } else if (key == CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES) {
            int[] intChar = (int[]) characteristics.get(key);
            for (int character : intChar != null ? intChar : new int[0]) {
                if (character == CameraCharacteristics.CONTROL_AE_MODE_OFF) {
                    values.add("Off");
                } else if (character == CameraCharacteristics.CONTROL_AE_MODE_ON) {
                    values.add("On");
                } else if (character == CameraCharacteristics.CONTROL_AE_MODE_ON_ALWAYS_FLASH) {
                    values.add("Always Flash");
                } else if (character == CameraCharacteristics.CONTROL_AE_MODE_ON_AUTO_FLASH) {
                    values.add("Auto Flash");
                } else if (character == CameraCharacteristics.CONTROL_AE_MODE_ON_AUTO_FLASH_REDEYE) {
                    values.add("Auto Flash Redeye");
                }
            }
        } else if (key == CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES) {
            Range<Integer>[] intChar = (Range<Integer>[]) characteristics.get(key);
            assert intChar != null;
            for (Range<Integer> character : intChar) {
                values.add(getRange(character));
            }
        } else if (key == CameraCharacteristics.CONTROL_AE_COMPENSATION_RANGE) {
            Range<Integer> character = (Range<Integer>) characteristics.get(key);
            values.add(getRange(Objects.requireNonNull(character)));
        } else if (key == CameraCharacteristics.CONTROL_AE_COMPENSATION_STEP) {
            Rational character = (Rational) characteristics.get(key);
            values.add(Objects.requireNonNull(character).toString());
        } else if (key == CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES) {
            int[] intChar = (int[]) characteristics.get(key);
            for (int character : intChar != null ? intChar : new int[0]) {
                if (character == CameraCharacteristics.CONTROL_AF_MODE_OFF) {
                    values.add("Off");
                } else if (character == CameraCharacteristics.CONTROL_AF_MODE_AUTO) {
                    values.add("Auto");
                } else if (character == CameraCharacteristics.CONTROL_AF_MODE_EDOF) {
                    values.add("EDOF");
                } else if (character == CameraCharacteristics.CONTROL_AF_MODE_MACRO) {
                    values.add("Macro");
                } else if (character == CameraCharacteristics.CONTROL_AF_MODE_CONTINUOUS_PICTURE) {
                    values.add("Continuous Picture");
                } else if (character == CameraCharacteristics.CONTROL_AF_MODE_CONTINUOUS_VIDEO) {
                    values.add("Continuous Video");
                }
            }
        } else if (key == CameraCharacteristics.CONTROL_AVAILABLE_EFFECTS) {
            int[] intChar = (int[]) characteristics.get(key);
            for (int character : intChar != null ? intChar : new int[0]) {
                if (character == CameraCharacteristics.CONTROL_EFFECT_MODE_OFF) {
                    values.add("Off");
                } else if (character == CameraCharacteristics.CONTROL_EFFECT_MODE_AQUA) {
                    values.add("Aqua");
                } else if (character == CameraCharacteristics.CONTROL_EFFECT_MODE_BLACKBOARD) {
                    values.add("Blackboard");
                } else if (character == CameraCharacteristics.CONTROL_EFFECT_MODE_MONO) {
                    values.add("Mono");
                } else if (character == CameraCharacteristics.CONTROL_EFFECT_MODE_NEGATIVE) {
                    values.add("Negative");
                } else if (character == CameraCharacteristics.CONTROL_EFFECT_MODE_POSTERIZE) {
                    values.add("Posterize");
                } else if (character == CameraCharacteristics.CONTROL_EFFECT_MODE_SEPIA) {
                    values.add("Sepia");
                } else if (character == CameraCharacteristics.CONTROL_EFFECT_MODE_SOLARIZE) {
                    values.add("Solarize");
                } else if (character == CameraCharacteristics.CONTROL_EFFECT_MODE_WHITEBOARD) {
                    values.add("Whiteboard");
                }
            }
        } else if (key == CameraCharacteristics.CONTROL_AVAILABLE_SCENE_MODES) {
            int[] intChar = (int[]) characteristics.get(key);
            for (int character : intChar != null ? intChar : new int[0]) {
                if (character == CameraCharacteristics.CONTROL_SCENE_MODE_DISABLED) {
                    values.add("Disabled");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_ACTION) {
                    values.add("Action");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_BARCODE) {
                    values.add("Barcode");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_BEACH) {
                    values.add("Beach");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_CANDLELIGHT) {
                    values.add("Candlelight");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_FACE_PRIORITY) {
                    values.add("Face Priority");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_FIREWORKS) {
                    values.add("Fireworks");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_HDR) {
                    values.add("HDR");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_LANDSCAPE) {
                    values.add("Landscape");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_NIGHT) {
                    values.add("Night");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_NIGHT_PORTRAIT) {
                    values.add("Night Portrait");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_PARTY) {
                    values.add("Party");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_PORTRAIT) {
                    values.add("Portrait");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_SNOW) {
                    values.add("Snow");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_SPORTS) {
                    values.add("Sports");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_STEADYPHOTO) {
                    values.add("Steady Photo");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_SUNSET) {
                    values.add("Sunset");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_THEATRE) {
                    values.add("Theatre");
                } else if (character == CameraCharacteristics.CONTROL_SCENE_MODE_HIGH_SPEED_VIDEO) {
                    values.add("High Speed Video");
                }
            }
        } else if (key == CameraCharacteristics.CONTROL_AVAILABLE_VIDEO_STABILIZATION_MODES) {
            int[] intChar = (int[]) characteristics.get(key);
            for (int character : intChar != null ? intChar : new int[0]) {
                if (character == CameraCharacteristics.CONTROL_VIDEO_STABILIZATION_MODE_ON) {
                    values.add("On");
                } else if (character == CameraCharacteristics.CONTROL_VIDEO_STABILIZATION_MODE_OFF) {
                    values.add("Off");
                }
            }
        } else if (key == CameraCharacteristics.CONTROL_AWB_AVAILABLE_MODES) {
            int[] intChar = (int[]) characteristics.get(key);
            for (int character : intChar != null ? intChar : new int[0]) {
                if (character == CameraCharacteristics.CONTROL_AWB_MODE_OFF) {
                    values.add("Off");
                } else if (character == CameraCharacteristics.CONTROL_AWB_MODE_AUTO) {
                    values.add("Auto");
                } else if (character == CameraCharacteristics.CONTROL_AWB_MODE_CLOUDY_DAYLIGHT) {
                    values.add("Cloudy Daylight");
                } else if (character == CameraCharacteristics.CONTROL_AWB_MODE_DAYLIGHT) {
                    values.add("Daylight");
                } else if (character == CameraCharacteristics.CONTROL_AWB_MODE_FLUORESCENT) {
                    values.add("Fluorescent");
                } else if (character == CameraCharacteristics.CONTROL_AWB_MODE_INCANDESCENT) {
                    values.add("Incandescent");
                } else if (character == CameraCharacteristics.CONTROL_AWB_MODE_SHADE) {
                    values.add("Shade");
                } else if (character == CameraCharacteristics.CONTROL_AWB_MODE_TWILIGHT) {
                    values.add("Twilight");
                } else if (character == CameraCharacteristics.CONTROL_AWB_MODE_WARM_FLUORESCENT) {
                    values.add("Warm Fluorescent");
                }
            }
        } else if (key == CameraCharacteristics.CONTROL_MAX_REGIONS_AE) {
            int character = (int) characteristics.get(key);
            values.add(String.valueOf(character));
        } else if (key == CameraCharacteristics.CONTROL_MAX_REGIONS_AF) {
            int character = (int) characteristics.get(key);
            values.add(String.valueOf(character));
        } else if (key == CameraCharacteristics.CONTROL_MAX_REGIONS_AWB) {
            int character = (int) characteristics.get(key);
            values.add(String.valueOf(character));
        } else if (key == CameraCharacteristics.EDGE_AVAILABLE_EDGE_MODES) {
            int[] intChar = (int[]) characteristics.get(key);
            for (int character : intChar != null ? intChar : new int[0]) {
                if (character == CameraCharacteristics.EDGE_MODE_OFF) {
                    values.add("Off");
                } else if (character == CameraCharacteristics.EDGE_MODE_FAST) {
                    values.add("Fast");
                } else if (character == CameraCharacteristics.EDGE_MODE_HIGH_QUALITY) {
                    values.add("High Quality");
                } else if (character == CameraCharacteristics.EDGE_MODE_ZERO_SHUTTER_LAG) {
                    values.add("Zero Shutter Lag");
                }
            }
        } else if (key == CameraCharacteristics.FLASH_INFO_AVAILABLE) {
            boolean character = (boolean) characteristics.get(key);
            values.add(character ? "Yes" : "No");
        } else if (key == CameraCharacteristics.HOT_PIXEL_AVAILABLE_HOT_PIXEL_MODES) {
            int[] intChar = (int[]) characteristics.get(key);
            for (int character : intChar != null ? intChar : new int[0]) {
                if (character == CameraCharacteristics.HOT_PIXEL_MODE_OFF) {
                    values.add("Off");
                } else if (character == CameraCharacteristics.HOT_PIXEL_MODE_FAST) {
                    values.add("Fast");
                } else if (character == CameraCharacteristics.HOT_PIXEL_MODE_HIGH_QUALITY) {
                    values.add("High Quality");
                }
            }
        } else if (key == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL) {
            int character = (int) characteristics.get(key);
            if (character == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_3) {
                values.add("Level 3");
            } else if (character == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_EXTERNAL) {
                values.add("External");
            } else if (character == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL) {
                values.add("Full");
            } else if (character == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY) {
                values.add("Legacy");
            } else if (character == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED) {
                values.add("Limited");
            }
        } else if (key == CameraCharacteristics.JPEG_AVAILABLE_THUMBNAIL_SIZES) {
            Size[] character = (Size[]) characteristics.get(key);
            for (Size size : character != null ? character : new Size[0]) {
                values.add(String.valueOf(size.getWidth()) + " x " + String.valueOf(size.getHeight()));
            }
        } else if (key == CameraCharacteristics.LENS_FACING) {
            int character = (int) characteristics.get(key);
            if (character == CameraCharacteristics.LENS_FACING_BACK) {
                values.add("Back");
            } else if (character == CameraCharacteristics.LENS_FACING_EXTERNAL) {
                values.add("External");
            } else if (character == CameraCharacteristics.LENS_FACING_FRONT) {
                values.add("Front");
            } else {
                values.add("Unknown");
            }
        } else if (key == CameraCharacteristics.LENS_INFO_AVAILABLE_APERTURES) {
            float[] floatChar = (float[]) characteristics.get(key);
            for (float character : floatChar != null ? floatChar : new float[0]) {
                values.add(String.valueOf(character));
            }
        } else if (key == CameraCharacteristics.LENS_INFO_AVAILABLE_FILTER_DENSITIES) {
            float[] floatChar = (float[]) characteristics.get(key);
            for (float character : floatChar != null ? floatChar : new float[0]) {
                values.add(String.valueOf(character));
            }
        } else if (key == CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS) {
            float[] floatChar = (float[]) characteristics.get(key);
            for (float character : floatChar != null ? floatChar : new float[0]) {
                values.add(String.valueOf(character) + "mm");
            }
        } else if (key == CameraCharacteristics.LENS_INFO_AVAILABLE_OPTICAL_STABILIZATION) {
            int[] intChar = (int[]) characteristics.get(key);
            for (int character : intChar != null ? intChar : new int[0]) {
                if (character == CameraCharacteristics.LENS_OPTICAL_STABILIZATION_MODE_OFF) {
                    values.add("Off");
                } else if (character == CameraCharacteristics.LENS_OPTICAL_STABILIZATION_MODE_ON) {
                    values.add("On");
                }
            }
        } else if (key == CameraCharacteristics.LENS_INFO_FOCUS_DISTANCE_CALIBRATION) {
            int character = (int) characteristics.get(key);
            if (character == CameraCharacteristics.LENS_INFO_FOCUS_DISTANCE_CALIBRATION_APPROXIMATE) {
                values.add("Approximate");
            } else if (character == CameraCharacteristics.LENS_INFO_FOCUS_DISTANCE_CALIBRATION_CALIBRATED) {
                values.add("Calibrated");
            } else if (character == CameraCharacteristics.LENS_INFO_FOCUS_DISTANCE_CALIBRATION_UNCALIBRATED) {
                values.add("Uncalibrated");
            }
        } else if (key == CameraCharacteristics.LENS_INFO_HYPERFOCAL_DISTANCE) {
            float character = (float) characteristics.get(key);
            values.add(String.valueOf(character));
        } else if (key == CameraCharacteristics.LENS_INFO_MINIMUM_FOCUS_DISTANCE) {
            float character = (float) characteristics.get(key);
            values.add(String.valueOf(character));
        } else if (key == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES) {
            int[] intChar = (int[]) characteristics.get(key);
            for (int character : intChar != null ? intChar : new int[0]) {
                if (character == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE) {
                    values.add("Backward Compatible");
                } else if (character == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_BURST_CAPTURE) {
                    values.add("Burst Capture");
                } else if (character == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_CONSTRAINED_HIGH_SPEED_VIDEO) {
                    values.add("Constrained High Speed Video");
                } else if (character == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_DEPTH_OUTPUT) {
                    values.add("Depth Output");
                } else if (character == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_LOGICAL_MULTI_CAMERA) {
                    values.add("Logical Multi Camera");
                } else if (character == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_MANUAL_POST_PROCESSING) {
                    values.add("Manual Post Processing");
                } else if (character == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_MANUAL_SENSOR) {
                    values.add("Manual Sensor");
                } else if (character == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_MONOCHROME) {
                    values.add("Monochrome");
                } else if (character == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_MOTION_TRACKING) {
                    values.add("Motion Tracking");
                } else if (character == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_PRIVATE_REPROCESSING) {
                    values.add("Private Reprocessing");
                } else if (character == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_RAW) {
                    values.add("Raw");
                } else if (character == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_READ_SENSOR_SETTINGS) {
                    values.add("Read Sensor Settings");
                } else if (character == CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES_YUV_REPROCESSING) {
                    values.add("YUV Reprocessing");
                }
            }
        } else if (key == CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC) {
            int character = (int) characteristics.get(key);
            values.add(String.valueOf(character));
        } else if (key == CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_PROC_STALLING) {
            int character = (int) characteristics.get(key);
            values.add(String.valueOf(character));
        } else if (key == CameraCharacteristics.REQUEST_MAX_NUM_OUTPUT_RAW) {
            int character = (int) characteristics.get(key);
            values.add(String.valueOf(character));
        } else if (key == CameraCharacteristics.REQUEST_PARTIAL_RESULT_COUNT) {
            int character = (int) characteristics.get(key);
            values.add(String.valueOf(character));
        } else if (key == CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM) {
            float character = (float) characteristics.get(key);
            values.add(String.valueOf(character));
        } else if (key == CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP) {
            StreamConfigurationMap character = (StreamConfigurationMap) characteristics.get(key);
            Size[] intArray = Objects.requireNonNull(character).getOutputSizes(ImageFormat.JPEG);
            for (Size size : intArray) {
                values.add(GetDetails.getMP(size, 2) + " - " + String.valueOf(size.getWidth()) + " x " + String.valueOf(size.getHeight()));
            }
        } else if (key == CameraCharacteristics.SCALER_CROPPING_TYPE) {
            int character = (int) characteristics.get(key);
            if (character == CameraCharacteristics.SCALER_CROPPING_TYPE_CENTER_ONLY) {
                values.add("Center Only");
            } else if (character == CameraCharacteristics.SCALER_CROPPING_TYPE_FREEFORM) {
                values.add("Freeform");
            }
        } else if (key == CameraCharacteristics.SENSOR_AVAILABLE_TEST_PATTERN_MODES) {
            int[] intChar = (int[]) characteristics.get(key);
            for (int character : intChar != null ? intChar : new int[0]) {
                if (character == CameraCharacteristics.SENSOR_TEST_PATTERN_MODE_OFF) {
                    values.add("Off");
                } else if (character == CameraCharacteristics.SENSOR_TEST_PATTERN_MODE_COLOR_BARS) {
                    values.add("Color Bars");
                } else if (character == CameraCharacteristics.SENSOR_TEST_PATTERN_MODE_COLOR_BARS_FADE_TO_GRAY) {
                    values.add("Color Bars Fade to Gray");
                } else if (character == CameraCharacteristics.SENSOR_TEST_PATTERN_MODE_CUSTOM1) {
                    values.add("Custom 1");
                } else if (character == CameraCharacteristics.SENSOR_TEST_PATTERN_MODE_PN9) {
                    values.add("PN9");
                } else if (character == CameraCharacteristics.SENSOR_TEST_PATTERN_MODE_SOLID_COLOR) {
                    values.add("Solid Color");
                }
            }
        } else if (key == CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT) {
            int character = (int) characteristics.get(key);
            if (character == CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_BGGR) {
                values.add("BGGR");
            } else if (character == CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_GBRG) {
                values.add("GBRG");
            } else if (character == CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_GRBG) {
                values.add("GRGB");
            } else if (character == CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_RGB) {
                values.add("RGB");
            } else if (character == CameraCharacteristics.SENSOR_INFO_COLOR_FILTER_ARRANGEMENT_RGGB) {
                values.add("RGGB");
            }
        } else if (key == CameraCharacteristics.SENSOR_INFO_TIMESTAMP_SOURCE) {
            int character = (int) characteristics.get(key);
            if (character == CameraCharacteristics.SENSOR_INFO_TIMESTAMP_SOURCE_REALTIME) {
                values.add("Realtime");
            } else if (character == CameraCharacteristics.SENSOR_INFO_TIMESTAMP_SOURCE_UNKNOWN) {
                values.add("Unknown");
            }
        } else if (key == CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE) {
            SizeF character = (SizeF) characteristics.get(key);
            values.add(String.format(Locale.US, "%.2f", Objects.requireNonNull(character).getWidth()) + " x " + String.format(Locale.US, "%.2f", character.getHeight()));
        } else if (key == CameraCharacteristics.SENSOR_INFO_PIXEL_ARRAY_SIZE) {
            Size size = (Size) characteristics.get(key);
            values.add(String.valueOf(Objects.requireNonNull(size).getWidth()) + " x " + String.valueOf(size.getHeight()));
        } else if (key == CameraCharacteristics.SENSOR_ORIENTATION) {
            int character = (int) characteristics.get(key);
            values.add(String.valueOf(character) + " deg");
        } else if (key == CameraCharacteristics.STATISTICS_INFO_AVAILABLE_FACE_DETECT_MODES) {
            int[] intChar = (int[]) characteristics.get(key);
            for (int character : intChar != null ? intChar : new int[0]) {
                if (character == CameraCharacteristics.STATISTICS_FACE_DETECT_MODE_OFF) {
                    values.add("Off");
                } else if (character == CameraCharacteristics.STATISTICS_FACE_DETECT_MODE_FULL) {
                    values.add("Full");
                } else if (character == CameraCharacteristics.STATISTICS_FACE_DETECT_MODE_SIMPLE) {
                    values.add("Simple");
                }
            }
        }

        if (key == CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP || key == CameraCharacteristics.JPEG_AVAILABLE_THUMBNAIL_SIZES) {
            return (values.toString().substring(1, values.toString().length() - 1)).replace(", ", "\n");

        } else {
            return values.toString().substring(1, values.toString().length() - 1);
        }
    }

    private String getRange(Range<Integer> character) {
        return "[" + character.getLower().toString() + "," + character.getUpper().toString() + "]";
    }
}
