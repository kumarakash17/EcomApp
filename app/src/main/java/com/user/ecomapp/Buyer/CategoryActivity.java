package com.user.ecomapp.Buyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.user.ecomapp.R;
import com.user.ecomapp.Sellers.SellerCategoryActivity;

public class CategoryActivity extends AppCompatActivity {
    private ImageView mobile,mob_access,tablet,laptops,laptops_access,renwed_mobile,pendriveAndStories,office_supplies,power_bank;
    private ImageView all_electronics,television,air_conditoner,regrigeretors;
    private ImageView jeans,shirt,tshirt,shorts;
    private ImageView sarees,ladiesjeans,tops,western_wear;
    private ImageView fruits,vegetables,spices,snacks;
    private ImageView otherproducts,newproducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        mobile=findViewById(R.id.mobiles);
        mob_access=findViewById(R.id.mobiles_accesseries);
        tablet=findViewById(R.id.tablets);
        laptops=findViewById(R.id.laptops);
        laptops_access=findViewById(R.id.laptop_accesseries);
        renwed_mobile=findViewById(R.id.renewed_mobiles);
        pendriveAndStories=findViewById(R.id.pendrivesAndStorages);
        office_supplies=findViewById(R.id.office_supplies_and_storages);
        power_bank=findViewById(R.id.powerbanks);
        all_electronics=findViewById(R.id.all_electronics);
        television=findViewById(R.id.televisions);
        air_conditoner=findViewById(R.id.air_conditioner);
        regrigeretors=findViewById(R.id.refrigeretors);
        jeans=findViewById(R.id.jeans);
        shirt=findViewById(R.id.shirts);
        tshirt=findViewById(R.id.tshirts);
        shorts=findViewById(R.id.shorts);
        sarees=findViewById(R.id.saarees);
        ladiesjeans=findViewById(R.id.womens_jeans);
        tops=findViewById(R.id.tops);
        western_wear=findViewById(R.id.western_wear);
        vegetables=findViewById(R.id.vegetables);
        fruits=findViewById(R.id.fruits);
        spices=findViewById(R.id.spices);
        snacks=findViewById(R.id.snacs);
        otherproducts=findViewById(R.id.other_products);
        newproducts=findViewById(R.id.new_products_coming_soon);



        String mob="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fmobiles.jpg?alt=media&token=7e803fa1-77ac-4082-9c66-d4ec57112ebd";
        Glide.with(getApplicationContext()).load(mob).into(mobile);

        String othpro="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fotherproductshoverimage.png?alt=media&token=d5e788ea-aaa4-4b52-90f7-098b4f784a25";
        Glide.with(getApplicationContext()).load(othpro).into(otherproducts);

        String newPro="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fproduct-coming-soon.jpg?alt=media&token=e627d7e9-6abc-46ee-97a6-578e2cb5f19d";
        Glide.with(getApplicationContext()).load(newPro).into(newproducts);

        String mob_Ac="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fmobile-accessories-for-phone.jpg?alt=media&token=cdf35619-a48b-41cb-8571-3fd319c5f530";
        Glide.with(getApplicationContext()).load(mob_Ac).into(mob_access);
        String tab="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Ftablet.jpg?alt=media&token=45c5006d-773d-4f58-baa9-611cb8d3e0fc";
        Glide.with(getApplicationContext()).load(tab).into(tablet);

        String lap="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Flaptop.png?alt=media&token=2d83b800-c5fc-4ebc-894f-83ba966ee75f";
        Glide.with(getApplicationContext()).load(lap).into(laptops);

        String lap_Acc="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Flaptop_accessories.jpg?alt=media&token=c19f338f-f2bd-42f8-9d86-bf2361e2d0e9";
        Glide.with(getApplicationContext()).load(lap_Acc).into(laptops_access);

        String ren_mob="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2FNokia-6.1-plus-refurbished-phone.jpg?alt=media&token=37c5f1b3-3697-4c07-a798-8b05115f9a35";
        Glide.with(getApplicationContext()).load(ren_mob).into(renwed_mobile);

        String pendr="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fpendrive.jpg?alt=media&token=1ff57ac7-0fb8-445e-b479-851f7ee0ca7c";
        Glide.with(getApplicationContext()).load(pendr).into(pendriveAndStories);

        String power_b="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fpowerbank_capacity-696x409.jpg?alt=media&token=780a438f-9ddc-42ed-8ce6-d4c55c4feea8";
        Glide.with(getApplicationContext()).load(power_b).into(power_bank);

        String all_elec="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fconsumer-electronic-products.jpg?alt=media&token=921f4cfc-0c0c-4fca-8ed4-3094553276ad";
        Glide.with(getApplicationContext()).load(all_elec).into(all_electronics);

        String tv="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Ftelevision.jpg?alt=media&token=9b369819-3464-441c-8c35-54531f18b43c";
        Glide.with(getApplicationContext()).load(tv).into(television);

