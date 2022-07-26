package com.ecom.ecommerceapp.Classes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ecom.ecommerceapp.ApiInterfaces.CatInterface;
import com.ecom.ecommerceapp.Models.Products;
import com.ecom.ecommerceapp.R;
import com.ecom.ecommerceapp.Adapters.category_adapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductActivity extends AppCompatActivity {
    List<Products> prods;
    List<Products> hl;
    String cat;
    category_adapter ca;
    EditText search;
    RecyclerView rec;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().hide();

        cat = getIntent().getStringExtra("category");

        search = findViewById(R.id.search2);
        back = findViewById(R.id.cat_back);
        rec = findViewById(R.id.catrec);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        hl = new ArrayList<>();
        ca = new category_adapter(this, hl);
        rec.setAdapter(ca);

        search.setText(cat);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductActivity.this, SearchActivity.class));
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductActivity.this, HomeActivity.class));
            }
        });

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
                Toast.makeText(ProductActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}