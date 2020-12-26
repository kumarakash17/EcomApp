package com.user.ecomapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.user.ecomapp.Interface.ItemClickListener;
import com.user.ecomapp.R;

public class SellerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ItemClickListener listener;
    public TextView txtSellerTextView;

    public SellerViewHolder(@NonNull View itemView) {
        super(itemView);
        txtSellerTextView=itemView.findViewById(R.id.admin_seller_textview);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition(),false);

    }
    public void setItemClickListener(ItemClickListener l){
        this.listener=l;

    }
}
