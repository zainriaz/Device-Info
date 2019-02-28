package com.ytheekshana.deviceinfo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class tabThermal extends Fragment {
    Context context;
    private ArrayList<ThermalInfo> thermalList, thermalList2;
    private ThermalAdapter thermalAdapter;

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
        View rootView = inflater.inflate(R.layout.tabthermal, container, false);

        try {
            final RecyclerView recyclerThermal = rootView.findViewById(R.id.recyclerThermal);
            recyclerThermal.setItemAnimator(null);
            loadThermal();

            GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
            thermalAdapter = new ThermalAdapter(context, thermalList);
            recyclerThermal.setLayoutManager(layoutManager);
            recyclerThermal.setAdapter(thermalAdapter);

            thermalList2 = new ArrayList<>();
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    loadThermal();
                    thermalList2 = thermalList;
                    recyclerThermal.post(new Runnable() {
                        @Override
                        public void run() {
                            thermalAdapter.updateEmployeeListItems(thermalList2);
                        }
                    });
                }
            }, 1, 2, TimeUnit.SECONDS);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rootView;
    }

    private void loadThermal() {
        thermalList = new ArrayList<>();
        File dir = new File("/sys/devices/virtual/thermal/");
        File[] files = dir.listFiles();
        for (File file : files) {
            try {
                File tempFileValue = new File(file.getAbsolutePath() + "/temp");
                File tempFileName = new File(file.getAbsolutePath() + "/type");
                BufferedReader bufferedReaderValue = new BufferedReader(new FileReader(tempFileValue));
                BufferedReader bufferedReaderName = new BufferedReader(new FileReader(tempFileName));
                String lineName = bufferedReaderName.readLine();
                String lineValue = bufferedReaderValue.readLine();
                if (!lineValue.trim().equals("0")) {
                    thermalList.add(new ThermalInfo(lineName, GetDetails.getFormattedTemp(lineValue)));
                }
                bufferedReaderName.close();
                bufferedReaderValue.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
