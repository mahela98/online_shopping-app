package com.mobilapp.expoter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mobilapp.expoter.Controller.SessionManager;
import com.mobilapp.expoter.models.ProductModel;
import com.mobilapp.expoter.models.UserModel;

import java.util.HashMap;

public class AddProductActivity extends AppCompatActivity {
    SessionManager sessionManager;
    private Button SelectImageBtn, createProductBtn;
    private EditText productCode, productName, productPrice, description;
    private ImageView imageView;
    private String user_username, user_email, user_phone;


    Uri imageUir;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("products");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.checkLogin();

        SelectImageBtn = findViewById(R.id.uploadImageBtn);
        createProductBtn = findViewById(R.id.saveProductBtn);
        imageView = findViewById(R.id.image);
        productCode = (EditText) findViewById(R.id.ProductCode);
        productName = (EditText) findViewById(R.id.ProductName);
        productPrice = (EditText) findViewById(R.id.ProductPrice);
        description = (EditText) findViewById(R.id.discription);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, 2);

            }
        });

        createProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUir != null && productCode != null && productName != null && productPrice != null) {
                    getUserDetails();
                    uploadToFireBase(imageUir);
                } else {
                    Toast.makeText(AddProductActivity.this, "Select an Image", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void uploadToFireBase(Uri uri) {
        StorageReference fileReff = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileReff.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileReff.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        ProductModel productModel = new ProductModel(productCode.getText().toString(), productName.getText().toString(),
                                productPrice.getText().toString(), user_phone, user_email, user_username, description.getText().toString(), uri.toString());

                        String modelId = root.push().getKey();
                        root.child(modelId).setValue(productModel);

                        Toast.makeText(AddProductActivity.this, "Uploaded successful", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                Toast.makeText(AddProductActivity.this, "on progress", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddProductActivity.this, "Faild to add", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri uuri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uuri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUir = data.getData();
            imageView.setImageURI(imageUir);

        }

    }


    private void getUserDetails() {
        DatabaseReference db_reference = FirebaseDatabase.getInstance("https://expoter-a07aa-default-rtdb.firebaseio.com/").getReference("users");

        HashMap<String, String> session_user = sessionManager.getUserDetails();
        String phone = session_user.get("phone");
        String password = session_user.get("password");

        Query checkUser = db_reference.orderByChild("phone").equalTo(phone);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String passwordFromDB = dataSnapshot.child(phone).child("password").getValue(String.class);
                    if (passwordFromDB.equals(password)) {
                        Log.d("user profile", "user exits");
                        user_username = dataSnapshot.child(phone).child("username").getValue(String.class);
                        user_email = dataSnapshot.child(phone).child("email").getValue(String.class);
                        user_phone = dataSnapshot.child(phone).child("phone").getValue(String.class);

                    } else {
                        Toast.makeText(AddProductActivity.this, "Error getting Data", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(AddProductActivity.this, "Error getting Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}