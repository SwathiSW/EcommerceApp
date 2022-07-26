package com.ecom.ecommerceapp.Classes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ecom.ecommerceapp.ApiInterfaces.ProductInterface;
import com.ecom.ecommerceapp.Models.Products;
import com.ecom.ecommerceapp.R;
import com.ecom.ecommerceapp.Adapters.hot_sale_adapter;
import com.ecom.ecommerceapp.Adapters.recent_view_adapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    RecyclerView hrec, rrec;
    hot_sale_adapter hsa;
    recent_view_adapter rva;
    List<Products> hl;
    List<Products> rl;
    List<Products> prods;
    ImageView jewel, electronic, mclothing, wclothing,  cartbtn;
    EditText src;
    CardView ebtn, jbtn, mbtn, wbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        getSupportActionBar().hide();

        hrec = findViewById(R.id.hsarec);
        rrec = findViewById(R.id.rvarec);
        jewel = findViewById(R.id.jewel);
        electronic = findViewById(R.id.electronic);
        mclothing = findViewById(R.id.mclothing);
        wclothing = findViewById(R.id.wclothing);
        src = findViewById(R.id.search);
        cartbtn = findViewById(R.id.cartbtn);
        ebtn = findViewById(R.id.ebtn);
        wbtn = findViewById(R.id.wbtn);
        mbtn = findViewById(R.id.mbtn);
        jbtn = findViewById(R.id.jbtn);

        hrec.setHasFixedSize(true);
        rrec.setHasFixedSize(true);
        hrec.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
        rrec.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
        hl = new ArrayList<>();
        rl = new ArrayList<>();

        loadData();

        src.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });

        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CartActivity.class));

            }
        });

        jbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ProductActivity.class).putExtra("category", "jewelery"));
            }
        });

        ebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ProductActivity.class).putExtra("category", "electronics"));
            }
        });

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ProductActivity.class).putExtra("category", "men's clothing"));
            }
        });

        wbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ProductActivity.class).putExtra("category", "women's clothing"));
            }
        });

    }
    private void loadData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductInterface inter = retrofit.create(ProductInterface.class);

        Call<List<Products>> products = inter.getProducts();
        products.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful() && response.body() != null){
                    prods = response.body();
                    for (int i = 0; i < prods.size(); i++) {

                        if (prods.get(i).getCategory().startsWith("ele")){
                            Glide.with(HomeActivity.this).load(prods.get(i).getImage()).into(electronic);
                        }
                        else if (prods.get(i).getCategory().startsWith("jewel")){
                            Glide.with(HomeActivity.this).load(prods.get(i).getImage()).into(jewel);
                        }
                        else if (prods.get(i).getCategory().startsWith("men")){
                            Glide.with(HomeActivity.this).load(prods.get(i).getImage()).into(mclothing);
                        }
                        else if (prods.get(i).getCategory().startsWith("women")){
                            Glide.with(HomeActivity.this).load(prods.get(i).getImage()).into(wclothing);
                        }

                        if (prods.get(i).getId() % 2 == 0){
                            hl.add(prods.get(i));
                        }
                        else{
                            rl.add(prods.get(i));
                        }

                    }
//                    sv.setVisibility(View.VISIBLE);
                }
                hsa = new hot_sale_adapter(HomeActivity.this, hl);
                hrec.setAdapter(hsa);
                rva = new recent_view_adapter(HomeActivity.this, rl);
                rrec.setAdapter(rva);
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}