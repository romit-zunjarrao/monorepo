package network;

import models.CountryDaily;
import models.CountryLatest;
import models.GlobalLatest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CovidDataApi {

    @GET("/totals")
    Call<GlobalLatest[]> getLatestGlobalData();

    @GET("/country")
    Call<CountryLatest[]> getLatestCountryData(@Query("name") String countryName);

    @GET("/report/country/name")
    Call<CountryDaily> getDailyCountryData(@Query("date") String date, @Query("name") String countryName);
}
