package com.mobilapp.expoter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mobilapp.expoter.Controller.DBHelper;
import com.mobilapp.expoter.models.UserModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText EmailInp, UsernameInp, PasswordInp, RePasswordInp, MobileNumber;
    MaterialButton createAccountBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccountBtn = (MaterialButton) findViewById(R.id.createAccountBtn);
        UsernameInp = (EditText) findViewById(R.id.register_username_input);
        EmailInp = (EditText) findViewById(R.id.register_email_input);
        MobileNumber = (EditText) findViewById(R.id.mobilenumber);
        PasswordInp = (EditText) findViewById(R.id.register_password_input);
        RePasswordInp = (EditText) findViewById(R.id.register_re_password_input);
        TextView loginPageLink = (TextView) findViewById(R.id.loginPageLink);


        loginPageLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance("https://expoter-a07aa-default-rtdb.firebaseio.com/");
                reference = rootNode.getReference("users");

                String username = UsernameInp.getText().toString();
                String password = PasswordInp.getText().toString();
                String re_password = RePasswordInp.getText().toString();
                String email = EmailInp.getText().toString();
                String mobileNumber = MobileNumber.getText().toString();


           if (password.equals(re_password)){
               UserModel user = new UserModel(username, email, mobileNumber, password);

               if (DBHelper.createUser(user, reference)) {
                   Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                   startActivity(intent);
                //   Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show();
                   Log.d("","Account Created");
               } else {
                   Log.d("","Account Creation Failed");

                   //  Toast.makeText(this,"",Toast.LENGTH_LONG).show();
               }
           }


            }
        });

    }


}