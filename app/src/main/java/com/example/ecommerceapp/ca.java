package com.example.ecommerceapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ca extends RecyclerView.Adapter<ca.MyHolder>{

    final Context context;
    final List<Products> cl;

    public ca(Context context, List<Products> cl) {
        this.context = context;
        this.cl = cl;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cat_layout, parent, false);
        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //category adapter

        holder.title.setText(cl.get(position).getTitle());
        holder.desc.setText(cl.get(position).getDescription());
        holder.price.setText("$"+cl.get(position).getPrice());
        Glide.with(context).load(cl.get(position).getImage()).into(holder.img);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cl.get(position).getId() % 2 == 0)
                    context.startActivity(new Intent(context, MainActivity4.class).putExtra("free_shipping", "true"));
                else
                    context.startActivity(new Intent(context, MainActivity4.class).putExtra("free_shipping", "false"));
            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cl.get(position).getId() % 2 == 0)
                    context.startActivity(new Intent(context, MainActivity4.class).putExtra("free_shipping", "true"));
                else
                    context.startActivity(new Intent(context, MainActivity4.class).putExtra("free_shipping", "false"));
            }
        });

    }


    @Override
    public int getItemCount() {
        return cl.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{

            TextView title, desc, price;
            ImageView img;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.ctextView3);
            desc = itemView.findViewById(R.id.ctextView4);
            price = itemView.findViewById(R.id.ctextView5);
            img = itemView.findViewById(R.id.cimageView3);

        }
    }
}
