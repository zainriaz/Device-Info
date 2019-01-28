package com.ytheekshana.deviceinfo;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SensorInfo> mDataSet;

    SensorAdapter(Context context, ArrayList<SensorInfo> list) {
        mContext = context;
        mDataSet = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_sensor_name;
        TextView txt_vendor_name;
        TextView txt_sensor_type;
        TextView txt_wake_up_type;
        TextView txt_sensor_power;

        ViewHolder(View view) {
            super(view);
            txt_sensor_name = view.findViewById(R.id.txt_sensor_name);
            txt_vendor_name = view.findViewById(R.id.txt_vendor_name);
            txt_sensor_type = view.findViewById(R.id.txt_sensor_type);
            txt_wake_up_type = view.findViewById(R.id.txt_wake_up_type);
            txt_sensor_power = view.findViewById(R.id.txt_sensor_power);
        }
    }

    @NonNull
    @Override
    public SensorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.sensor_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String txt_sensor_name = mDataSet.get(position).getSensorName();
        final String txt_vendor_name = mDataSet.get(position).getVendorName();
        final String txt_sensor_type = mDataSet.get(position).getSensorType();
        final String txt_wake_up_type = mDataSet.get(position).getWakeUpType();
        final String txt_sensor_power = mDataSet.get(position).getSensorPower();

        holder.txt_sensor_name.setText(txt_sensor_name);
        holder.txt_vendor_name.setText(txt_vendor_name);
        holder.txt_sensor_type.setText(txt_sensor_type);
        holder.txt_wake_up_type.setText(txt_wake_up_type);
        holder.txt_sensor_power.setText(txt_sensor_power);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}