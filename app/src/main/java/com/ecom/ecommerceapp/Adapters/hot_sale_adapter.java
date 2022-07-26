package com.ecom.ecommerceapp.Adapters;

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
import com.ecom.ecommerceapp.Classes.DetailsActivity;
import com.ecom.ecommerceapp.Models.Products;
import com.ecom.ecommerceapp.R;

import java.util.List;

public class hot_sale_adapter extends RecyclerView.Adapter<hot_sale_adapter.MyHolder>{

    final Context context;
    final List<Products> hl;

    public hot_sale_adapter(Context context, List<Products> hl) {
        this.context = context;
        this.hl = hl;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hsa_layout, parent, false);
        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.title.setText(hl.get(position).getTitle());
        holder.desc.setText(hl.get(position).getDescription());
        holder.price.setText("$"+hl.get(position).getPrice());
        Glide.with(context).load(hl.get(position).getImage()).into(holder.img);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, DetailsActivity.class).putExtra("free_shipping", "true")
                        .putExtra("url", hl.get(position).getImage())
                        .putExtra("title", hl.get(position).getTitle())
                        .putExtra("price", hl.get(position).getPrice()+"")
                        .putExtra("des", hl.get(position).getDescription())
                        .putExtra("rating",hl.get(position).getRating().get("rate")+"")
                        .putExtra("count", hl.get(position).getRating().get("count")+"")
                        .putExtra("id", hl.get(position).getId()+"")

                );
            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    context.startActivity(new Intent(context, DetailsActivity.class).putExtra("free_shipping", "true")
                            .putExtra("url", hl.get(position).getImage())
                            .putExtra("title", hl.get(position).getTitle())
                            .putExtra("des", hl.get(position).getDescription())
                            .putExtra("price", hl.get(position).getPrice()+"")
                            .putExtra("rating",hl.get(position).getRating().get("rate")+"")
                            .putExtra("count", hl.get(position).getRating().get("count")+"")
                            .putExtra("id", hl.get(position).getId()+"")


                    );

            }
        });

    }


    @Override
    public int getItemCount() {
        return hl.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{

            TextView title, desc, price;
            ImageView img;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textView3);
            desc = itemView.findViewById(R.id.textView4);
            price = itemView.findViewById(R.id.textView5);
            img = itemView.findViewById(R.id.imageView3);

        }
    }
}
