package com.rz.movieguide.repository.remote;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitClient {

    private static final String API_KEY = "f7f800e90f5e622e79979e0199d27e46";
    private static final String BASE_URL = "https://api.themoviedb.org/";
    public static MovieApi movieApi;

    private static Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                try {
                    Request originalRequest = chain.request();
                    HttpUrl originalUrl = originalRequest.url();

                    HttpUrl newUrl = originalUrl.newBuilder()
                            .addQueryParameter("api_key", API_KEY)
                            .build();

                    Request newRequest = originalRequest.newBuilder()
                            .url(newUrl)
                            .build();
                    Log.d("MovieApi", newRequest.toString());
                    return chain.proceed(newRequest);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                 return null;

            }
        };
    }


    private static OkHttpClient getOkhttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(getInterceptor())
                .build();
    }


    public static Retrofit getRetrofit() {
        return new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(getOkhttpClient())
                .build();
    }

    public static MovieApi getMovieApi() {
        if(movieApi == null) {
            movieApi = getRetrofit().create(MovieApi.class);
        }
        return movieApi;
    }


}
