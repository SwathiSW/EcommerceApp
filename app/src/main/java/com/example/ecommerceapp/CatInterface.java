package com.example.ecommerceapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CatInterface {

    @GET("products/category/{category}")
    Call<List<Products>> getProducts(@Path("category") String category);

}
