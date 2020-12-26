package com.user.ecomapp.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.user.ecomapp.Interface.ItemClickListener;
import com.user.ecomapp.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ItemClickListener listener;
    public TextView orderName,orderPrice,orderPhone,orderAddress,orderDate;
    public Button button;
    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        orderName=itemView.findViewById(R.id.order_name);
        orderPrice=itemView.findViewById(R.id.order_price);
        orderPhone=itemView.findViewById(R.id.order_phone);
        orderDate=itemView.findViewById(R.id.order_date);
        orderName=itemView.findViewById(R.id.order_name);
        orderAddress=itemView.findViewById(R.id.order_address);

        button=itemView.findViewById(R.id.showOrderDetails);
    }
    public void setItemClickListener(ItemClickListener l){
        this.listener=l;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition(),false);

    }
}
