package com.ytheekshana.deviceinfo;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<CameraInfo> mDataSet;
    private int dividerColor, textColor;

    CameraAdapter(Context context, int dividerColor, int textColor, ArrayList<CameraInfo> list) {
        mContext = context;
        mDataSet = list;
        this.dividerColor = dividerColor;
        this.textColor = textColor;
    }

    void addData(ArrayList<CameraInfo> list){
        mDataSet.clear();
        mDataSet.addAll(list);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtFeatureName;
        TextView txtFeatureValue;
        View divider;

        ViewHolder(View view) {
            super(view);
            txtFeatureName = view.findViewById(R.id.txtFeatureName);
            txtFeatureValue = view.findViewById(R.id.txtFeatureValue);
            divider = view.findViewById(R.id.divider);
        }
    }

    @NonNull
    @Override
    public CameraAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.camera_feature_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String featureName = mDataSet.get(position).getFeatureName();
        final String featureValue = mDataSet.get(position).getFeatureValue();

        holder.txtFeatureName.setText(featureName);
        holder.txtFeatureName.setTypeface(null, Typeface.BOLD);
        holder.txtFeatureName.setTextSize(16);
        holder.txtFeatureName.setPadding(0, 15, 0, 0);

        holder.txtFeatureValue.setText(featureValue);
        holder.txtFeatureValue.setTextColor(textColor);
        holder.txtFeatureValue.setPadding(0, 0, 0, 15);
        holder.txtFeatureValue.setTextSize(16);

        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
        param.addRule(RelativeLayout.BELOW, R.id.txtFeatureValue);
        holder.divider.setLayoutParams(param);
        holder.divider.setBackgroundColor(dividerColor);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}