package com.ecom.ecommerceapp.Classes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ecom.ecommerceapp.Models.CartModel;
import com.ecom.ecommerceapp.R;
import com.ecom.ecommerceapp.Adapters.cart_items_adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    FirebaseAuth auth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Cart");
    List<CartModel> cl;
    RecyclerView rec;
    TextView total;
    cart_items_adapter cia;
    Button cont;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        getSupportActionBar().hide();

        rec = findViewById(R.id.crec2);
        total = findViewById(R.id.total);
        cont = findViewById(R.id.cont2);
        back = findViewById(R.id.imageView4);

        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this));
        cl = new ArrayList<>();
        cia = new cart_items_adapter(this, cl);
        rec.setAdapter(cia);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, HomeActivity.class));
            }
        });

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CartActivity.this, "Service not available yet !", Toast.LENGTH_SHORT).show();
            }
        });

        loadData();

    }

    private void loadData() {

        ref.child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cl.clear();
                for (DataSnapshot s : snapshot.getChildren()){
                    CartModel cm =  s.getValue(CartModel.class);
                    cl.add(cm);
                }

                cia.notifyDataSetChanged();
                total.setText("$"+cia.getAmount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}