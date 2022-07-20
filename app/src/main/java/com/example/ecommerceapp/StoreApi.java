package com.example.ecommerceapp;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface StoreApi {
    String baseUrl = "https://fakestoreapi.com/";

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    Call<ResponseBody> loginUser(@Body RequestBody body);
}
