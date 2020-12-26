package com.user.ecomapp.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.ecomapp.Prevalent.Prevalent;
import com.user.ecomapp.R;
import com.user.ecomapp.ViewHolder.CartViewHolder;
import com.user.ecomapp.models.Cart;

public class CartActivity extends AppCompatActivity {
    private TextView totalAmount,txtMsg;
    private Button confirmProdButton;
    private RecyclerView recyclerView;
    private String State="Normal";
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;
    private int overAllTotalPrice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView=findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        totalAmount=findViewById(R.id.total_price);
        confirmProdButton=findViewById(R.id.confirm_product_button);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        txtMsg=findViewById(R.id.txtMessage);



        cartListRef=FirebaseDatabase.getInstance().getReference().child("Cart List");
        confirmProdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(CartActivity.this,ConfirmFinalOrderActivity.class);
                i.putExtra("totalPrice",String.valueOf(overAllTotalPrice));
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckOrderState();
        totalAmount.setText(String.valueOf(overAllTotalPrice));

        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options=new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child("User View").
                child(Prevalent.currentOnlieUser.getPhone()).child("products"),Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Cart cart) {
                cartViewHolder.cartName.setText(cart.getName());
                cartViewHolder.cartPrice.setText(cart.getPrice()+" \u20B9");
                cartViewHolder.cartQuantity.setText("Quantity "+cart.getQuantity()+" kgs");
                cartViewHolder.cartDuration.setText("Duration "+cart.getDuration()+" days");
                cartViewHolder.cartUsage.setText(cart.getUsage());
                cartViewHolder.cartWeight.setText("Weight "+cart.getWeight());

                int oneTypeProductPrice=(Integer.valueOf(cart.getPrice())*Integer.valueOf(cart.getQuantity()));
                overAllTotalPrice=oneTypeProductPrice+overAllTotalPrice;
                cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[]=new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"
                                };
                        AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options:");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    Intent i=new Intent(CartActivity.this,ProductDetailsActivity.class);
                                    i.putExtra("pid",cart.getPid());
                                    startActivity(i);
                                }
                                if(which==1){
                                    cartListRef.child("User View").child(Prevalent.currentOnlieUser.getPhone()).child("products").
                                            child(cart.getPid())
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            cartListRef.child("Admin View").child(Prevalent.currentOnlieUser.getPhone()).child("products").child(cart.getPid())
                                                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(CartActivity.this,"Item removes successfully..",Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    });

                                }
                            }
                        });
                        builder.show();
                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                CartViewHolder holder=new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    private void CheckOrderState(){
        DatabaseReference orderRef=FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlieUser.getPhone());
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String shipingState=dataSnapshot.child("State").getValue().toString();
                String userName=dataSnapshot.child("name").getValue().toString();
                if(shipingState.equals("shipped")){
                    totalAmount.setText("Dear "+userName+"order is shipped successfully");
                    recyclerView.setVisibility(View.GONE);
                    txtMsg.setVisibility(View.VISIBLE);
                    confirmProdButton.setVisibility(View.GONE);
                    Toast.makeText(CartActivity.this,"You can purchase more once your order is shipped",Toast.LENGTH_SHORT).show();


                }else if(shipingState.equals("Not Shipped")){
                    recyclerView.setVisibility(View.GONE);
                    txtMsg.setVisibility(View.VISIBLE);
                    confirmProdButton.setVisibility(View.GONE);
                    totalAmount.setText("Shipping State = Not Shipped");
                    Toast.makeText(CartActivity.this,"You can purchase more once your order is shipped",Toast.LENGTH_SHORT).show();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}