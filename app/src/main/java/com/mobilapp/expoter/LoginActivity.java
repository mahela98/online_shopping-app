package com.mobilapp.expoter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobilapp.expoter.Controller.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private EditText PhoneInp, PasswordInp;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(getApplicationContext());


        MaterialButton loginBtn_loginPage = (MaterialButton) findViewById(R.id.loginBtn);
        PhoneInp = (EditText) findViewById(R.id.MobileNbrLI);
        PasswordInp = (EditText) findViewById(R.id.passwordLI);
        TextView registerPageLink = (TextView) findViewById(R.id.registerPageLink);

        registerPageLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        loginBtn_loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = PhoneInp.getText().toString();
                String password = PasswordInp.getText().toString();
                loginUser(phone, password);
            }
        });


    }

    public void loginUser(String userEnteredPhone, String userEnteredPassword) {
        DatabaseReference reference = FirebaseDatabase
                .getInstance("https://expoter-a07aa-default-rtdb.firebaseio.com/")
                .getReference("users");

      //  Log.d("username", userEnteredUsername);
        Query checkUser = reference.orderByChild("phone").equalTo(userEnteredPhone);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String passwordFromDB = dataSnapshot.child(userEnteredPhone).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredPassword)) {

                        sessionManager.createLoginSession(userEnteredPhone, userEnteredPassword);

                        Log.d("intent", "user exits");

                        String usernameFromDB = dataSnapshot.child(userEnteredPhone).child("username").getValue(String.class);
                  //      String emailFromDB = dataSnapshot.child(userEnteredPhone).child("email").getValue(String.class);
                   //     String phoneFromDB = dataSnapshot.child(userEnteredPhone).child("phone").getValue(String.class);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);



                        Log.d("intent", usernameFromDB);

                        // intent.putExtra("password", passwordFromDB);
                        startActivity(intent);

                    } else {
                        Log.d("", "Wrong Password");

                    }
                } else {
                    Log.d("", "No such user exist");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}