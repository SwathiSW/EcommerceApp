package com.ecom.ecommerceapp.ApiInterfaces;

import com.ecom.ecommerceapp.Models.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CatInterface {

    @GET("products/category/{category}")
    Call<List<Products>> getProducts(@Path("category") String category);

}
