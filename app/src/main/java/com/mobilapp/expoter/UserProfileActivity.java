package com.mobilapp.expoter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfileActivity extends AppCompatActivity {

    private EditText EmailInp, UsernameInp, MobileNumber;
    private String user_username, user_email, user_phone;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        // get all users
        reference = FirebaseDatabase.getInstance("https://expoter-a07aa-default-rtdb.firebaseio.com/").getReference("users");

        Button loginBtn_loginPage = (Button) findViewById(R.id.updateDetails);

        UsernameInp = (EditText) findViewById(R.id.register_username_input);
        EmailInp = (EditText) findViewById(R.id.register_email_input);
        MobileNumber = (EditText) findViewById(R.id.mobilenumber);

        showAllUserData();

        loginBtn_loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateData(view);
            }
        });
    }

    private void showAllUserData() {
        Intent intent = getIntent();
        user_username = intent.getStringExtra("username");
        user_email = intent.getStringExtra("email");
        user_phone = intent.getStringExtra("phone");

        Log.d("UserProfileActivity", user_username);


        UsernameInp.setText(user_username);
        EmailInp.setText(user_email);
        MobileNumber.setText(user_phone);
    }

    public void UpdateData(View view) {
        if (isUsernameChanged() || isEmailChanged()) {
            Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Data is same", Toast.LENGTH_SHORT).show();

        }
    }


    private boolean isEmailChanged() {
        if (!user_email.equals(EmailInp.getText().toString())) {
            reference.child(user_phone).child("email").setValue(EmailInp.getText().toString());
            user_phone=EmailInp.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isUsernameChanged() {
        if (!user_username.equals(UsernameInp.getText().toString())) {
            reference.child(user_phone).child("username").setValue(UsernameInp.getText().toString());
            user_username= UsernameInp.getText().toString();
            return true;
        } else {
            return false;
        }
    }

}