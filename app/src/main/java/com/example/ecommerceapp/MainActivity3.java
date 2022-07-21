package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity3 extends AppCompatActivity {
    List<Products> prods;
    List<Products> hl;
    String cat;
    ca ca;
    RecyclerView rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().hide();

        cat = getIntent().getStringExtra("category");

        rec = findViewById(R.id.catrec);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        hl = new ArrayList<>();
        ca = new ca(this, hl);
        rec.setAdapter(ca);
        
        loadData();
        
    }

    private void loadData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CatInterface inter = retrofit.create(CatInterface.class);

        Call<List<Products>> products = inter.getProducts(cat);
        products.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful() && response.body() != null){
                    prods = response.body();
                    for (int i = 0; i < prods.size(); i++) {
                        hl.add(prods.get(i));
                    }
                }
                ca.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(MainActivity3.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}