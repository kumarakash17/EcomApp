package com.user.ecomapp.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.user.ecomapp.Admins.AdminMaintainProductsActivity;
import com.user.ecomapp.Buyer.MainActivity;
import com.user.ecomapp.R;
import com.user.ecomapp.ViewHolder.ProductViewHolder;
import com.user.ecomapp.models.Products;

public class SellerHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mNavDrawer;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference productRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_nav_home_layout);
        recyclerView=findViewById(R.id.seller_home_list);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        Toolbar toolbar=findViewById(R.id.seller_toolbar);
        toolbar.setTitle("All products");
        setSupportActionBar(toolbar);
        productRef= FirebaseDatabase.getInstance().getReference().child("products");

        mNavDrawer=findViewById(R.id.seller_drawer_layout);
        NavigationView navigationView=findViewById(R.id.navigationViewseller);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,mNavDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        mNavDrawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    public void onBackPressed() {
        if(mNavDrawer.isDrawerOpen(GravityCompat.START)){
            mNavDrawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menueitem) {
        switch (menueitem.getItemId()){
            case R.id.nav_seller_Home:
            {
                startActivity(new Intent(SellerHomeActivity.this,SellerHomeActivity.class));
                return true;

            }
            case R.id.nav_seller_order:

            {
                startActivity(new Intent(SellerHomeActivity.this,SellerOrderActivity.class));
                return true;

            }
            case R.id.nav_seller_add:
            {
                startActivity(new Intent(SellerHomeActivity.this,SellerCategoryActivity.class));
                return true;

            }
            case R.id.nav_seller_unverified_prod:
            {

                startActivity(new Intent(SellerHomeActivity.this,SellerUnverifiedProductsActivity.class));
                return true;

            }
            case R.id.nav_seller_logout:
            {
                final FirebaseAuth mAuth;
                mAuth=FirebaseAuth.getInstance();
                mAuth.signOut();

                Intent i=new Intent(SellerHomeActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                productViewHolder.txtprod_name.setText(""+products.getName()+"\n"+"state: "+products.getState());
                productViewHolder.txtprod_desc.setText(products.getDescription());
                productViewHolder.txtprod_price.setText(products.getPrice()+"\u20B9");
                productViewHolder.txtprod_weight.setText("Weight "+products.getWeight()+"kg");
                productViewHolder.txtprod_duration.setText(products.getDuration()+" Days");
                productViewHolder.txtprod_usage.setText(products.getUsage());
                productViewHolder.txtProdSellerID.setText("seller id"+products.getSid());


                Picasso.get().load(products.getImage()).into(productViewHolder.imageView);
                productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[]=new CharSequence[]{
                                "yes",
                                "no"
                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(SellerHomeActivity.this);
                        builder.setTitle("Do you wanna Edit or delete this project?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                            Intent i=new Intent(SellerHomeActivity.this, AdminMaintainProductsActivity.class);
                                            i.putExtra("pid",products.getPid());
                                            startActivity(i);

                                }else{


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