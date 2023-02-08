package com.example.wheelnavigator.Recommended;


import android.Manifest;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Location;


import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.wheelnavigator.CountributeActivity;
import com.example.wheelnavigator.PlacePagePackage.PlacePage;
import com.example.wheelnavigator.R;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.location.LocationRequest;
import java.util.ArrayList;
import java.util.Locale;

public class RecommendedActivity extends AppCompatActivity implements PlaceSelectListener {
    private RecyclerView recyclerView;
    ArrayList<PlaceDataModle> list;
    private DatabaseReference Ref;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private PlaceAdapter placeAdapter;
    private FusedLocationProviderClient fusedLocationClient;
    double dis;
    double  wayLatitude;
    double   wayLongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommended_fragment);

        recyclerView = findViewById(R.id.PlaceRecyclerView);
        Ref = FirebaseDatabase.getInstance().getReference("Place Requests");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeAdapter = new PlaceAdapter(this, list, this);
        recyclerView.setAdapter(placeAdapter);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
           wayLatitude = extras.getDouble("Lat");
           wayLongitude = extras.getDouble("Lng");

        }


       Toast.makeText(RecommendedActivity.this, wayLatitude + " + " + wayLongitude, Toast.LENGTH_LONG).show();



        Ref.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (snapshot.exists()) {
                        PlaceDataModle place = dataSnapshot.getValue(PlaceDataModle.class);


                        if (place.Approved == true)
                            list.add(place);
                        dis = distance(place.getPlaceLat(), place.getPlaceLng() , wayLatitude , wayLongitude);

                        place.setDistance(Double.parseDouble(new DecimalFormat("##.##").format(dis)) );
                    }
                }
                placeAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });













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


    @Override
    public void onItemClicked(PlaceDataModle placeDataModle) {
        Intent intent = new Intent(RecommendedActivity.this, PlacePage.class);
        intent.putExtra("Crn", placeDataModle.getCrn());
        startActivity(intent);

    }
}