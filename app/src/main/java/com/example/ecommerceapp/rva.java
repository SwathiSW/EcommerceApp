package com.example.ecommerceapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class rva extends RecyclerView.Adapter<rva.MyHolder>{

    Context context;
    List<Products> rl;

    public rva(Context context, List<Products> rl) {
        this.context = context;
        this.rl = rl;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rva_layout, parent, false);
        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.title.setText(rl.get(position).getTitle());
        holder.desc.setText(rl.get(position).getDescription());
        holder.price.setText("$"+rl.get(position).getPrice());
        Glide.with(context).load(rl.get(position).getImage()).into(holder.img);

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return rl.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {

        CardView add;
        TextView title, desc, price;
        ImageView img;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textView32);
            desc = itemView.findViewById(R.id.textView42);
            price = itemView.findViewById(R.id.textView52);
            img = itemView.findViewById(R.id.imageView32);
            add = itemView.findViewById(R.id.addtocart);

        }
    }
}
