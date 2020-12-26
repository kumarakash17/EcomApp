package com.user.ecomapp.Admins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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
import com.user.ecomapp.ViewHolder.ProductViewHolder;
import com.user.ecomapp.ViewHolder.SellerViewHolder;
import com.user.ecomapp.models.Products;
import com.user.ecomapp.models.Sellers;

public class AdminSellerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference sellerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_seller);

        recyclerView=findViewById(R.id.admin_seller_list);
        recyclerView.setHasFixedSize(false);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        sellerRef= FirebaseDatabase.getInstance().getReference().child("Sellers");

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Sellers> options=new FirebaseRecyclerOptions.Builder<Sellers>().
                setQuery(sellerRef,Sellers.class).build();
        FirebaseRecyclerAdapter<Sellers, SellerViewHolder> adapter=new FirebaseRecyclerAdapter<Sellers, SellerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SellerViewHolder sellerViewHolder, int i, @NonNull Sellers sellers) {
                sellerViewHolder.txtSellerTextView.setText("Name: "+sellers.getName()+"\n\n"+"Phone: "+sellers.getPhone()+"\n\n"+"Bank account number: "
                        +sellers.getBankAccountNumber()+"\n\n"
                        +"ifsc code "+ sellers.getIfsc()+"\n\n"+"Authorized phone: "+sellers.getPhone()+"\n\n"
                        +"Authorized Email: "+sellers.getEmailLinkedWithBank());
                sellerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(AdminSellerActivity.this);
                        builder.setTitle("Do you want to remove this seller?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sellerRef.child(sellers.getSid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(AdminSellerActivity.this,"Deleted..",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public SellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_seller_layout,parent,false);
                SellerViewHolder holder=new SellerViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}