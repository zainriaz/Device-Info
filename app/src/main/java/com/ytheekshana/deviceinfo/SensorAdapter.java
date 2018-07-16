package com.ytheekshana.deviceinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SensorAdapter extends BaseAdapter{

    private LayoutInflater layoutInflater;
    private List<SensorList> listStorage;

    SensorAdapter(Context context, List<SensorList> customizedListView) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder listViewHolder;
        if (convertView == null) {
            listViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.sensor_list, parent, false);

            listViewHolder.sensorNameInListView = convertView.findViewById(R.id.list_sensor_name);
            listViewHolder.vendorNameInListView = convertView.findViewById(R.id.list_vendor_name);
            listViewHolder.sensorTypeInListView = convertView.findViewById(R.id.list_sensor_type);
            listViewHolder.wakeUpTypeInListView = convertView.findViewById(R.id.list_wake_up_type);
            listViewHolder.sensorPowerInListView = convertView.findViewById(R.id.list_sensor_power);
            convertView.setTag(listViewHolder);
        } else {
            listViewHolder = (ViewHolder) convertView.getTag();
        }
        listViewHolder.sensorNameInListView.setText(listStorage.get(position).getSensorName());
        listViewHolder.vendorNameInListView.setText(listStorage.get(position).getVenderName());
        listViewHolder.sensorTypeInListView.setText(listStorage.get(position).getSensorType());
        listViewHolder.wakeUpTypeInListView.setText(listStorage.get(position).getWakeUpType());
        listViewHolder.sensorPowerInListView.setText(listStorage.get(position).getSensorPower());

        return convertView;
    }

    static class ViewHolder {
        TextView sensorNameInListView;
        TextView vendorNameInListView;
        TextView sensorTypeInListView;
        TextView wakeUpTypeInListView;
        TextView sensorPowerInListView;
    }
}