package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity4 extends AppCompatActivity {
    String free_shipping, url;
    ImageView img;
    CardView cd;
    TextView title, des;
    String title_txt, des_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        getSupportActionBar().hide();

        free_shipping = getIntent().getStringExtra("free_shipping");
        url = getIntent().getStringExtra("url");
        title_txt = getIntent().getStringExtra("title");
        des_txt = getIntent().getStringExtra("des");

        img = findViewById(R.id.dimg);
        title = findViewById(R.id.dtitle);
        des = findViewById(R.id.ddes);

        Glide.with(this).load(url).into(img);
        title.setText(title_txt);
        des.setText(des_txt);

    }
}