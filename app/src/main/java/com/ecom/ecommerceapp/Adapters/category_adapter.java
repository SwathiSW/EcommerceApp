package com.ecom.ecommerceapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ecom.ecommerceapp.Models.CartModel;
import com.ecom.ecommerceapp.Classes.DetailsActivity;
import com.ecom.ecommerceapp.Models.Products;
import com.ecom.ecommerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class category_adapter extends RecyclerView.Adapter<category_adapter.MyHolder>{

    final Context context;
    final List<Products> cl;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public category_adapter(Context context, List<Products> cl) {
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
                    context.startActivity(new Intent(context, DetailsActivity.class).putExtra("free_shipping", "true")
                            .putExtra("url", cl.get(position).getImage())
                            .putExtra("title", cl.get(position).getTitle())
                            .putExtra("des", cl.get(position).getDescription())
                            .putExtra("price", cl.get(position).getPrice()+"")
                            .putExtra("rating",cl.get(position).getRating().get("rate")+"")
                            .putExtra("count", cl.get(position).getRating().get("count")+"")
                            .putExtra("id", cl.get(position).getId()+"")

                    );
                else
                    context.startActivity(new Intent(context, DetailsActivity.class).putExtra("free_shipping", "false")
                            .putExtra("url", cl.get(position).getImage())
                            .putExtra("title", cl.get(position).getTitle())
                            .putExtra("des", cl.get(position).getDescription())
                            .putExtra("price", cl.get(position).getPrice()+"")
                            .putExtra("rating",cl.get(position).getRating().get("rate")+"")
                            .putExtra("count", cl.get(position).getRating().get("count")+"")
                            .putExtra("id", cl.get(position).getId()+"")

                    );
            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cl.get(position).getId() % 2 == 0)
                    context.startActivity(new Intent(context, DetailsActivity.class).putExtra("free_shipping", "true")
                            .putExtra("url", cl.get(position).getImage())
                            .putExtra("title", cl.get(position).getTitle())
                            .putExtra("des", cl.get(position).getDescription())
                            .putExtra("price", cl.get(position).getPrice()+"")
                            .putExtra("rating",cl.get(position).getRating().get("rate")+"")
                            .putExtra("count", cl.get(position).getRating().get("count")+"")
                            .putExtra("id", cl.get(position).getId()+"")

                    );
                else
                    context.startActivity(new Intent(context, DetailsActivity.class).putExtra("free_shipping", "false")
                            .putExtra("url", cl.get(position).getImage())
                            .putExtra("title", cl.get(position).getTitle())
                            .putExtra("des", cl.get(position).getDescription())
                            .putExtra("price", cl.get(position).getPrice()+"")
                            .putExtra("rating",cl.get(position).getRating().get("rate")+"")
                            .putExtra("count", cl.get(position).getRating().get("count")+"")
                            .putExtra("id", cl.get(position).getId()+"")

                    );
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Added to cart !", Toast.LENGTH_SHORT).show();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Cart").child(auth.getUid()).child(cl.get(position).getId()+"");
                CartModel cm = new CartModel(cl.get(position).getId(), cl.get(position).getTitle(), cl.get(position).getPrice(),
                        cl.get(position).getDescription(), cl.get(position).getCategory(), cl.get(position).getImage(),
                        null, 1);
            }
        });

    }


    @Override
    public int getItemCount() {
        return cl.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{

            TextView title, desc, price;
            ImageView img, add;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.ctextView3);
            desc = itemView.findViewById(R.id.ctextView4);
            price = itemView.findViewById(R.id.ctextView5);
            img = itemView.findViewById(R.id.cimageView3);
            add = itemView.findViewById(R.id.imageView);

        }
    }
}
