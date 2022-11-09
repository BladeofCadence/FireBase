package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    // Check if user is signed in (non-null) and update UI accordingly.
//    currentUser = mAuth.getCurrentUser();
//
//
//    if (currentUser == null)
//    {
//        // nobody is logged in... we should probably show some
//        //  buttons for "Login" and "Register" that will call our cool new Activities
//    }
//    else
//    {
//        // we have a user already logged in... cool.  What do we do with that?
//    }

//sign out
//FirebaseAuth.getInstance().signOut();

}