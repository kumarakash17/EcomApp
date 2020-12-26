package com.user.ecomapp.Admins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.user.ecomapp.Buyer.MainActivity;
import com.user.ecomapp.Buyer.NewHomeActivity;
import com.user.ecomapp.R;
import com.user.ecomapp.ViewHolder.ProductViewHolder;
import com.user.ecomapp.models.Products;

import java.util.HashMap;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Button logout,checkOrders,maintainProd,checkNewProduct;
    private DrawerLayout mNavDrawer;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference productRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_drawer_layout);

        recyclerView=findViewById(R.id.new_products_re);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        productRef= FirebaseDatabase.getInstance().getReference().child("product");


        Toolbar toolbar=findViewById(R.id.admin_toolbar);
        toolbar.setTitle("All unverified products");
        setSupportActionBar(toolbar);

        mNavDrawer=findViewById(R.id.admin_drawer_layout);
        NavigationView navigationView=findViewById(R.id.navigation_view_admin);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,mNavDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        mNavDrawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);




           }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {

            case R.id.nav_maintain_pro:
            {
                Intent intent=new Intent(AdminHomeActivity.this, NewHomeActivity.class);
                intent.putExtra("person","Admins");
                startActivity(intent);
                return true;
            }
            case R.id.nav_check_order:
            {
                startActivity(new Intent(AdminHomeActivity.this, AdminCheckNewOrderActivity.class));
                return true;
            }
            case R.id.nav_amount:
            {
                return true;
            }
            case R.id.nav_seller:
            {
                startActivity(new Intent(AdminHomeActivity.this,AdminSellerActivity.class));
                return true;
            }
            case R.id.nav_admin_logout:
            {
                Intent i=new Intent(AdminHomeActivity.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
                return true;
            }
        }
        return false;
    }
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products>().
                setQuery(productRef,Products.class).build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull Products products) {
                productViewHolder.txtprod_name.setText(""+products.getName());
                productViewHolder.txtprod_price.setText(products.getPrice()+"\u20B9");
                productViewHolder.txtprod_weight.setText("Weight "+products.getWeight()+"kg");
                productViewHolder.txtprod_duration.setText(products.getDuration()+" Days");
                productViewHolder.txtprod_usage.setText(products.getUsage());
                productViewHolder.txtprod_desc.setText(products.getDescription());
                productViewHolder.txtProdSellerID.setText("seller id : "+products.getSid());

                Picasso.get().load(products.getImage()).into(productViewHolder.imageView);
                productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[]=new CharSequence[]{
                                "yes",
                                "no"
                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(AdminHomeActivity.this);
                        builder.setTitle("Do you wanna approve this project?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    final String category=products.getCategory();
                                    final String date=products.getDate();
                                    final String description=products.getDescription();
                                    final String duration=products.getDuration();
                                    final String name=products.getName();
                                    final String phone=products.getPhone();
                                    final String pid=products.getPid();
                                    final String price=products.getPrice();
                                    final String image=products.getImage();
                                    final String sellerAddress=products.getSellerAddress();
                                    final String sellerEmail=products.getSellerEmail();
                                    final String sellerName=products.getSellerName();
                                    final String weight=products.getWeight();
                                    final String usage=products.getUsage();
                                    final String time=products.getTime();
                                    final String sid=products.getSid();
                                    final String sellerPhone=products.getSellerPhone();
                                    HashMap<String,Object> pro=new HashMap<>();
                                    pro.put("category",category);
                                    pro.put("date",date);
                                    pro.put("description",description);
                                    pro.put("duration",duration);
                                    pro.put("name",name);
                                    pro.put("phone",phone);
                                    pro.put("pid",pid);
                                    pro.put("price",price);
                                    pro.put("image",image);
                                    pro.put("sellerAddress",sellerAddress);
                                    pro.put("sellerEmail",sellerEmail);
                                    pro.put("sellerName",sellerName);
                                    pro.put("state","Approved");
                                    pro.put("weight",weight);
                                    pro.put("usage",usage);
                                    pro.put("time",time);
                                    pro.put("sid",sid);
                                    pro.put("sellerPhone",sellerPhone);

                                    final DatabaseReference productsRef=FirebaseDatabase.getInstance().getReference().child("products");
                                    productsRef.child(products.getPid()).updateChildren(pro).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(AdminHomeActivity.this,"product is approved",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    productRef.child(products.getPid()).removeValue();


                                }else{
                                    productRef.child(products.getPid()).removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(AdminHomeActivity.this,"Product is deleted",Toast.LENGTH_SHORT).show();
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
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout,parent,false);
                ProductViewHolder holder=new ProductViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}