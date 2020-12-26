package com.user.ecomapp.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.user.ecomapp.Prevalent.Prevalent;
import com.user.ecomapp.R;
import com.user.ecomapp.models.Products;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {
    private TextView name,weight,usage,price,duration,description,sid;
    private ImageView itemImage;
    private Button addToCart;
    private EditText quantity;
    private String productId="",State="Normal";
    private DatabaseReference prodRef,cartListRef;
    private String saveCurrentDate,savecurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        name=findViewById(R.id.item_product_name);

        weight=findViewById(R.id.item_product_weight);
        usage=findViewById(R.id.item_product_usage);
        price=findViewById(R.id.item_product_price);
        duration=findViewById(R.id.item_product_duration);
        itemImage=findViewById(R.id.item_image);
        addToCart=findViewById(R.id.addtocart);
        quantity=findViewById(R.id.item_product_quantity);
        description=findViewById(R.id.item_product_description);
        sid=findViewById(R.id.item_seller_id);

        productId=getIntent().getStringExtra("pid");
        cartListRef=FirebaseDatabase.getInstance().getReference().child("Cart List");

        getProductDetails(productId);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    addtoCart();


            }
        });



    }

    private void addtoCart() {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd , yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        savecurrentTime=currentTime.format(calendar.getTime());

        final HashMap<String,Object> cartMap=new HashMap<>();
        cartMap.put("pid",productId);
        cartMap.put("name",name.getText().toString());
        cartMap.put("descritption",description.getText().toString());
        cartMap.put("price",price.getText().toString());
        cartMap.put("weight",weight.getText().toString());
        cartMap.put("duration",duration.getText().toString());
        cartMap.put("usage",usage.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",savecurrentTime);
        cartMap.put("quantity",quantity.getText().toString());
        cartMap.put("sid",sid.getText().toString());

        cartListRef.child("User View").child(Prevalent.currentOnlieUser.getPhone())
                .child("products").
                child(productId)
                .updateChildren(cartMap).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ProductDetailsActivity.this,"Added to cart successfully",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ProductDetailsActivity.this,NewHomeActivity.class);
                intent.putExtra("person","Users");
                startActivity(intent);

            }
        });

    }

    private void getProductDetails(String productId) {
        prodRef= FirebaseDatabase.getInstance().getReference().child("products");
        prodRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Products products=snapshot.getValue(Products.class);
                    name.setText(products.getName());
                    description.setText(products.getDescription());
                    duration.setText(products.getDuration());
                    price.setText(products.getPrice());
                    weight.setText(products.getWeight());
                    usage.setText(products.getUsage());
                    sid.setText((products.getSid()));

                    Picasso.get().load(products.getImage()).into(itemImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
       CheckOrderState();


    }

    private void CheckOrderState(){
        DatabaseReference orderRef=FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlieUser.getPhone());
        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String shipingState=dataSnapshot.child("State").getValue().toString();
                String userName=dataSnapshot.child("name").getValue().toString();
                if(shipingState.equals("shipped")){
                    State="Order Shipped";
                }else if(shipingState.equals("Not Shipped")){
                    State="Order Placed";
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}