package com.ytheekshana.deviceinfo;

import android.widget.Filter;

import java.util.ArrayList;

public class AppFilter extends Filter {

    private AppAdapter adapter;
    private ArrayList<AppInfo> filterList;

    AppFilter(ArrayList<AppInfo> filterList, AppAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<AppInfo> filteredPlayers = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                if (filterList.get(i).getAppName().toUpperCase().contains(constraint)) {
                    filteredPlayers.add(filterList.get(i));
                }
            }
            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = filterList.size();
            results.values = filterList;

        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.mDataSet = (ArrayList<AppInfo>) results.values;
        adapter.notifyDataSetChanged();
    }
}