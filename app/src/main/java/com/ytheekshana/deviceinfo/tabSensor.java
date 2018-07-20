package com.ytheekshana.deviceinfo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class tabSensor extends Fragment {

    List<SensorList> allSensors;
    SensorAdapter allSensorsAdapter;
    ListView listViewSensors;
    String getcount;
    TextView sensor_count;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.tabsensor, container, false);
        listViewSensors = rootView.findViewById(R.id.sensor_list);
        sensor_count = rootView.findViewById(R.id.sensor_count);
        final SwipeRefreshLayout swipesensorlist = rootView.findViewById(R.id.swipesensorlist);
        swipesensorlist.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadSensors();
                swipesensorlist.setRefreshing(false);
            }
        });
        LoadSensors();


        listViewSensors.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView listView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition = (listView == null || listView.getChildCount() == 0) ?
                        0 : listViewSensors.getChildAt(0).getTop();
                swipesensorlist.setEnabled((topRowVerticalPosition >= 0));
            }
        });

        return rootView;
    }

    private List<SensorList> getAllSensors() {

        List<SensorList> allsensors = new ArrayList<>();
        SensorManager mSensorManager = (SensorManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = Objects.requireNonNull(mSensorManager).getSensorList(Sensor.TYPE_ALL);

        for (Sensor s : deviceSensors) {
            String sensorName = s.getName();
            String sensorVendor = "Vendor : " + s.getVendor();
            String sensorType = "Type : " + GetDetails.GetSensorType(s.getType());
            String wakup = s.isWakeUpSensor()?"Yes":"No";
            String wakeUpType = "Wake Up Sensor : " + wakup;
            String sensorPower = "Power : "+s.getPower()+"mA";
            allsensors.add(new SensorList(sensorName, sensorVendor, sensorType,wakeUpType,sensorPower));
        }
        return allsensors;
    }

    private void LoadSensors(){
        allSensors = getAllSensors();
        allSensorsAdapter = new SensorAdapter(Objects.requireNonNull(getActivity()), allSensors);
        listViewSensors.setAdapter(allSensorsAdapter);
        getcount = String.valueOf(listViewSensors.getAdapter().getCount())+" Sensors are available on your device";
        sensor_count.setText(getcount);
    }
}