package com.mobilapp.expoter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobilapp.expoter.Controller.MyAdapter;
import com.mobilapp.expoter.models.ProductModel;

import java.util.ArrayList;

public class AllProductsActivity extends AppCompatActivity {
    ListView listView;

    DatabaseReference reference;
    ArrayList<ProductModel> productModelArrayList=new ArrayList<ProductModel>();
    MyAdapter myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        reference= FirebaseDatabase.getInstance().getReference().child("products");

        getProductListFromDB();
    
    }

private void displayList(){
    listView = findViewById(R.id.listviewId);
    myAdapter = new MyAdapter(this, productModelArrayList);
    listView.setAdapter(myAdapter);
    myAdapter.notifyDataSetChanged();
}



    public void getProductListFromDB(){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                     for (DataSnapshot dss: dataSnapshot.getChildren()){

                         ProductModel product= new ProductModel();
                         product.setProductId( dss.child("productId").getValue(String.class));
                         product.setProductName( dss.child("productName").getValue(String.class));
                         product.setProductPrice( dss.child("productPrice").getValue(String.class));
                         product.setOwnerPhone( dss.child("ownerPhone").getValue(String.class));
                         product.setOwnerName( dss.child("ownerName").getValue(String.class));
                         product.setOwnerEmail( dss.child("ownerEmail").getValue(String.class));
                         product.setDescription( dss.child("description").getValue(String.class));
                         product.setImageUrl( dss.child("imageUrl").getValue(String.class));

                         Log.d("",product.getProductName());

                         productModelArrayList.add(product);
                     }

                    if (!productModelArrayList.isEmpty()){
                        displayList();

                    }else {
                        Toast.makeText(AllProductsActivity.this, "No Products", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}