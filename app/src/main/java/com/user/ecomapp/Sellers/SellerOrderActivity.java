package com.user.ecomapp.Sellers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.ecomapp.R;
import com.user.ecomapp.ViewHolder.CartViewHolder;
import com.user.ecomapp.models.Cart;
import com.user.ecomapp.models.Products;

import java.util.ArrayList;
import java.util.List;

public class SellerOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference orderRef;
    private List<Cart> list=new ArrayList<>();
    private int totalAmount=0,amount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_order);

        recyclerView=findViewById(R.id.seller_orders_list);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        orderRef= FirebaseDatabase.getInstance().getReference().child("Cart List").child("Admin View");
        orderRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for(DataSnapshot child:snapshot.getChildren()){
                    Log.d("children",""+child.getValue());
                    Products products=child.getValue(Products.class);
                    if(products.getSid().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        list.add(child.getValue(Cart.class));
                    }
                }
                updateAdapter(list);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateAdapter(List<Cart> list) {
        RecyclerView.Adapter<CartViewHolder> adapter=new RecyclerView.Adapter<CartViewHolder>() {
            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                CartViewHolder holder=new CartViewHolder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
                holder.cartPrice.setText("price "+list.get(position).getPrice()+" \u20B9");
                holder.cartQuantity.setText("quantity "+list.get(position).getQuantity());
                holder.cartUsage.setText("usage "+list.get(position).getUsage());
                holder.cartName.setText("name "+list.get(position).getName());
                holder.cartDuration.setText("Duration "+list.get(position).getDuration()+" days");
                int j=Integer.valueOf(list.get(position).getQuantity())*Integer.valueOf(list.get(position).getPrice());
                amount=amount+j;
                final DatabaseReference totalAmountRef=FirebaseDatabase.getInstance().getReference().child("Amount");
                updatePrice(amount);

            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private void updatePrice(int amount) {
        totalAmount=amount+totalAmount;
        Toast.makeText(this,String.valueOf(totalAmount),Toast.LENGTH_SHORT).show();
        amount=0;

        final DatabaseReference totalAmountRef=FirebaseDatabase.getInstance().getReference().child("Amount");
        totalAmountRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                child("sid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
        totalAmountRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Amount").setValue(String.valueOf(totalAmount));
    }

}