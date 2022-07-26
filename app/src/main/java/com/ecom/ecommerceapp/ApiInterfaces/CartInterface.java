package com.ecom.ecommerceapp.ApiInterfaces;

import com.ecom.ecommerceapp.Models.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CartInterface {

    @GET("products/{id}")
    Call<List<Products>> getProducts(@Path("id") String id);

}
