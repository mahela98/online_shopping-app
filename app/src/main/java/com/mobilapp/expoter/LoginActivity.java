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

public class LoginActivity extends AppCompatActivity {

    private EditText UsernameInp, PasswordInp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MaterialButton loginBtn_loginPage = (MaterialButton) findViewById(R.id.loginBtn);
        UsernameInp = (EditText) findViewById(R.id.usernameLI);
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
                String username = UsernameInp.getText().toString();
                String password = PasswordInp.getText().toString();
                loginUser(username, password);
            }
        });


    }

    public void loginUser(String userEnteredUsername, String userEnteredPassword) {
        DatabaseReference reference = FirebaseDatabase
                .getInstance("https://expoter-a07aa-default-rtdb.firebaseio.com/")
                .getReference("users");

        Log.d("username", userEnteredUsername);
        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredPassword)) {

                        Log.d("intent", "user exits");

                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        String phoneFromDB = dataSnapshot.child(userEnteredUsername).child("phone").getValue(String.class);

                        Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phone", phoneFromDB);


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