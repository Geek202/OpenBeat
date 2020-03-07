package me.geek.tom.openbeat.btsapi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static Retrofit getApiClient() {

        OkHttpClient client = new OkHttpClient.Builder().build();


        return new Retrofit.Builder()
                .baseUrl("https://admin.beatthestreet.me/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static Retrofit getPagedataClient() {
        OkHttpClient client = new OkHttpClient.Builder().build();

        return new Retrofit.Builder()
                .baseUrl("https://www.beatthestreet.me/page-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
