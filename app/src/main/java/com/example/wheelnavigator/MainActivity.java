package com.example.wheelnavigator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wheelnavigator.Admin.AdminPanel;
import com.example.wheelnavigator.Recommended.RecommendedActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth Auth = FirebaseAuth.getInstance();

    double  wayLatitude ;
    double   wayLongitude;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

                BottomNavigationView bottomNavigationView ;
               bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()) {
                   case R.id.Recommended: {
                       Intent intent = new Intent(getApplicationContext(), RecommendedActivity.class);
                       intent.putExtra("Lat" , wayLatitude);
                       intent.putExtra("Lng", wayLongitude);
                       startActivity(intent);
                       overridePendingTransition(0,0);
                       return true;
                   }
                   case R.id.Explore:
                       startActivity(new Intent(getApplicationContext(), ExploreActivity.class));
                       overridePendingTransition(0,0);
                       return true;
                   case R.id.Contribute:
                       startActivity(new Intent(getApplicationContext(), CountributeActivity.class));
                       overridePendingTransition(0,0);
                       return true;
                   case R.id.Account:
                       startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                       overridePendingTransition(0,0);
                       return true;

               }
               return false;
           }
       });









    }




    }












