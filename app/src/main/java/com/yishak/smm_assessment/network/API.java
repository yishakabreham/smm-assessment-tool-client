package com.yishak.smm_assessment.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API
{
    public static APIInterface detail()
    {
        Retrofit retrofit = null;
//        TokenInterceptor interceptor = new TokenInterceptor(token);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://192.168.0.100:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface api = retrofit.create(APIInterface.class);
        return api;
    }

    public static APIInterface getAllProjects()
    {
        Retrofit retrofit = null;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://192.168.0.100:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface api = retrofit.create(APIInterface.class);
        return api;
    }

    public static APIInterface postTransaction()
    {
        Retrofit retrofit = null;
        //TokenInterceptor interceptor = new TokenInterceptor(token);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://192.168.0.100:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface api = retrofit.create(APIInterface.class);
        return api;
    }

    public static APIInterface getProject()
    {
        Retrofit retrofit = null;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://192.168.0.100:3000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface api = retrofit.create(APIInterface.class);
        return api;
    }
}
