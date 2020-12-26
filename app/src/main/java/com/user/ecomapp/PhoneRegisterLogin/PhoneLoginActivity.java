package com.user.ecomapp.PhoneRegisterLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.ecomapp.Admins.AdminHomeActivity;
import com.user.ecomapp.Buyer.MainActivity;
import com.user.ecomapp.Buyer.NewHomeActivity;
import com.user.ecomapp.Buyer.ProductDetailsActivity;
import com.user.ecomapp.Prevalent.Prevalent;
import com.user.ecomapp.R;
import com.user.ecomapp.models.Users;

public class PhoneLoginActivity extends AppCompatActivity {
    private TextView gototregister,adminPanelLink,userPanelLink;
    private EditText inputPhoneNumber,inputPassword;
    private Button loginButton;
    private ImageButton gobacktomainpage;
    private String parentDBName="Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        inputPassword=findViewById(R.id.password_login);
        inputPhoneNumber=findViewById(R.id.phone_login);
        loginButton=findViewById(R.id.login_button);
        gototregister=findViewById(R.id.gotoregister);
        gobacktomainpage=findViewById(R.id.gobacktomain);
        userPanelLink=findViewById(R.id.iamnotadmin);
        adminPanelLink=findViewById(R.id.iamadmin);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        adminPanelLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Login admin");
                adminPanelLink.setVisibility(View.INVISIBLE);
                userPanelLink.setVisibility(View.VISIBLE);
                parentDBName="Admins";
            }
        });
        userPanelLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("Login");
                adminPanelLink.setVisibility(View.VISIBLE);
                userPanelLink.setVisibility(View.INVISIBLE);
                parentDBName="Users";

            }
        });

        gototregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PhoneLoginActivity.this,PhoneRegisterActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(PhoneLoginActivity.this);
                finish();
            }
        });

        gobacktomainpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PhoneLoginActivity.this,MainActivity.class);
                startActivity(intent);
                Animatoo.animateSlideDown(PhoneLoginActivity.this);
                finish();

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }
    private void loginUser(){
        String phone=inputPhoneNumber.getText().toString();
        String password=inputPassword.getText().toString();

        if(phone.isEmpty()){
            Toast.makeText(this,"Please enter the phone",Toast.LENGTH_SHORT);
        }
        else if(password.isEmpty()){
            Toast.makeText(this,"Please enter the password",Toast.LENGTH_SHORT);
        }
        else{
            AuthorizeUser(phone,password);
        }

    }

    private void AuthorizeUser(String phone, String password) {
        final DatabaseReference databaseReference;
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDBName).child(phone).exists()){
                    Users userData=dataSnapshot.child(parentDBName).child(phone).getValue(Users.class);
                    if(userData.getPhone().equals(phone)){
                        if(userData.getPassword().equals(password)){
                            if(parentDBName.equals("Admins")){

                                Toast.makeText(PhoneLoginActivity.this,"Welcome Admin....",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(PhoneLoginActivity.this, AdminHomeActivity.class);
                                startActivity(intent);

                            }else {

                                Toast.makeText(PhoneLoginActivity.this,"Logged in Successfully...",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(PhoneLoginActivity.this, NewHomeActivity.class);
                                intent.putExtra("person","Users");
                                Prevalent.currentOnlieUser=userData;
                                startActivity(intent);

                            }
                        }else{
                            Toast.makeText(PhoneLoginActivity.this,"Password is incorrect...",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}