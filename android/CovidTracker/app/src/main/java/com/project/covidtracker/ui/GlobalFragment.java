package com.project.covidtracker.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.project.covidtracker.R;

import java.util.ArrayList;
import java.util.List;

import models.GlobalLatest;
import network.CovidDataApi;
import network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class GlobalFragment extends Fragment {

    private PieChart pieChart;
    private static final String TAG = "GlobalFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_global, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pieChart = view.findViewById(R.id.global_pie_chart);
        pieChart.setCenterText("Global");
        getGlobalLatestData();
    }

    private void getGlobalLatestData() {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance(getActivity().getApplicationContext());
        CovidDataApi covidDataApi = retrofit.create(CovidDataApi.class);
        Call<GlobalLatest[]> call = covidDataApi.getLatestGlobalData();
        call.enqueue(new Callback<GlobalLatest[]>() {
            @Override
            public void onResponse(Call<GlobalLatest[]> call, Response<GlobalLatest[]> response) {
                GlobalLatest[] globalLatest = response.body();
                List<PieEntry> pieEntries = new ArrayList();
                pieEntries.add(new PieEntry(globalLatest[0].getConfirmed(), "Confirmed"));
                pieEntries.add(new PieEntry(globalLatest[0].getCritical(), "Critical"));
                pieEntries.add(new PieEntry(globalLatest[0].getRecovered(), "Recovered"));
                pieEntries.add(new PieEntry(globalLatest[0].getDeaths(), "Deaths"));
                PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                PieData pieData = new PieData(pieDataSet);
                pieChart.setData(pieData);
                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                pieDataSet.setSliceSpace(2f);
                pieDataSet.setValueTextColor(Color.WHITE);
                pieDataSet.setValueTextSize(10f);
                pieChart.invalidate();
                Log.d(TAG, "onResponse: GlobalLatest response" + response.toString());
                Log.d(TAG, "onResponse: GlobalLatest response" + globalLatest);
            }

            @Override
            public void onFailure(Call<GlobalLatest[]> call, Throwable t) {
                Log.d(TAG, "Failed getting global data!!!");
                Log.d(TAG, "" + t.getMessage());
                t.printStackTrace();
            }
        });
    }

}