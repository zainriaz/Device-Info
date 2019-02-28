package com.ytheekshana.deviceinfo;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class ThermalDiffCallback extends DiffUtil.Callback {

    private final ArrayList<ThermalInfo> mOldThermalList;
    private final ArrayList<ThermalInfo> mNewThermalList;

    ThermalDiffCallback(ArrayList<ThermalInfo> oldThermalList, ArrayList<ThermalInfo> newThermalList) {
        this.mOldThermalList = oldThermalList;
        this.mNewThermalList = newThermalList;
    }

    @Override
    public int getOldListSize() {
        return mOldThermalList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewThermalList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldThermalList.get(oldItemPosition).getThermalName().equals(mNewThermalList.get(
                newItemPosition).getThermalName());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final ThermalInfo oldThermal = mOldThermalList.get(oldItemPosition);
        final ThermalInfo newThermal = mNewThermalList.get(newItemPosition);

        return oldThermal.getThermalValue().equals(newThermal.getThermalValue());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}