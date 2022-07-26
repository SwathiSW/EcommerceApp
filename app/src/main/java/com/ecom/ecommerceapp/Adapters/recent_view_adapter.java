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
import androidx.cardview.widget.CardView;
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

public class recent_view_adapter extends RecyclerView.Adapter<recent_view_adapter.MyHolder>{

    Context context;
    List<Products> rl;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public recent_view_adapter(Context context, List<Products> rl) {
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
                Toast.makeText(context, "added !", Toast.LENGTH_SHORT).show();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Cart").child(auth.getUid());

                CartModel cm = new CartModel(rl.get(position).getId(), rl.get(position).getTitle(), rl.get(position).getPrice(),
                        rl.get(position).getDescription(), rl.get(position).getCategory(), rl.get(position).getImage(),
                        null, 1);
                ref.child(rl.get(position).getId()+"").setValue(cm);

            }
        });

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    context.startActivity(new Intent(context, DetailsActivity.class).putExtra("free_shipping", "false")
                            .putExtra("url", rl.get(position).getImage())
                            .putExtra("title", rl.get(position).getTitle())
                            .putExtra("des", rl.get(position).getDescription())
                            .putExtra("price", rl.get(position).getPrice()+"")
                            .putExtra("rating",rl.get(position).getRating().get("rate")+"")
                            .putExtra("id", rl.get(position).getId()+"")

                            .putExtra("count", rl.get(position).getRating().get("count")+""));

            }
        });
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    context.startActivity(new Intent(context, DetailsActivity.class).putExtra("free_shipping", "false")
                            .putExtra("url", rl.get(position).getImage())
                            .putExtra("title", rl.get(position).getTitle())
                            .putExtra("des", rl.get(position).getDescription())
                            .putExtra("price", rl.get(position).getPrice()+"")
                            .putExtra("rating",rl.get(position).getRating().get("rate")+"")
                            .putExtra("id", rl.get(position).getId()+"")
                            .putExtra("count", rl.get(position).getRating().get("count")+""));


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
