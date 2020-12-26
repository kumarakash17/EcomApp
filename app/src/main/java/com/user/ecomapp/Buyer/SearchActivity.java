package com.user.ecomapp.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.user.ecomapp.R;
import com.user.ecomapp.ViewHolder.ProductViewHolder;
import com.user.ecomapp.models.Products;

public class SearchActivity extends AppCompatActivity {
    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchButton=findViewById(R.id.search_button);
        searchEditText=findViewById(R.id.search_edit_text);
        recyclerView=findViewById(R.id.search_list);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText=searchEditText.getText().toString();
                onStart();


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("products");
        FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(reference.orderByChild("name").startAt(searchText),Products.class).build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter=new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i, @NonNull Products products) {
                productViewHolder.txtprod_name.setText(""+products.getName());
                productViewHolder.txtprod_price.setText(products.getPrice()+"\u20B9");
                productViewHolder.txtprod_weight.setText("Weight "+products.getWeight()+"kg");
                productViewHolder.txtprod_duration.setText(products.getDuration()+" Days");
                productViewHolder.txtprod_usage.setText(products.getUsage());
                productViewHolder.txtprod_desc.setText(products.getDescription());
                Picasso.get().load(products.getImage()).into(productViewHolder.imageView);
                productViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(SearchActivity.this, ProductDetailsActivity.class);
                        i.putExtra("pid",products.getPid());
                        startActivity(i);
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