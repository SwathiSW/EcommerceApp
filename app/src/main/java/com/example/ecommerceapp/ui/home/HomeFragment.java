package com.example.ecommerceapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.MainActivity;
import com.example.ecommerceapp.MainActivity2;
import com.example.ecommerceapp.MainActivity3;
import com.example.ecommerceapp.ProductInterface;
import com.example.ecommerceapp.Products;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.databinding.FragmentHomeBinding;
import com.example.ecommerceapp.hsa;
import com.example.ecommerceapp.rva;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    RecyclerView hrec, rrec;
    hsa hsa;
    rva rva;
    List<Products> hl;
    List<Products> rl;
    List<Products> prods;
    ImageView jewel, electronic, mclothing, wclothing, back;
    EditText src;
    ScrollView sv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        hrec = root.findViewById(R.id.hsarec);
        rrec = root.findViewById(R.id.rvarec);
        jewel = root.findViewById(R.id.jewel);
        electronic = root.findViewById(R.id.electronic);
        mclothing = root.findViewById(R.id.mclothing);
        wclothing = root.findViewById(R.id.wclothing);
        back = root.findViewById(R.id.back);
        src = root.findViewById(R.id.search);
        sv = root.findViewById(R.id.scrollView2);

        hrec.setHasFixedSize(true);
        rrec.setHasFixedSize(true);
        hrec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rrec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        hl = new ArrayList<>();
        rl = new ArrayList<>();

        loadData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        jewel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity3.class).putExtra("category", "jewelery"));
            }
        });

        electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity3.class).putExtra("category", "electronics"));
            }
        });

        mclothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity3.class).putExtra("category", "men's clothing"));
            }
        });

        wclothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity3.class).putExtra("category", "women's clothing"));
            }
        });

        return root;
    }

    private void loadData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                //.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductInterface inter = retrofit.create(ProductInterface.class);
        Log.d("Cool", "onResponse: ");

        Call<List<Products>> products = inter.getProducts();
        products.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful() && response.body() != null){
                    prods = response.body();
                    for (int i = 0; i < prods.size(); i++) {

                        if (prods.get(i).getCategory().startsWith("ele")){
                            Glide.with(getContext()).load(prods.get(i).getImage()).into(electronic);
                        }
                        else if (prods.get(i).getCategory().startsWith("jewel")){
                            Glide.with(getContext()).load(prods.get(i).getImage()).into(jewel);
                        }
                        else if (prods.get(i).getCategory().startsWith("men")){
                            Glide.with(getContext()).load(prods.get(i).getImage()).into(mclothing);
                        }
                        else if (prods.get(i).getCategory().startsWith("women")){
                            Glide.with(getContext()).load(prods.get(i).getImage()).into(wclothing);
                        }

                        if (prods.get(i).getId() % 2 == 0){
                            hl.add(prods.get(i));
                        }
                        else{
                            rl.add(prods.get(i));
                        }

                    }
                    sv.setVisibility(View.VISIBLE);
                }
                hsa = new hsa(getContext(), hl);
                hrec.setAdapter(hsa);
                rva = new rva(getContext(), rl);
                rrec.setAdapter(rva);
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}