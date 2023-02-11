package com.example.wheelnavigator.Recommended;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Location;


import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.wheelnavigator.AccountActivity;
import com.example.wheelnavigator.CountributeActivity;
import com.example.wheelnavigator.ExploreActivity;
import com.example.wheelnavigator.PlacePagePackage.PlacePage;
import com.example.wheelnavigator.R;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.location.LocationRequest;
import java.util.ArrayList;
import java.util.Locale;

public class RecommendedActivity extends AppCompatActivity  implements PlaceSelectListener {
    private RecyclerView recyclerView;
    ArrayList<PlaceDataModle> list;
    private DatabaseReference Ref;
    double  wayLatitude ;
    double   wayLongitude;
    private FusedLocationProviderClient fusedLocationClient;

    private PlaceAdapter placeAdapter;

    double dis;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommended_fragment);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        recyclerView = findViewById(R.id.PlaceRecyclerView);
        Ref = FirebaseDatabase.getInstance().getReference("Place Requests");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeAdapter = new PlaceAdapter(this, list, this);
        recyclerView.setAdapter(placeAdapter);



        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);


            return;
        }



            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location != null){
                        wayLatitude = location.getLatitude();
                        wayLongitude= location.getLongitude();
                        if(wayLongitude != 0.0 && wayLatitude != 0.0){
                            fillrecyclerview();

                        }

                    }



                }
            });


        progressDialog.dismiss();


        BottomNavigationView bottomNavigationView ;
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Recommended);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Recommended: {

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






     progressDialog.dismiss();





    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist * 1.609344);

    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

public void fillrecyclerview() {

        Ref.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (snapshot.exists()) {
                        PlaceDataModle place = dataSnapshot.getValue(PlaceDataModle.class);


                        if (place.Approved == true) {
                        if(place.getApplicationRatingScore() >= 16) {
                            dis = distance(place.getPlaceLat(), place.getPlaceLng(), wayLatitude, wayLongitude);
                            place.setDistance(Double.parseDouble((new DecimalFormat("##.##").format(dis))));
                            list.add(place);
                        }
                        }
                    }
                }
                placeAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }






    @Override
    public void onItemClicked(PlaceDataModle placeDataModle) {
        Intent intent = new Intent(RecommendedActivity.this, PlacePage.class);
        intent.putExtra("Crn", placeDataModle.getCrn());

        startActivity(intent);

    }
}