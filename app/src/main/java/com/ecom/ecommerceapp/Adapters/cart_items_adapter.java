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
import com.ecom.ecommerceapp.Models.CartModel;
import com.ecom.ecommerceapp.Classes.DetailsActivity;
import com.ecom.ecommerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class cart_items_adapter extends RecyclerView.Adapter<cart_items_adapter.MyHolder>{

    final Context context;
    final List<CartModel> cl;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public cart_items_adapter(Context context, List<CartModel> cl) {
        this.context = context;
        this.cl = cl;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new MyHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.title.setText(cl.get(position).getTitle());
        holder.desc.setText(cl.get(position).getDescription());
        holder.price.setText("$"+cl.get(position).getPrice());
        Glide.with(context).load(cl.get(position).getImage()).into(holder.img);
        holder.count.setText(cl.get(position).getNoItems()+"");

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
                int count = Integer.parseInt(holder.count.getText().toString());
                holder.count.setText(++count + "");
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Cart").child(auth.getUid()).child(cl.get(position).getId()+"");
                ref.child("noItems").setValue(Integer.parseInt(holder.count.getText().toString()));

            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.count.getText().toString());
                holder.count.setText(--count + "");

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Cart").child(auth.getUid()).child(cl.get(position).getId()+"");
                ref.child("noItems").setValue(Integer.parseInt(holder.count.getText().toString()));
            }
        });

    }


    @Override
    public int getItemCount() {
        return cl.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{

            TextView title, desc, price, count;
            ImageView img, remove, add;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.ctitle);
            desc = itemView.findViewById(R.id.cdes);
            price = itemView.findViewById(R.id.cprice);
            img = itemView.findViewById(R.id.cimg);
            remove = itemView.findViewById(R.id.crem);
            add = itemView.findViewById(R.id.cadd);
            count = itemView.findViewById(R.id.ccount);


        }
    }
    public float getAmount(){
        float amount = 0;

        for (int i = 0; i < cl.size(); i++) {
            amount += cl.get(i).getPrice() * cl.get(i).getNoItems();
        }
        return amount;
    }
}
