package com.example.wheelnavigator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class ExploreActivity extends AppCompatActivity {
    private FirebaseAuth Auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore_fragment);
        Auth.signOut();
    }
}