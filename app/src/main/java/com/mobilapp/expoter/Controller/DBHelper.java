package com.mobilapp.expoter.Controller;

import com.google.firebase.database.DatabaseReference;
import com.mobilapp.expoter.models.UserModel;

public  class DBHelper {

    public static boolean createUser(UserModel user, DatabaseReference reference){
        try {
            reference.child(user.getPhone()).setValue(user);
            return  true;
        }catch (Exception e){
            return false;
        }

    }
}
