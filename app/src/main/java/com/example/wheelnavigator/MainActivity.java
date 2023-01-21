package com.example.wheelnavigator;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wheelnavigator.Admin.AdminPanel;
import com.example.wheelnavigator.Recommended.RecommendedActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth Auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

       final Button Recommended = findViewById(R.id.button1);
        final Button Explore = findViewById(R.id.button2);
        final Button Countribute = findViewById(R.id.button3);
        final Button Account = findViewById(R.id.button4);
        final Button Admin = findViewById(R.id.button5);


        Recommended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RecommendedActivity.class));
            }
        });
        Explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ExploreActivity.class));
            }
        });
        Countribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CountributeActivity.class));
            }
        });
        Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AccountActivity.class));
            }
        });

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminPanel.class));
            }
        });



    }


    }












