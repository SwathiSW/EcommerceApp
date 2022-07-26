package com.ecom.ecommerceapp.Classes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ecom.ecommerceapp.Models.CartModel;
import com.ecom.ecommerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailsActivity extends AppCompatActivity {
    String free_shipping, url, id;
    ImageView img, st1, st2, st3, st4, st5, add, remove, back;
    CardView cd;
    TextView title, des, price, dcount, counter;
    String title_txt, des_txt, price_txt, count;
    int rating;
    Button cont;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        getSupportActionBar().hide();

        free_shipping = getIntent().getStringExtra("free_shipping");
        url = getIntent().getStringExtra("url");
        title_txt = getIntent().getStringExtra("title");
        des_txt = getIntent().getStringExtra("des");
        price_txt = "$ "+ getIntent().getStringExtra("price");
        count = getIntent().getStringExtra("count");
        id = getIntent().getStringExtra("id");
        rating = (int) Float.parseFloat(getIntent().getStringExtra("rating"));

        img = findViewById(R.id.dimg);
        back = findViewById(R.id.dback);
        cd = findViewById(R.id.cd3);
        title = findViewById(R.id.dtitle);
        des = findViewById(R.id.ddes);
        dcount = findViewById(R.id.dcount);
        price = findViewById(R.id.dprice);
        st1 = findViewById(R.id.star1);
        st2 = findViewById(R.id.star2);
        st3 = findViewById(R.id.star3);
        st4 = findViewById(R.id.star4);
        st5 = findViewById(R.id.star5);
        cont = findViewById(R.id.cont);
        add = findViewById(R.id.add_item);
        remove = findViewById(R.id.remove_item);
        counter = findViewById(R.id.counter);

        if (free_shipping.equals("true"))
            cd.setVisibility(View.VISIBLE);
        else
            cd.setVisibility(View.GONE);

        setData();

        setRating(rating, count);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsActivity.this, HomeActivity.class));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(counter.getText().toString());
                counter.setText(++count + "");

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(counter.getText().toString());

                if (count!=0){
                    cont.setVisibility(View.VISIBLE);

                    counter.setText(--count + "");
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Cart").child(auth.getUid());
                    ref.child(id).child("noItems").setValue(Integer.parseInt(counter.getText().toString()));
               }else{
                    cont.setVisibility(View.GONE);
                }

            }
        });

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Cart").child(auth.getUid());
                ref.child(id).setValue(
                        new CartModel(Integer.parseInt(id), title_txt, Float.parseFloat(getIntent().getStringExtra("price")), des_txt, "category", url, null, Integer.parseInt(counter.getText().toString()))
                );
                startActivity(new Intent(DetailsActivity.this, CartActivity.class));

            }
        });


    }

    private void setData() {
        Glide.with(this).load(url).into(img);
        title.setText(title_txt);
        des.setText(des_txt);
        price.setText(price_txt);
        dcount.setText(count+" ratings");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Cart").child(auth.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()){
                    if ((s.child("id").getValue()+"").equals(id)){
                        String c = s.child("noItems").getValue().toString();
                        counter.setText(c);
                        return;
                    }

                    counter.setText("1");


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setRating(int rating, String count) {
        switch(rating){
            case 1:
                Glide.with(DetailsActivity.this).load(R.drawable.ic_baseline_star_24).into(st1);
                break;
            case 2:
                Glide.with(DetailsActivity.this).load(R.drawable.ic_baseline_star_24).into(st1);
                Glide.with(DetailsActivity.this).load(R.drawable.ic_baseline_star_24).into(st2);
                break;
            case 3:
                Glide.with(DetailsActivity.this).load(R.drawable.ic_baseline_star_24).into(st1);
                Glide.with(DetailsActivity.this).load(R.drawable.ic_baseline_star_24).into(st2);
                Glide.with(DetailsActivity.this).load(R.drawable.ic_baseline_star_24).into(st3);
                break;
            case 4:
                Glide.with(DetailsActivity.this).load(R.drawable.ic_baseline_star_24).into(st1);
                Glide.with(DetailsActivity.this).load(R.drawable.ic_baseline_star_24).into(st2);
                Glide.with(DetailsActivity.this).load(R.drawable.ic_baseline_star_24).into(st3);
                Glide.with(DetailsActivity.this).load(R.drawable.ic_baseline_star_24).into(st4);
                break;
            case 5:
                Glide.with(DetailsActivity.this).load(R.drawable.ic_baseline_star_24).into(st1);
                Glide.with(DetailsActivity.this).load(R.drawable.ic_baseline_star_24).into(st2);
                Glide.with(DetailsActivity.this).load(R.drawable.ic_baseline_star_24).into(st3);
                Glide.with(DetailsActivity.this).load(R.drawable.ic_baseline_star_24).into(st4);
                Glide.with(DetailsActivity.this).load(R.drawable.ic_baseline_star_24).into(st5);
                break;
            default:
                break;

        }
    }
}