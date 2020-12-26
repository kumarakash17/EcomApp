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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.ecomapp.Buyer.MainActivity;
import com.user.ecomapp.R;

import java.util.HashMap;

public class PhoneRegisterActivity extends AppCompatActivity {
    private TextView gotologin;
    private ImageButton gobacktomain;
    private Button register_button;
    private EditText register_name,register_phone,register_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);

        gotologin=findViewById(R.id.gotoLogin);
        register_name=findViewById(R.id.reg_name);
        register_phone=findViewById(R.id.reg_phone);
        register_password=findViewById(R.id.reg_password);
        register_button=findViewById(R.id.register_button);
        gobacktomain=findViewById(R.id.gobacktomain);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creatAccount();
            }
        });

       gotologin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(PhoneRegisterActivity.this,PhoneLoginActivity.class);
               startActivity(intent);
               Animatoo.animateSlideDown(PhoneRegisterActivity.this);
               finish();
           }
       });
       gobacktomain.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(PhoneRegisterActivity.this,MainActivity.class);
               startActivity(intent);
               Animatoo.animateSlideDown(PhoneRegisterActivity.this);
               finish();
           }
       });
    }
    private void  creatAccount(){
        String name=register_name.getText().toString();
        String phone=register_phone.getText().toString();
        String password=register_password.getText().toString();

        if(name.isEmpty()){
            Toast.makeText(this,"Please enter the name",Toast.LENGTH_SHORT);
        }
        else if(phone.isEmpty()){
            Toast.makeText(this,"Please enter the phone",Toast.LENGTH_SHORT);
        }
        else if(password.isEmpty()){
            Toast.makeText(this,"Please enter the password",Toast.LENGTH_SHORT);
        }
        else{
            ValidateUser(name,phone,password);
        }


    }

    private void ValidateUser(String name, String phone, String password) {
        final DatabaseReference databaseReference;
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if(!(datasnapshot.child("Users").child(phone).exists())){
                    HashMap<String,Object> registerUser=new HashMap<>();
                    registerUser.put("name",name);
                    registerUser.put("phone",phone);
                    registerUser.put("password",password);
                    databaseReference.child("Users").child(phone).updateChildren(registerUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(PhoneRegisterActivity.this,"Congratulations your account is created..",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(PhoneRegisterActivity.this,PhoneLoginActivity.class);
                                startActivity(i);
                            }else{
                                Toast.makeText(PhoneRegisterActivity.this,"Network error!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(PhoneRegisterActivity.this,"User already exist..",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

}