        String air_cond="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fac.jpg?alt=media&token=13b46346-5ac3-4b04-b06d-cbee43b6042b";
        Glide.with(getApplicationContext()).load(air_cond).into(air_conditoner);

        String freeg="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Ffreedge.jpg?alt=media&token=6e02d569-c02d-461a-95a7-779f9051b784";
        Glide.with(getApplicationContext()).load(freeg).into(regrigeretors);

        String j="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fslim-men-jeans-500x500.jpg?alt=media&token=43450f9a-4fc2-4937-8ea3-839eb8f2f109";
        Glide.with(getApplicationContext()).load(j).into(jeans);

        String sh="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fshirts.jpg?alt=media&token=9e0deb7d-cfa3-475a-8259-2a817de781ea";
        Glide.with(getApplicationContext()).load(sh).into(shirt);

        String tsh="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Ftshirt.jpg?alt=media&token=e463fcf9-2c57-4141-8fd4-db6f02630299";
        Glide.with(getApplicationContext()).load(tsh).into(tshirt);

        String sho="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fmensshorrts.jpg?alt=media&token=d4988309-a789-46f0-ac6a-5d82e0d69cb8";
        Glide.with(getApplicationContext()).load(sho).into(shorts);

        String lad_j="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2FladiesJeans.webp?alt=media&token=376a4def-0fc8-41cf-a525-c11d60f69c17";
        Glide.with(getApplicationContext()).load(lad_j).into(ladiesjeans);

        String sar="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fsaree.webp?alt=media&token=1d93b7e4-2cfb-490b-9963-7d07dfe6d41c";
        Glide.with(getApplicationContext()).load(sar).into(sarees);

        String tp="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Ftops.jpg?alt=media&token=9627006f-f487-4ff3-8b84-3ef7045602c5";
        Glide.with(getApplicationContext()).load(tp).into(tops);

        String wes="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fwestern.jpg?alt=media&token=a8f87109-47ff-4a3b-a832-bad24b7b8809";
        Glide.with(getApplicationContext()).load(wes).into(western_wear);

        String veg="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fvegetables.jpg?alt=media&token=8b52c9ff-3589-4a79-9563-241f01cced73";
        Glide.with(getApplicationContext()).load(veg).into(vegetables);

        String fru="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Ffruit-main.jpg?alt=media&token=f73fa160-f284-4440-935c-3ee355bc1e1f";
        Glide.with(getApplicationContext()).load(fru).into(fruits);

        String spic="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fspices.jpg?alt=media&token=6c268485-f1a5-46ef-b678-e18c7d13af6b";
        Glide.with(getApplicationContext()).load(spic).into(spices);

        String sn="https://firebasestorage.googleapis.com/v0/b/mystoreforquicky.appspot.com/o/Categories%2Fsnacks.jpg?alt=media&token=56dfde86-c3bc-4fbc-a5d6-3d934a789d31";
        Glide.with(getApplicationContext()).load(sn).into(snacks);

        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","mobile");
                startActivity(i);
            }
        });
        mob_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","mobile_accesseries");
                startActivity(i);
            }
        });
        renwed_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","renwed_mobile");
                startActivity(i);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","laptops");
                startActivity(i);
            }
        });
        laptops_access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","laptops_access");
                startActivity(i);
            }
        });
        tablet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","tablet");
                startActivity(i);
            }
        });
        office_supplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","office_supplies");
                startActivity(i);
            }
        });
        pendriveAndStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","pendriveAndStories");
                startActivity(i);
            }
        });
        all_electronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","all_electronics");
                startActivity(i);
            }
        });
        television.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","television");
                startActivity(i);
            }
        });
        air_conditoner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","air_conditoner");
                startActivity(i);
            }
        });
        regrigeretors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","regrigeretors");
                startActivity(i);
            }
        });
        shirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","shirt");
                startActivity(i);
            }
        });
        jeans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","jeans");
                startActivity(i);
            }
        });
        shorts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","shorts");
                startActivity(i);
            }
        });
        tshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","tshirt");
                startActivity(i);
            }
        });
        sarees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","sarees");
                startActivity(i);
            }
        });
        ladiesjeans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","ladiesjeans");
                startActivity(i);
            }
        });
        western_wear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","western_wear");
                startActivity(i);
            }
        });
        tops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","tops");
                startActivity(i);
            }
        });
        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CategoryActivity.this, ParticularCategory.class);
                i.putExtra("category","fruits");
                startActivity(i);
            }
        });
        otherproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CategoryActivity.this,ParticularCategory.class);
                intent.putExtra("category","other_products");
                startActivity(intent);
            }
        });
        newproducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CategoryActivity.this,ParticularCategory.class);
                intent.putExtra("category","new_products_coming_soon");
                startActivity(intent);

            }
        });

    }
}