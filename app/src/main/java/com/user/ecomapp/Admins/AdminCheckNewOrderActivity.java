package com.user.ecomapp.Admins;

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
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.ecomapp.R;
import com.user.ecomapp.ViewHolder.OrderViewHolder;
import com.user.ecomapp.models.Orders;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdminCheckNewOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private String saveCurrentDate,savecurrentTime;
    private DatabaseReference orderRef,cartRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_new_order);
        recyclerView = findViewById(R.id.order_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd , yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        savecurrentTime=currentTime.format(calendar.getTime());

        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        FirebaseRecyclerOptions<Orders> options = new FirebaseRecyclerOptions.Builder<Orders>().
                setQuery(orderRef, Orders.class).build();
        FirebaseRecyclerAdapter<Orders,OrderViewHolder> adapter=new FirebaseRecyclerAdapter<Orders, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i, @NonNull Orders orders) {
                orderViewHolder.orderPrice.setText("Total amount : "+orders.getTotalAmount());
                orderViewHolder.orderPhone.setText("Phone: "+orders.getPhone());
                orderViewHolder.orderName.setText("Name: "+orders.getName());
                orderViewHolder.orderDate.setText("Date: "+saveCurrentDate+" Time: "+savecurrentTime);
                orderViewHolder.orderAddress.setText("Place at: "+orders.getAddress()+","+orders.getCity());

                orderViewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pid=getRef(i).getKey();
                        Intent intent=new Intent(AdminCheckNewOrderActivity.this, AdminUserProductActivity.class);
                        intent.putExtra("pid",pid);
                        startActivity(intent);
                    }
                });
                orderViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[]=new CharSequence[]{
                                "yes",
                                "no"
                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(AdminCheckNewOrderActivity.this);
                        builder.setTitle("Have you shipped this product ?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    String pid=getRef(i).getKey();
                                    removeOrder(pid);
                                    removeCartList(pid);
                                    startActivity(new Intent(AdminCheckNewOrderActivity.this, AdminHomeActivity.class));
                                }else{
                                    finish();

                                }
                            }
                        });
                        builder.show();

                    }
                });


            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout,parent,false);
                OrderViewHolder holder=new OrderViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void removeCartList(String pid) {
        orderRef.child(pid).removeValue();
    }

    private void removeOrder(String pid) {
        cartRef=FirebaseDatabase.getInstance().getReference().child("Cart List").child("Admin View").child(pid);
        cartRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AdminCheckNewOrderActivity.this,"success..",Toast.LENGTH_SHORT).show();
            }
        });
    }
}