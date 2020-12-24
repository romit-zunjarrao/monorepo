package com.project.covidtracker.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.project.covidtracker.R;

import java.util.ArrayList;
import java.util.List;

import models.CountryDaily;
import models.CountryLatest;
import network.CovidDataApi;
import network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CountryFragment extends Fragment {

    private Pie pie;
    private Pie line;
    private static final String TAG = "CountryFragment";
    AnyChartView countryPieView, countryLineView;

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
        countryPieView = (AnyChartView) view.findViewById(R.id.country_pie_chart);
        countryLineView = (AnyChartView) view.findViewById(R.id.country_line_chart);
        getCountryLatestData();

        new Handler(Looper.getMainLooper()).postDelayed(()-> {
                    getCountryDailyData();
                },2000
            );
    }


    private void getCountryLatestData() {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance(getActivity().getApplicationContext());
        CovidDataApi covidDataApi = retrofit.create(CovidDataApi.class);
        Call<CountryLatest[]> call = covidDataApi.getLatestCountryData("Italy");
        call.enqueue(new Callback<CountryLatest[]>() {
            @Override
            public void onResponse(Call<CountryLatest[]> call, Response<CountryLatest[]> response) {
                CountryLatest[] countryLatest = response.body();
                List<DataEntry> data = new ArrayList<>();
                Log.d(TAG, "onResponse: CountryLatest response" + response.body());
                Log.d(TAG, "onResponse: CountryLatest response" + countryLatest.length);
                data.add(new ValueDataEntry("confirmed", countryLatest[0].getConfirmed()));
                data.add(new ValueDataEntry("recovered", countryLatest[0].getRecovered()));
                data.add(new ValueDataEntry("deaths", countryLatest[0].getDeaths()));
                data.add(new ValueDataEntry("critical", countryLatest[0].getCritical()));
                setCountryPieChart(data);
            }

            @Override
            public void onFailure(Call<CountryLatest[]> call, Throwable t) {
                Log.d(TAG, "Failed getting country data!!!");
                Log.d(TAG, "" + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void getCountryDailyData() {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance(getActivity().getApplicationContext());
        CovidDataApi covidDataApi = retrofit.create(CovidDataApi.class);
        Call<CountryDaily[]> call = covidDataApi.getDailyCountryData("2020-04-01", "Italy");
        call.enqueue(new Callback<CountryDaily[]>() {
            @Override
            public void onResponse(Call<CountryDaily[]> call, Response<CountryDaily[]> response) {
                CountryDaily[] countryDaily = response.body();
                List<DataEntry> data = new ArrayList<>();
                Log.d(TAG, "onResponse: CountryDaily response" + response.body());
                Log.d(TAG, "onResponse: CountryDaily response" + countryDaily.length);
                CountryDaily.Province province = countryDaily[0].getProvinces().get(0);
                data.add(new ValueDataEntry("confirmed", province.getConfirmed()));
                data.add(new ValueDataEntry("recovered", province.getRecovered()));
                data.add(new ValueDataEntry("deaths", province.getDeaths()));
                data.add(new ValueDataEntry("active", province.getActive()));
                setCountryLineChart(data);
            }

            @Override
            public void onFailure(Call<CountryDaily[]> call, Throwable t) {
                Log.d(TAG, "Failed getting country data!!!");
                Log.d(TAG, "" + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void setCountryPieChart(List<DataEntry> data) {
        new Handler(Looper.getMainLooper()).post(()->{
            APIlib.getInstance().setActiveAnyChartView(countryPieView);
            pie = AnyChart.pie();
            pie.data(data);
            countryPieView.setChart(pie);
        });
    }

    public void setCountryLineChart(List<DataEntry> data) {
        new Handler(Looper.getMainLooper()).postDelayed(()-> {
            APIlib.getInstance().setActiveAnyChartView(countryLineView);
            line = AnyChart.pie();
            line.data(data);
            countryLineView.setChart(line);
        },2000);
    }


}