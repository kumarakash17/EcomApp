package com.user.ecomapp.Sellers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.user.ecomapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SellerAddNewProductActivity extends AppCompatActivity {
    private String categoryName,name,descr,price,weight,duration,usage,saveCurrentDate,savecurrentTime,productRandomKey,downloadImageUrl;
    private EditText prod_name,prod_desc,prod_price,prod_weight,prod_duration,prod_usage;
    private ImageView addImageofproduct;
    private Button addNewProduct;
    private static final int GalleryPic=1;
    private StorageReference produtImageRef;
    private DatabaseReference productReference,sellerRef;
    private Uri imageUri;
    private String UniqueId,sName,sAddress,sPhone,sEmail,sID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_new_product);

        categoryName=getIntent().getExtras().getString("category").toString();
        Toast.makeText(this,categoryName,Toast.LENGTH_LONG).show();
        prod_name=findViewById(R.id.productname);
        prod_desc=findViewById(R.id.productdesc);
        prod_price=findViewById(R.id.productprice);
        prod_duration=findViewById(R.id.productduration);
        prod_weight=findViewById(R.id.productweight);
        prod_usage=findViewById(R.id.productusage);
        addImageofproduct=findViewById(R.id.addnewimage);
        addNewProduct=findViewById(R.id.addNewProduct);

        produtImageRef= FirebaseStorage.getInstance().getReference().child("product image");
        productReference=FirebaseDatabase.getInstance().getReference().child("product");
        sellerRef=FirebaseDatabase.getInstance().getReference().child("Sellers");

        addImageofproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyInfo();
            }
        });
        sellerRef.child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            sName=dataSnapshot.child("name").getValue().toString();
                            sAddress=dataSnapshot.child("address").getValue().toString();
                            sEmail=dataSnapshot.child("email").getValue().toString();
                            sPhone=dataSnapshot.child("phone").getValue().toString();
                            sID=dataSnapshot.child("sid").getValue().toString();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        }

    private void verifyInfo() {
        name=prod_name.getText().toString();
        descr=prod_desc.getText().toString();
        price=prod_price.getText().toString();
        weight=prod_weight.getText().toString();
        usage=prod_usage.getText().toString();
        duration=prod_duration.getText().toString();



        if(imageUri==null){
            Toast.makeText(this,"Please upload product image",Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please write product name",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(descr)){
            Toast.makeText(this,"Please write product descritption",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(price)){
            Toast.makeText(this,"Please write product price",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty( weight)){
            Toast.makeText(this,"Please write product weight",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(duration)){
            Toast.makeText(this,"Please write product duration",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(usage)){
            Toast.makeText(this,"Please write product usage",Toast.LENGTH_SHORT).show();
        }else {
            storeProductInformation();
        }

    }

    private void storeProductInformation() {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd , yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        savecurrentTime=currentTime.format(calendar.getTime());
        productRandomKey=saveCurrentDate+savecurrentTime;

        final StorageReference filepath=produtImageRef.child(imageUri.getLastPathSegment()+productRandomKey+".jpg");
        UniqueId=productRandomKey+imageUri.getLastPathSegment();
        final UploadTask uploadTask=filepath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String exception=e.toString();
                Toast.makeText(SellerAddNewProductActivity.this,"Error..",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(SellerAddNewProductActivity.this,"Product Image successfully..",Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        String downloadImageUri=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadImageUrl=task.getResult().toString();
                            Toast.makeText(SellerAddNewProductActivity.this,"Got the product image url successfully",Toast.LENGTH_SHORT).show();
                            saveProductIntoDatabase();

                        }
                    }
                });
            }
        });

    }
    private void saveProductIntoDatabase(){
        HashMap<String,Object> productInfo=new HashMap<>();
        productInfo.put("pid",productRandomKey);
        productInfo.put("date",saveCurrentDate);
        productInfo.put("category",categoryName);
        productInfo.put("time",savecurrentTime);
        productInfo.put("name",name);
        productInfo.put("descrption",descr);
        productInfo.put("price",price);
        productInfo.put("weight",weight);
        productInfo.put("duration",duration);
        productInfo.put("usage",usage);
        productInfo.put("image",downloadImageUrl);
        productInfo.put("state","Not Approved");
        productInfo.put("sellerName",sName);
        productInfo.put("sellerAddress",sAddress);
        productInfo.put("sellerPhone",sPhone);
        productInfo.put("sellerEmail",sEmail);
        productInfo.put("sid",sID);

        productReference.child(productRandomKey).updateChildren(productInfo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SellerAddNewProductActivity.this,"Product added successfully...",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(SellerAddNewProductActivity.this, SellerHomeActivity.class);
                            intent.putExtra("uId",UniqueId);
                            startActivity(intent);
                        }
                        else{
                            String message=task.getException().toString();
                            Toast.makeText(SellerAddNewProductActivity.this,"Error "+message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void openGallery() {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/'");
            startActivityForResult(intent, GalleryPic);

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPic &&resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
            addImageofproduct.setImageURI(imageUri);
        }
    }
}