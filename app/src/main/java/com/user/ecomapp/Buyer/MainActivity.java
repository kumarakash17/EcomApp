package com.user.ecomapp.Buyer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.user.ecomapp.PhoneRegisterLogin.PhoneLoginActivity;
import com.user.ecomapp.PhoneRegisterLogin.PhoneRegisterActivity;
import com.user.ecomapp.R;
import com.user.ecomapp.Sellers.SellerHomeActivity;
import com.user.ecomapp.Sellers.SellerRegisterActivity;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayout phonSignin;
    private LinearLayout phoneSignup;
    private Button skipbutton;
    private TextView sellerBegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        phoneSignup = findViewById(R.id.linearLayout1);
        skipbutton = findViewById(R.id.skip_button);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setKeepScreenOn(true);
        recyclerView.setHasFixedSize(true);
        phonSignin=findViewById(R.id.linearLayout2);
        sellerBegin=findViewById(R.id.seller_begin);


        phoneSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhoneRegisterActivity.class);
                startActivity(intent);

                Animatoo.animateSlideDown(MainActivity.this);
                finish();

            }
        });
        skipbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewHomeActivity.class);
                startActivity(intent);
                Animatoo.animateSwipeRight(MainActivity.this);


            }
        });
        phonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PhoneLoginActivity.class));
                Animatoo.animateSwipeRight(MainActivity.this);
            }
        });
        sellerBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SellerRegisterActivity.class));
                Animatoo.animateSwipeRight(MainActivity.this);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            Intent i=new Intent(MainActivity.this,SellerHomeActivity.class);
            startActivity(i);
        }



    }
}