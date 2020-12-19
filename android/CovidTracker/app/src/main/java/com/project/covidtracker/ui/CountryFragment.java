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

import models.CountryLatest;
import network.CovidDataApi;
import network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CountryFragment extends Fragment {

    private Pie pie;
    private static final String TAG = "CountryFragment";

    public CountryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pie = AnyChart.pie();
        getCurrentLatestData();
        AnyChartView countryPieView = (AnyChartView) view.findViewById(R.id.country_pie_chart);
        countryPieView.setChart(pie);
    }


    private void getCurrentLatestData() {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance(getActivity().getApplicationContext());
        CovidDataApi covidDataApi = retrofit.create(CovidDataApi.class);
        Call<CountryLatest[]> call = covidDataApi.getLatestCountryData("Italy");
        call.enqueue(new Callback<CountryLatest[]>() {
            @Override
            public void onResponse(Call<CountryLatest[]> call, Response<CountryLatest[]> response) {
                CountryLatest[] countryLatest = response.body();
                List<DataEntry> data = new ArrayList<>();

                data.add(new ValueDataEntry("confirmed", countryLatest[0].getConfirmed()));
                data.add(new ValueDataEntry("recovered", countryLatest[0].getRecovered()));
                data.add(new ValueDataEntry("deaths", countryLatest[0].getDeaths()));
                data.add(new ValueDataEntry("critical", countryLatest[0].getCritical()));
                pie.data(data);
                Log.d(TAG, "onResponse: CountryLatest response" + response.body().toString());
                Log.d(TAG, "onResponse: GlobalLatest response" + countryLatest[0].toString());

            }

            @Override
            public void onFailure(Call<CountryLatest[]> call, Throwable t) {
                Log.d(TAG, "Failed getting country data!!!");
                Log.d(TAG, "" + t.getMessage());
                t.printStackTrace();
            }
        });
    }
}