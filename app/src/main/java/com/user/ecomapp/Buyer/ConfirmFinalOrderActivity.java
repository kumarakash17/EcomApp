package com.user.ecomapp.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.ecomapp.Prevalent.Prevalent;
import com.user.ecomapp.R;
import com.user.ecomapp.models.Cart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class  ConfirmFinalOrderActivity extends AppCompatActivity {
    private TextView totalAmount,orderPhone;
    private EditText orderName,orderAddress,orderCity,orderMail;
    private Button confirmOrder;
    private String overAllTotalAmount="";
    private String saveCurrentDate,savecurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount=findViewById(R.id.total_amount);
        orderName=findViewById(R.id.order_name);
        orderAddress=findViewById(R.id.order_address);
        orderCity=findViewById(R.id.order_city);
        orderMail=findViewById(R.id.order_mail);
        orderPhone=findViewById(R.id.order_phone_number);
        confirmOrder=findViewById(R.id.confirm_order);

        orderPhone.setText(Prevalent.currentOnlieUser.getPhone());

        overAllTotalAmount=getIntent().getStringExtra("totalPrice");
        totalAmount.setText(overAllTotalAmount+"\u20B9");
        Toast.makeText(this,overAllTotalAmount,Toast.LENGTH_SHORT).show();
        
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                check();
            }
        });


    }

    private void check() {
        if(TextUtils.isEmpty(orderAddress.getText().toString())){
            Toast.makeText(this,"Please provide your address..",Toast.LENGTH_SHORT).show();
        }
          else if(TextUtils.isEmpty(orderPhone.getText().toString())){
            Toast.makeText(this,"Please provide your phone..",Toast.LENGTH_SHORT).show();
        }
           else if(TextUtils.isEmpty(orderName.getText().toString())){
            Toast.makeText(this,"Please provide your Name..",Toast.LENGTH_SHORT).show();
        }
          else if(TextUtils.isEmpty(orderCity.getText().toString())){
            Toast.makeText(this,"Please provide your city..",Toast.LENGTH_SHORT).show();
        }
         else if(TextUtils.isEmpty(orderMail.getText().toString())){
            Toast.makeText(this,"Please provide your Email id..",Toast.LENGTH_SHORT).show();
        }else {
             ConfirmOrder();
        }
    }

    private void ConfirmOrder() {
        final List<Cart> listOfItems=new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd , yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        savecurrentTime=currentTime.format(calendar.getTime());

        final DatabaseReference orderRef= FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlieUser.getPhone());
        HashMap<String,Object> ordermap=new HashMap<>();
        ordermap.put("totalAmount",overAllTotalAmount);
        ordermap.put("name",orderName.getText().toString());
        ordermap.put("phone",Prevalent.currentOnlieUser.getPhone());
        ordermap.put("address",orderAddress.getText().toString());
        ordermap.put("city",orderCity.getText().toString());
        ordermap.put("Email",orderMail.getText().toString());
        ordermap.put("Date",saveCurrentDate);
        ordermap.put("Time",savecurrentTime);
        ordermap.put("State","Not Shipped");
        orderRef.updateChildren(ordermap).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("failure","",e);
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
             public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List").
                             child("User View")
                            .child(Prevalent.currentOnlieUser.getPhone())
                            .child("products").
                            addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                            for(DataSnapshot snapshot:datasnapshot.getChildren()){
                                listOfItems.add(snapshot.getValue(Cart.class));
                            }
                            updateAdminView(listOfItems);
                            updateCartView();
                        }

                         @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });

    }

    private void updateCartView() {
        FirebaseDatabase.getInstance().getReference().child("Cart List")
                .child("User View")
                .child(Prevalent.currentOnlieUser.getPhone()).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ConfirmFinalOrderActivity.this,"Congratulation,Your Order is placed...",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(ConfirmFinalOrderActivity.this,NewHomeActivity.class);
                            i.putExtra("person","Users");
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();

                        }
                    }
                });
    }

    private void updateAdminView(List<Cart> listOfItems) {
        final DatabaseReference cartListRef=FirebaseDatabase.getInstance().getReference().child("Cart List");
        for(Cart cartItem:listOfItems){
            final HashMap<String,Object> cartMap=new HashMap<>();
            cartMap.put("pid",cartItem.getPid());
            cartMap.put("name",cartItem.getName());
            cartMap.put("price",cartItem.getPrice());
            cartMap.put("weight",cartItem.getWeight());
            cartMap.put("usage",cartItem.getUsage());
            cartMap.put("quantity",cartItem.getQuantity());
            cartMap.put("date",cartItem.getDate());
            cartMap.put("sid",cartItem.getSid());
            cartMap.put("time",cartItem.getTime());
            cartMap.put("duration",cartItem.getDuration());

            cartListRef.child("Admin View").child(Prevalent.currentOnlieUser.getPhone())
                    .child(cartItem.getPid()).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(ConfirmFinalOrderActivity.this,"You can purchase more products once your order is verfied",Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

}