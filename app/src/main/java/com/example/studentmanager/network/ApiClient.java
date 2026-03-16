package com.example.studentmanager.network;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
   private static final String BASE_URL = "http://54.251.167.244:8000/api/";
//    private  static  final String BASE_URL = "http://10.0.2.2:8000/api/";  // local api
    private static Retrofit instance;

    public static Retrofit getInstance(Context context) {
        if (instance == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        SharedPreferences prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
                        String token = prefs.getString("access_token", "");

                        Request request = chain.request().newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .build();
                        return chain.proceed(request);
                    })
                    .build();

            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}