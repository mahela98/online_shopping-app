package com.mobilapp.expoter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.mobilapp.expoter.Controller.SessionManager;

public class HomeActivity extends AppCompatActivity {

    SessionManager sessionManager;
    Button allProductListBtn, MyProductsBtn,cartBtn,profileBtn,logoutBtn,addProductBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();


        allProductListBtn = (Button) findViewById(R.id.allProductListBtn);
        MyProductsBtn = (Button) findViewById(R.id.MyProductsBtn);
        cartBtn = (Button) findViewById(R.id.cartBtn);
        addProductBtn = (Button) findViewById(R.id.addProductBtn);
        profileBtn = (Button) findViewById(R.id.profileBtn);
        logoutBtn = (Button) findViewById(R.id.logoutBtn);


        allProductListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("","allProductListBtn");
            }
        });

        MyProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("","MyProductsBtn");

            }
        });

        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("","cartBtn");

            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("","addProductBtn");
                Intent intent = new Intent(HomeActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("","profileBtn");
                Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
                startActivity(intent);

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("","logoutBtn");
                sessionManager.logOutUser();
            }
        });


    }
}