package com.ecom.ecommerceapp.Classes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ecom.ecommerceapp.ApiInterfaces.ProductInterface;
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

public class SearchActivity extends AppCompatActivity {

    EditText src;
    ImageView back;
    Context context;
    RecyclerView rec;
    List<Products> sl;
    category_adapter ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        context = SearchActivity.this;

        back = findViewById(R.id.src_back);
        src = findViewById(R.id.search3);
        rec = findViewById(R.id.srec);

        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this));
        sl = new ArrayList<>();
        ca = new category_adapter(this, sl);
        rec.setAdapter(ca);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, HomeActivity.class));
            }
        });

        src.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            private void search(String text) {
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
                        if (response.isSuccessful() && response.body() != null) {
                                List<Products> prods = response.body();

                            sl.clear();
                            for (int i = 0; i < prods.size(); i++) {
                                if (prods.get(i).getTitle().toLowerCase().contains(text.toLowerCase()) ||
                                    prods.get(i).getCategory().toLowerCase().contains(text.toLowerCase()) ||
                                    (prods.get(i).getPrice()+"").toLowerCase().contains(text.toLowerCase())){
                                    sl.add(prods.get(i));
                                }
                            }
                            ca.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Products>> call, Throwable t) {
                        Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}