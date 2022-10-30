package com.mobilapp.expoter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class UserProfileActivity extends AppCompatActivity {

    private EditText EmailInp, UsernameInp , MobileNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        UsernameInp = (EditText) findViewById(R.id.register_username_input);
        EmailInp = (EditText) findViewById(R.id.register_email_input);
        MobileNumber = (EditText) findViewById(R.id.mobilenumber);

     showAllUserData();
    }

    private void showAllUserData() {
        Intent intent = getIntent();
        String user_username = intent.getStringExtra("username");
        String user_email = intent.getStringExtra("email");
        String user_phone = intent.getStringExtra("phone");

        Log.d("UserProfileActivity",user_username);


        UsernameInp.setText(user_username);
        EmailInp.setText(user_email);
        MobileNumber.setText(user_phone);
    }
}