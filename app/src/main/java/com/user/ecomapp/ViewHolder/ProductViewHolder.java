package com.user.ecomapp.ViewHolder;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.user.ecomapp.Interface.ItemClickListener;
import com.user.ecomapp.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ItemClickListener listener;
    public TextView txtprod_name,txtprod_desc,txtprod_price,txtprod_weight,txtprod_duration,txtprod_usage,txtProdSellerID,txtSellerName;
    public ImageView imageView;
    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.product_image);
        txtprod_name=itemView.findViewById(R.id.user_product_name);
        txtprod_desc=itemView.findViewById(R.id.user_product_description);
        txtprod_price=itemView.findViewById(R.id.user_product_price);
        txtprod_weight=itemView.findViewById(R.id.user_product_weight);
        txtprod_duration=itemView.findViewById(R.id.user_product_duration);
        txtprod_usage=itemView.findViewById(R.id.user_product_usage);
        txtProdSellerID=itemView.findViewById(R.id.user_product_seller_id);

    }


    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition(),false);

    }
   public void setItemClickListener(ItemClickListener l){
        this.listener=l;

   }
}
