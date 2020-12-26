package com.user.ecomapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.user.ecomapp.Interface.ItemClickListener;
import com.user.ecomapp.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ItemClickListener listener;
    public TextView cartName,cartPrice,cartQuantity,cartUsage,cartDuration,cartWeight;
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        cartName=itemView.findViewById(R.id.cart_name);
        cartPrice=itemView.findViewById(R.id.cart_price);
        cartQuantity=itemView.findViewById(R.id.cart_quantity);
        cartUsage=itemView.findViewById(R.id.cart_usage);
        cartDuration=itemView.findViewById(R.id.cart_duration);
        cartWeight=itemView.findViewById(R.id.weight);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition(),false);

    }
    public void setItemClickListener(ItemClickListener l){
        this.listener=l;

    }
}
