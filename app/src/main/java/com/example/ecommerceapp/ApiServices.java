package com.example.ecommerceapp;

import android.app.Activity;
import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServices {
    public static class ApiService {

        public static StoreApi getInstance() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(StoreApi.baseUrl)
                    //.addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit.create(StoreApi.class);
        }
    }
}
