package com.user.ecomapp.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.user.ecomapp.Admins.AdminMaintainProductsActivity;
import com.user.ecomapp.R;
import com.user.ecomapp.ViewHolder.ProductViewHolder;
import com.user.ecomapp.models.Products;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class NewHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mNavDrawer;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference productRef;
    private FloatingActionButton floatingActionButton;
    private String person;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout);
        person=getIntent().getStringExtra("person");


        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        mNavDrawer=(DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView=findViewById(R.id.navigationView);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,mNavDrawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        mNavDrawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Paper.init(this);
        floatingActionButton=findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(person.equals("Admins")){
                    Toast.makeText(NewHomeActivity.this,"Welcome admin..",Toast.LENGTH_SHORT).show();
                }
                else if(person.equals("Users")){
                    startActivity(new Intent(NewHomeActivity.this,CartActivity.class));

                }


            }
        });


        TextView userName=findViewById(R.id.user_name);
        CircleImageView profilePic=findViewById(R.id.user_profile_image);

        //userName.setText(Prevalent.currentOnlieUser.getName());
        productRef= FirebaseDatabase.getInstance().getReference().child("products");

        recyclerView=findViewById(R.id.recycler_menue);
        recyclerView.setHasFixedSize(false);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products>().
                setQuery(productRef.orderByChild("state").equalTo("Approved"),Products.class).build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull Products products) {
                productViewHolder.txtprod_name.setText(""+products.getName());
                productViewHolder.txtprod_price.setText(products.getPrice()+"\u20B9");
                productViewHolder.txtprod_weight.setText("Weight "+products.getWeight()+"kg");
                productViewHolder.txtprod_duration.setText(products.getDuration()+" Days");
                productViewHolder.txtprod_usage.setText(products.getUsage());
                productViewHolder.txtprod_desc.setText(products.getDescription());
                productViewHolder.txtProdSellerID.setText("seller id:"+products.getSid());

                Picasso.get().load(products.getImage()).into(productViewHolder.imageView);
                productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(person.equals("Admins")){

                            Intent i = new Intent(NewHomeActivity.this, AdminMaintainProductsActivity.class);
                            i.putExtra("pid", products.getPid());
                            startActivity(i);

                        }else if(person.equals("Users")){
                            Intent i = new Intent(NewHomeActivity.this, ProductDetailsActivity.class);
                            i.putExtra("pid", products.getPid());
                            startActivity(i);
                        }
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cart:
            {
                Intent i=new Intent(NewHomeActivity.this,CartActivity.class);
                    startActivity(i);

                return true;

            }
            case R.id.nav_myorder:
            {
                if(person.equals("Admins")){
                    Toast.makeText(NewHomeActivity.this,"Welcome admin..",Toast.LENGTH_SHORT).show();
                }
                if(person.equals("Users")){
                    Toast.makeText(NewHomeActivity.this,"Welcome User..",Toast.LENGTH_SHORT).show();
                }

                return true;
            }
            case R.id.nav_search:
            {
                if(person.equals("Admins")){
                    Toast.makeText(NewHomeActivity.this,"Welcome admin..",Toast.LENGTH_SHORT).show();
                }
                if(person.equals("Users")){

                    Intent i=new Intent(NewHomeActivity.this, SearchActivity.class);
                    startActivity(i);
                    return true;

                }

            }
            case R.id.nav_categories:
            {
                if(person.equals("Admins")){
                    Toast.makeText(NewHomeActivity.this,"Welcome admin..",Toast.LENGTH_SHORT).show();
                }
                if(person.equals("Users")){

                    return true;

                }

            }
            case R.id.nav_logout:
            {
                Paper.book().destroy();
                Intent intent=new Intent(NewHomeActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            }
            case R.id.nav_setting:
            {
                if(person.equals("Admins")){
                    Toast.makeText(NewHomeActivity.this,"Welcome admin..",Toast.LENGTH_SHORT).show();
                }
                else if(person.equals("Users")){

                    Intent intent=new Intent(NewHomeActivity.this,SettingActivity.class);
                    startActivity(intent);


                }
                return true;

            }
        }
        return false;
    }
}