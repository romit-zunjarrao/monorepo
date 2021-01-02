package com.project.covidtracker.ui;

import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
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

    private static final String TAG = "CountryFragment";
    PieChart countryPieView, countryLineView;
    EditText countryEditText;
    Button searchButton;

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
        countryPieView = view.findViewById(R.id.country_pie_chart);
        countryLineView = view.findViewById(R.id.country_line_chart);
        countryEditText = view.findViewById(R.id.country_edit_text);
        searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener((View v) -> {
            if (countryEditText.length() != 0) {
                searchCountry(countryEditText.getText().toString().trim());
            }
        });
        searchCountry("Italy");
    }


    private void getCountryLatestData(String countryName) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance(getActivity().getApplicationContext());
        CovidDataApi covidDataApi = retrofit.create(CovidDataApi.class);
        Call<CountryLatest[]> call = covidDataApi.getLatestCountryData(countryName);
        call.enqueue(new Callback<CountryLatest[]>() {
            @Override
            public void onResponse(Call<CountryLatest[]> call, Response<CountryLatest[]> response) {
                CountryLatest[] countryLatest = response.body();
                countryPieView.invalidate();

                List<PieEntry> pieEntries = new ArrayList<>();
                pieEntries.add(new PieEntry(countryLatest[0].getConfirmed(), "Confirmed"));
                pieEntries.add(new PieEntry(countryLatest[0].getCritical(), "Critical"));
                pieEntries.add(new PieEntry(countryLatest[0].getDeaths(), "Deaths"));
                pieEntries.add(new PieEntry(countryLatest[0].getRecovered(), "Recovered"));
                PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                PieData pieData = new PieData(pieDataSet);
                countryPieView.setData(pieData);
                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                pieDataSet.setSliceSpace(2f);
                pieDataSet.setValueTextColor(Color.WHITE);
                pieDataSet.setValueTextSize(10f);
                Log.d(TAG, "onResponse:  CountryName returned by API" + countryLatest[0].getCountry() + "\n" + countryLatest[0].getConfirmed());
            }

            @Override
            public void onFailure(Call<CountryLatest[]> call, Throwable t) {
                Log.d(TAG, "Failed getting country data!!!");
                Log.d(TAG, "" + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void getCountryDailyData(String countryName) {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance(getActivity().getApplicationContext());
        CovidDataApi covidDataApi = retrofit.create(CovidDataApi.class);
        Call<CountryDaily[]> call = covidDataApi.getDailyCountryData("2020-04-01", countryName);
        call.enqueue(new Callback<CountryDaily[]>() {
            @Override
            public void onResponse(Call<CountryDaily[]> call, Response<CountryDaily[]> response) {
                CountryDaily[] countryDaily = response.body();
                Log.d(TAG, "onResponse: CountryDaily response" + response.body());
                CountryDaily.Province province = countryDaily[0].getProvinces().get(0);
                countryLineView.invalidate();
                List<PieEntry> pieEntries = new ArrayList<>();
                pieEntries.add(new PieEntry(province.getConfirmed(), "Confirmed"));
                pieEntries.add(new PieEntry(province.getRecovered(), "Recovered"));
                pieEntries.add(new PieEntry(province.getDeaths(), "Deaths"));
                pieEntries.add(new PieEntry(province.getActive(), "Active"));
                PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
                PieData pieData = new PieData(pieDataSet);
                countryLineView.setData(pieData);
                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                pieDataSet.setSliceSpace(2f);
                pieDataSet.setValueTextColor(Color.WHITE);
                pieDataSet.setValueTextSize(10f);
                Log.d(TAG, "onResponse:  CountryName returned by API" + countryDaily[0].getCountry() + "\n" + province.getConfirmed());
            }

            @Override
            public void onFailure(Call<CountryDaily[]> call, Throwable t) {
                Log.d(TAG, "Failed getting country data!!!");
                Log.d(TAG, "" + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    public void searchCountry(String countryName) {
        getCountryLatestData(countryName);
        new Handler(Looper.getMainLooper()).post(() -> {
                    getCountryDailyData(countryName);
                }
        );
    }
}