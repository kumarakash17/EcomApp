package com.user.ecomapp.Sellers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.ecomapp.Buyer.MainActivity;
import com.user.ecomapp.R;

import java.util.HashMap;

public class SellerRegisterActivity extends AppCompatActivity {
    private EditText name,phone,email,password,address,pan,gst,bankAccountNumber,ifscCode,phoneBank,emailBank;
    private Button seller_register,login_begin;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_seller_register);
        name=findViewById(R.id.seller_name);
        phone=findViewById(R.id.seller_phone);
        email=findViewById(R.id.seller_email);
        password=findViewById(R.id.seller_password);
        bankAccountNumber=findViewById(R.id.seller_bank_Account);
        address=findViewById(R.id.seller_address);
        pan=findViewById(R.id.seller_pan_no);
        gst=findViewById(R.id.gst_num);
        ifscCode=findViewById(R.id.ifsc_code);
        phoneBank=findViewById(R.id.seller_phone_number_linked_to_account_number);
        emailBank=findViewById(R.id.seller_mail_linked_to_account_number);
        seller_register=findViewById(R.id.seller_registration);
        login_begin=findViewById(R.id.seller_login_begin);

        login_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerRegisterActivity.this,SellerLoginActivity.class));
                Animatoo.animateSwipeRight(SellerRegisterActivity.this);
            }
        });
        seller_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSeller();
            }
        });


    }

    private void registerSeller() {
        String sName=name.getText().toString();
        String sPhone=phone.getText().toString();
        String sEmail=email.getText().toString();
        String sPassword=password.getText().toString();
        String sAddress=address.getText().toString();
        String sPan=pan.getText().toString();
        String sGst=gst.getText().toString();
        String sBankAccount=bankAccountNumber.getText().toString();
        String sIFSC=ifscCode.getText().toString();
        String sPhoneBank=phoneBank.getText().toString();
        String sEmailBank=emailBank.getText().toString();

        if (sName.equals("")||sAddress.equals("")||sBankAccount.equals("")||sEmail.equals("")||sPhone.equals("")||sGst.equals("")||sEmailBank.equals("")||sIFSC.equals("")
                ||sPan.equals("")||sAddress.equals("")||sPhoneBank.equals("")||sPassword.equals("")
        ){
            Toast.makeText(this,"Please fill all the fields..",Toast.LENGTH_SHORT).show();

        }else {
            mAuth.createUserWithEmailAndPassword(sEmail,sPassword).
                    addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        final DatabaseReference sellerRef= FirebaseDatabase.getInstance().getReference();
                        String sid=mAuth.getCurrentUser().getUid();
                        HashMap<String,Object> sellerMap=new HashMap<>();
                        sellerMap.put("name",sName);
                        sellerMap.put("phone",sPhone);
                        sellerMap.put("email",sEmail);
                        sellerMap.put("password",sPassword);
                        sellerMap.put("address",sAddress);
                        sellerMap.put("pan",sPan);
                        sellerMap.put("gst",sGst);
                        sellerMap.put("bankAccountNumber",sBankAccount);
                        sellerMap.put("ifsc",sIFSC);
                        sellerMap.put("phoneLinkedWithBank",sPhoneBank);
                        sellerMap.put("emailLinkedWithBank",sEmailBank);
                        sellerMap.put("sid",sid);

                        sellerRef.child("Sellers").child(sid).updateChildren(sellerMap).addOnCompleteListener(
                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(SellerRegisterActivity.this,"You have registered successfully...",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SellerRegisterActivity.this,SellerHomeActivity.class));

                                    }
                                }
                        );
                    }else{
                        Toast.makeText(SellerRegisterActivity.this,"Registration failed.."+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }
}