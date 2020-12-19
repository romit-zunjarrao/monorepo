package network;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.ConfigUtils;

public class RetrofitClientInstance {

    public static Retrofit retrofit;
    private static final String TAG = "RetrofitClientInstance";

    public static Retrofit getRetrofitInstance(Context context) {
        if (retrofit == null) {
            try {
                retrofit = new Retrofit.Builder()
                        .baseUrl(ConfigUtils.getProperty("BASE_URL", context))
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(getOkHttpClient(context))
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retrofit;
    }

    public static OkHttpClient getOkHttpClient(Context context) {
        return new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.d(TAG, "intercept: key " + ConfigUtils.getProperty("KEY", context) + "\n host "+  ConfigUtils.getProperty("HOST", context));
                Request newRequest = chain.request().newBuilder()
                        .addHeader("x-rapidapi-key", ConfigUtils.getProperty("KEY", context))
                        .addHeader("x-rapidapi-host", ConfigUtils.getProperty("HOST", context))
                        .build();
                Log.d(TAG, "intercept: "+ newRequest.toString());
                Log.d(TAG, "intercept: "+ newRequest.headers().toString());
                return chain.proceed(newRequest);
            }
        }).build();
    }
}
