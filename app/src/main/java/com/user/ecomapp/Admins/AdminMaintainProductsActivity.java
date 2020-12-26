package com.user.ecomapp.Admins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.user.ecomapp.R;

import java.util.HashMap;

public class AdminMaintainProductsActivity extends AppCompatActivity {
    private Button applyChangesButton,deleteProduct;
    private EditText name,desc,weight,price,usage,duration;
    private ImageView maintainimageView;
    private String productId;
    private DatabaseReference productRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);
        maintainimageView=findViewById(R.id.user_maintain_product_image);
        name=findViewById(R.id.user_maintain_product_name);
        desc=findViewById(R.id.user_maintain_product_description);
        weight=findViewById(R.id.user_maintain_product_weight);
        price=findViewById(R.id.user_maintain_product_price);
        usage=findViewById(R.id.user_maintain_product_usage);
        duration=findViewById(R.id.user_maintain_product_duration);
        applyChangesButton=findViewById(R.id.applyChanges_button);
        deleteProduct=findViewById(R.id.delete_button);

        productId=getIntent().getStringExtra("pid");

        productRef= FirebaseDatabase.getInstance().getReference().child("products").child(productId);
        displaySpecificInformation();

        applyChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChanges();
            }
        });
        deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct(productId);
            }
        });
        

    }

    private void deleteProduct(String productId) {
        productRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AdminMaintainProductsActivity.this,"deleted..",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminMaintainProductsActivity.this,AdminHomeActivity.class));
            }
        });
    }

    private void applyChanges() {
        String kname=name.getText().toString();
        String kdesc=desc.getText().toString();
        String kprice=price.getText().toString();
        String kweight=weight.getText().toString();
        String kusage=usage.getText().toString();
        String kduration=duration.getText().toString();

        if(TextUtils.isEmpty(kname)){
            Toast.makeText(this,"Please write down product name..",Toast.LENGTH_SHORT).show();
        }
         else if(TextUtils.isEmpty(kdesc)){
            Toast.makeText(this,"Please write down product description..",Toast.LENGTH_SHORT).show();
        }
       else if(TextUtils.isEmpty(kprice)){
            Toast.makeText(this,"Please write down product price..",Toast.LENGTH_SHORT).show();
        }
          else if(TextUtils.isEmpty(kweight)){
            Toast.makeText(this,"Please write down product weight..",Toast.LENGTH_SHORT).show();
        }
         else if(TextUtils.isEmpty(kusage)){
            Toast.makeText(this,"Please write down product is used or new..",Toast.LENGTH_SHORT).show();
        }
         else if(TextUtils.isEmpty(kduration)){
            Toast.makeText(this,"Please write down product estimated time to deliver..",Toast.LENGTH_SHORT).show();
        }
         else {
            HashMap<String,Object> productMap=new HashMap<>();
            productMap.put("pid",productId);
            productMap.put("name",kname);
            productMap.put("descrption",kdesc);
            productMap.put("price",kprice);
            productMap.put("weight",kweight);
            productMap.put("usage",kusage);
            productMap.put("duration",kduration);

            productRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(AdminMaintainProductsActivity.this,"Changes applied..",Toast.LENGTH_SHORT).show();
                    finish();

                }
            });

        }
    }

    private void displaySpecificInformation() {
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String sname=dataSnapshot.child("name").getValue().toString();
                    String sdesc=dataSnapshot.child("descrption").getValue().toString();
                    String sweight=dataSnapshot.child("weight").getValue().toString();
                    String susage=dataSnapshot.child("usage").getValue().toString();
                    String sprice=dataSnapshot.child("price").getValue().toString();
                    String sduration=dataSnapshot.child("duration").getValue().toString();
                    String simage=dataSnapshot.child("image").getValue().toString();

                    name.setText(sname);
                    desc.setText(sdesc);
                    weight.setText(sweight);
                    price.setText(sprice);
                    duration.setText(sduration);
                    usage.setText(susage );
                    Picasso.get().load(simage).into(maintainimageView);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}