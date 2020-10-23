package com.ayush26.event_management_admin;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private static RetrofitAPIInterface apiInterface;
    public static RemoteConfigClass remoteConfigClass = RemoteConfigClass.getRemoteConfig();
    public static String BASE_URL="https://event-mgt.herokuapp.com/";

    public static void getBaseURL(){
//        BASE_URL = remoteConfigClass.getBaseURL();
        Log.i("TAG", "getBaseURL: "+ BASE_URL);
    }

    public static RetrofitAPIInterface getClient() {
        if (apiInterface == null) {
//            getBaseURL();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiInterface = retrofit.create(RetrofitAPIInterface.class);
        }
        return apiInterface;
    }
    private static OkHttpClient getHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder();
//                String authToken = App.getInstance().getSessionManager().getX_AUTH_TOKEN();
//                if (!StringUtils.isEmpty(authToken)) {
//                    builder.addHeader("X-AUTH-TOKEN", authToken);
//                }
//                request = builder.build();
                return chain.proceed(request);
            }
        });
        return httpClient.build();
    }
}