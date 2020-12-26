package com.user.ecomapp.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.user.ecomapp.R;

public class SellerLoginActivity extends AppCompatActivity {
    private EditText email,password;
    private Button seller_login;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        email=findViewById(R.id.seller_email_login);
        password=findViewById(R.id.seller_password_login);
        seller_login=findViewById(R.id.seller_login_button);
        mAuth=FirebaseAuth.getInstance();

        seller_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        }

    private void loginUser() {

        final String iPassword=password.getText().toString();
        final String iEmail=email.getText().toString();
        if(iEmail.equals("")||iPassword.equals("")){
            Toast.makeText(this,"Please input your login details..",Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(iEmail,iPassword).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(SellerLoginActivity.this,SellerHomeActivity.class));
                                finish();
                            }
                        }
                    }
            );

        }
    }

}