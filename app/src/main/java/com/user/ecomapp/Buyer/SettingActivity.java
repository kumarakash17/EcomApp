package com.user.ecomapp.Buyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.user.ecomapp.Prevalent.Prevalent;
import com.user.ecomapp.R;

import java.util.HashMap;

public class SettingActivity extends AppCompatActivity {
    private EditText inputName,inputAddress,inputPincode;
    private TextView settiingClose,settingUpdate;
    private String parentDBName="Users";
    private DatabaseReference setting;
    private String name,address,pincode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        settiingClose=findViewById(R.id.close_setting);
        settingUpdate=findViewById(R.id.update_setting);
         inputAddress=findViewById(R.id.setting_address);
         inputName=findViewById(R.id.setting_name);
         inputPincode=findViewById(R.id.setting_pincode);

         settiingClose.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });

         settingUpdate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 updateSettingInfo();
             }
         });
        setting= FirebaseDatabase.getInstance().getReference().child(Prevalent.currentOnlieUser.getPhone());
        setting.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                     name=dataSnapshot.child("name").getValue().toString();
                     address=dataSnapshot.child("address").getValue().toString();
                     pincode=dataSnapshot.child("pincode").getValue().toString();

                     inputName.setText(name);
                     inputAddress.setText(address);
                     inputPincode.setText(pincode);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateSettingInfo() {

        HashMap<String,Object> user=new HashMap<>();
        user.put("name",inputName.getText().toString());
        user.put("address",inputAddress.getText().toString());
        user.put("pincode",inputPincode.getText().toString());

        setting.updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(parentDBName.equals("Users")) {
                    Toast.makeText(SettingActivity.this, "Profile Updated Successfully..", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SettingActivity.this, NewHomeActivity.class));
                }


            }
        });
    }

}