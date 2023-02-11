package com.example.wheelnavigator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wheelnavigator.PlacePagePackage.PlacePage;
import com.example.wheelnavigator.Recommended.PlaceAdapter;
import com.example.wheelnavigator.Recommended.PlaceDataModle;
import com.example.wheelnavigator.Recommended.PlaceSelectListener;
import com.example.wheelnavigator.Recommended.RecommendedActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.PrimitiveIterator;

public class ExploreActivity extends AppCompatActivity implements PlaceSelectListener {
    private RecyclerView ExploreRecyclerView;
    ArrayList<PlaceDataModle> list;
    private DatabaseReference Ref;
    private SearchView searchView;
    private PlaceAdapter placeAdapter;
    private String selectedFilter = "all";
    double  wayLatitude ;
    double   wayLongitude;
    double dis;
    private FusedLocationProviderClient fusedLocationClient;
    private ImageView RestaurantFilterIcon , PharmaFilterIcon , HospitalFilterIcon , GymFilterIcon, CoffeShopFilterIcon , MosqueFilterIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore_fragment);
        ExploreRecyclerView = findViewById(R.id.ExploreRecyclerview);
        searchView = findViewById(R.id.search_bar);

        Ref = FirebaseDatabase.getInstance().getReference("Place Requests");
        list = new ArrayList<>();
        ExploreRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeAdapter = new PlaceAdapter(this, list, this);



        ExploreRecyclerView.setAdapter(placeAdapter);


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





        BottomNavigationView bottomNavigationView ;
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Explore);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Recommended: {
                        Intent intent = new Intent(getApplicationContext(), RecommendedActivity.class);

                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    }
                    case R.id.Explore:

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






        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                RestaurantFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                PharmaFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                HospitalFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                CoffeShopFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                GymFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                MosqueFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
               Search(s);
                return false;
            }
        });


        RestaurantFilterIcon = findViewById(R.id.RestaurantFilter);
        RestaurantFilterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestaurantFilterIcon.setBackgroundColor(Color.parseColor("#FFFFFF"));

                PharmaFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                HospitalFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                CoffeShopFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                GymFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                MosqueFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                filter("Restaurant");
            }
        });
        PharmaFilterIcon = findViewById(R.id.pharmafilter);
        PharmaFilterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PharmaFilterIcon.setBackgroundColor(Color.parseColor("#FFFFFF"));

                RestaurantFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));

                HospitalFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                CoffeShopFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                GymFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                MosqueFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                filter("Pharmacy");
            }
        });
        HospitalFilterIcon = findViewById(R.id.hospitalFilter);
        HospitalFilterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HospitalFilterIcon.setBackgroundColor(Color.parseColor("#FFFFFF"));

                RestaurantFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                PharmaFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                CoffeShopFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                GymFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                MosqueFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                filter("Hospital");
            }
        });
        GymFilterIcon = findViewById(R.id.Gymfilter);
        GymFilterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GymFilterIcon.setBackgroundColor(Color.parseColor("#FFFFFF"));

                RestaurantFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                PharmaFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                HospitalFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                CoffeShopFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                MosqueFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));

                filter("Gym");

            }
        });
        CoffeShopFilterIcon= findViewById(R.id.Coffeshopfilter);
        CoffeShopFilterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CoffeShopFilterIcon.setBackgroundColor(Color.parseColor("#FFFFFF"));


                RestaurantFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                PharmaFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                HospitalFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));

                GymFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
               MosqueFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                filter("Coffee Shop");
            }
        });
        MosqueFilterIcon = findViewById(R.id.Mosquefilter);
        MosqueFilterIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MosqueFilterIcon.setBackgroundColor(Color.parseColor("#FFFFFF"));

                RestaurantFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                PharmaFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                HospitalFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
               CoffeShopFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));
               GymFilterIcon.setBackgroundColor(Color.parseColor("#00FFFFFF"));


                filter("Mosque");
            }
        });

    }


    private void Search(String placename) {
        ArrayList<PlaceDataModle> placelist = new ArrayList<>();
        for (PlaceDataModle myPlace : list) {
            if (myPlace.getName().toLowerCase(Locale.ROOT).contains(placename)) {

                placelist.add(myPlace);
            }
        }
        PlaceAdapter mPlaceAdapter = new PlaceAdapter(this, placelist, this);
        ExploreRecyclerView.setAdapter(mPlaceAdapter);

    }

    private void filter(String filter) {
        selectedFilter = filter;
        ArrayList<PlaceDataModle> filteredPlaces = new ArrayList<>();
        for (PlaceDataModle filtredplace : list) {
            if (filtredplace.getPlaceType().toLowerCase().contains(filter.toLowerCase())) {

                        filteredPlaces.add(filtredplace);
                    }

                }

        PlaceAdapter mPlaceAdapter = new PlaceAdapter(this, filteredPlaces, this);
        ExploreRecyclerView.setAdapter(mPlaceAdapter);

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


                        if (place.isApproved() == true) {

                            dis = distance(place.getPlaceLat(), place.getPlaceLng(), wayLatitude, wayLongitude);
                            place.setDistance(Double.parseDouble((new DecimalFormat("##.##").format(dis))));
                            list.add(place);

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
        public void onItemClicked (PlaceDataModle placeDataModle){
            Intent intent = new Intent(ExploreActivity.this, PlacePage.class);
            intent.putExtra("Crn", placeDataModle.getCrn());
            startActivity(intent);
        }



}