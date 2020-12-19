package com.project.covidtracker.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
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

    private Pie pie;
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

        pie = AnyChart.pie();
        getGlobalLatestData();
        AnyChartView globalPieView = (AnyChartView) view.findViewById(R.id.global_pie_chart);
        globalPieView.setChart(pie);
    }


    private void getGlobalLatestData() {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance(getActivity().getApplicationContext());
        CovidDataApi covidDataApi = retrofit.create(CovidDataApi.class);
        Call<GlobalLatest[]> call = covidDataApi.getLatestGlobalData();
        call.enqueue(new Callback<GlobalLatest[]>() {
            @Override
            public void onResponse(Call<GlobalLatest[]> call, Response<GlobalLatest[]> response) {
                GlobalLatest[] globalLatest = response.body();
                List<DataEntry> data = new ArrayList<>();
                data.add(new ValueDataEntry("confirmed", globalLatest[0].getConfirmed()));
                data.add(new ValueDataEntry("recovered", globalLatest[0].getRecovered()));
                data.add(new ValueDataEntry("deaths", globalLatest[0].getDeaths()));
                data.add(new ValueDataEntry("critical", globalLatest[0].getCritical()));
                pie.data(data);
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