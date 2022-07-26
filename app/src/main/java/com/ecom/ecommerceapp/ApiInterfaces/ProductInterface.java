package com.ecom.ecommerceapp.ApiInterfaces;

import com.ecom.ecommerceapp.Models.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductInterface {

    @GET("products")
    Call<List<Products>> getProducts();
}
