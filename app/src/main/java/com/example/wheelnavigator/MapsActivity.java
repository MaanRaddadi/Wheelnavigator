package com.example.wheelnavigator;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;


import android.content.Intent;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wheelnavigator.Admin.Create_Rating;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.wheelnavigator.databinding.ActivityMapsBinding;

import java.util.Set;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private LocationRequest locationRequest;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ImageView ImgMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImgMarker = findViewById(R.id.marker);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng sydney = new LatLng(24.467842, 39.611347);
        Marker marker = mMap.addMarker(new MarkerOptions().draggable(true).position(sydney));
        float zoomLevel = 16.0f; //This goes up to 21

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));

        ImgMarker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mMap.clear();
                Marker placeLocationMarker = mMap.addMarker(new MarkerOptions().position(mMap.getCameraPosition().target));
                Intent intent = new Intent();
                double Data1 = placeLocationMarker.getPosition().latitude;
                double Data2 = placeLocationMarker.getPosition().longitude;
                intent.putExtra("Data1", Data1);
                intent.putExtra("Data2", Data2);
                setResult(3 , intent);
                onBackPressed();
            }


        });


    }
}