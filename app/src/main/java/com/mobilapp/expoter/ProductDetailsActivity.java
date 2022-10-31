package com.mobilapp.expoter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobilapp.expoter.Controller.SessionManager;
import com.mobilapp.expoter.models.ProductModel;
import com.mobilapp.expoter.models.UserModel;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {
    SessionManager sessionManager;

    TextView productNameTitle, productPrice, description, ownerNameTxt, ownerEmailTxt, ownerPhoneTxt;
    ImageView itemImage;
    DatabaseReference reference;
    ProductModel product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        //session
        sessionManager = new SessionManager(this.getApplicationContext());
        sessionManager.checkLogin();

        reference = FirebaseDatabase.getInstance().getReference("products");

        Intent intent = getIntent();
        Bundle productBundle = intent.getExtras();
        if (productBundle != null) {
            String productId = (String) productBundle.get("productId");

            Log.d("Intent Data" , productId);


            productNameTitle = findViewById(R.id.titleText);
            productPrice = findViewById(R.id.priceTxt);
            ownerNameTxt = findViewById(R.id.ownerNameTxt);
            description = findViewById(R.id.descriptionText);
            ownerPhoneTxt = findViewById(R.id.ownerPhoneTxt);
            ownerEmailTxt = findViewById(R.id.ownerEmailTxt);
            itemImage = findViewById(R.id.itemImage);
            getProdutFromDB(productId);

        } else {
            Toast.makeText(this, "No product Id", Toast.LENGTH_SHORT).show();
        }
    }


    private void displayData(){
        productNameTitle.setText(product.getProductName());
        productPrice.setText(product.getProductPrice());
        ownerNameTxt.setText(product.getOwnerName());
        description.setText(product.getDescription());
        ownerPhoneTxt.setText(product.getOwnerPhone());
        ownerEmailTxt.setText(product.getOwnerEmail());

        try {

            Picasso.get().load(product.getImageUrl()).into(itemImage);
            //  Log.d("Pro details activity", String.valueOf(product.getImage().length));

        }catch (Exception e){
            Log.d("Cant load Image",e.getMessage());

        }

    }


    private void getProdutFromDB(String productId) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dss : dataSnapshot.getChildren()) {

                        if (dss.child("productId").getValue(String.class).equals(productId)) {
                            product = new ProductModel();
                            product.setProductId(dss.child("productId").getValue(String.class));
                            product.setProductName(dss.child("productName").getValue(String.class));
                            product.setProductPrice(dss.child("productPrice").getValue(String.class));
                            product.setOwnerPhone(dss.child("ownerPhone").getValue(String.class));
                            product.setOwnerName(dss.child("ownerName").getValue(String.class));
                            product.setOwnerEmail(dss.child("ownerEmail").getValue(String.class));
                            product.setDescription(dss.child("description").getValue(String.class));
                            product.setImageUrl(dss.child("imageUrl").getValue(String.class));

                            Log.d("", product.getProductName());
                        }
                    }
                displayData();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}