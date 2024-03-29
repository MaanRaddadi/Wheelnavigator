package com.example.wheelnavigator.PlacePagePackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wheelnavigator.Admin.ImgAdapter;
import com.example.wheelnavigator.Admin.ImgModel;
import com.example.wheelnavigator.UserFeedback.usrFeedbackDataModel;
import com.example.wheelnavigator.UserFeedback.usrfeedback;
import com.example.wheelnavigator.R;
import com.example.wheelnavigator.UserFeedback.usrfeedbackadapter;
import com.example.wheelnavigator.usrfeedbackpage;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Locale;

public class PlacePage extends AppCompatActivity implements OnMapReadyCallback {
    private RecyclerView recyclerView , feedbackrecyclerview;
    private ArrayList<usrFeedbackDataModel> feedbacklist;
    private ArrayList<ImgModel> list;
    private usrfeedbackadapter feedbackadapter;
    private TextView PlaceName , Ratingvlauetxt , ServicesDetails;
    private ImageView PlaceLogo , applicationRatingIcon, navigationBtn;;
    private ImgAdapter adapter;
    private Button WriteFeedback , ViewFeedbacks ;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Place Requests");
    private DatabaseReference feedbackRef = FirebaseDatabase.getInstance().getReference("UserFeedbacks");
    private DatabaseReference ref, Imgref;
    private RatingBar usrRatingbar;
    private MapView mapView;
    @SuppressLint({"SuspiciousIndentation"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_page);

        //Catch Crn Value from another Class
        String value = "1";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("Crn");
        }

        navigationBtn = findViewById(R.id.NavigationBtn);
        WriteFeedback = findViewById(R.id.WriteFeedBack);
        PlaceName = findViewById(R.id.PagePlaceName);
        PlaceLogo = findViewById(R.id.PagePlaceLogo);

       //set ref to "Place Requests" -----> "Crn" for the choosen place
        ref =  mDatabase.child(value);






        ref.child("Name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PlaceName.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref.child("imageUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Glide.with(PlacePage.this).load(snapshot.getValue()).into(PlaceLogo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
      //Send place Crn to the user feedback Activity
        String finalValue = value;
        WriteFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlacePage.this, usrfeedback.class);
                intent.putExtra("Crn", finalValue);
                startActivity(intent);

            }
        });

        ServicesDetails = findViewById(R.id.ServicesDetails);

        ref.child("Details of Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ServicesDetails.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//Place page Recycler View
                recyclerView = findViewById(R.id.PicRecyclerview);
        recyclerView.setLayoutManager(  new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            list = new ArrayList<>();
            adapter = new ImgAdapter(this , list);
        recyclerView.setAdapter(adapter);


//Loading place pictures from firebase
    Imgref= mDatabase.child(value).child("Pictures");

            Imgref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                ImgModel model = dataSnapshot.getValue(ImgModel.class);
                list.add(model);
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

        //Setting the application rating icon inside the place page
        applicationRatingIcon = findViewById(R.id.ApplicationRatingIcon);

        ref.child("ApplicationRatingScore").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    if (Integer.valueOf(snapshot.getValue().toString()) >= 27) {
                        applicationRatingIcon.setImageResource(R.drawable.ic_baseline_accessible_24_green);
                    }
                    if(Integer.valueOf(snapshot.getValue().toString()) >= 23 && Integer.valueOf(snapshot.getValue().toString()) < 27){
                          applicationRatingIcon.setImageResource(R.drawable.baseline_accessible_24_darkgreen);
                    }
                    if (Integer.valueOf(snapshot.getValue().toString()) >= 16 && Integer.valueOf(snapshot.getValue().toString()) < 23) {
                        applicationRatingIcon.setImageResource(R.drawable.ic_baseline_accessible_24_yellew);
                    }
                    if (Integer.valueOf(snapshot.getValue().toString()) >= 6 && Integer.valueOf(snapshot.getValue().toString()) < 16) {
                        applicationRatingIcon.setImageResource(R.drawable.ic_baseline_accessible_24_orange);
                    }
                    if (Integer.valueOf(snapshot.getValue().toString()) < 6) {
                        applicationRatingIcon.setImageResource(R.drawable.ic_baseline_accessible_24_red);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ViewFeedbacks = findViewById(R.id.ViewFeedbacks);
        String finalValue1 = value;
        ViewFeedbacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlacePage.this, usrfeedbackpage.class);
                intent.putExtra("Crn", finalValue1);
                startActivity(intent);
            }
        });

  usrRatingbar = findViewById(R.id.ratingBar);
  Ratingvlauetxt = findViewById(R.id.RatingValue);
        CalcFeedbackAvg(value);

        mapView = findViewById(R.id.map);
        mapView.getMapAsync( this);
        mapView.onCreate(savedInstanceState);






}

private void CalcFeedbackAvg (String Crn) {
    ArrayList<usrFeedbackDataModel> avg = new ArrayList<usrFeedbackDataModel>();



        feedbackRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Float sum = 0.0f;
                avg.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (snapshot.exists()) {

                        usrFeedbackDataModel feedback = dataSnapshot.getValue(usrFeedbackDataModel.class);
                        if (feedback.getCrn() != null && Crn != null) {
                            if (feedback.getCrn().equalsIgnoreCase(Crn)) {
                                Float rating = Float.valueOf(feedback.getRatingValue());


                                sum = sum + rating;

                                avg.add(feedback);

                            }
                        }
                    }

                }
                float totalAvg = sum / avg.size();
                usrRatingbar.setRating(totalAvg);
                Ratingvlauetxt.setText(new DecimalFormat("##.##").format(totalAvg));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });








}

    @Override
    protected void onStop() {
        mapView.onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        mapView.onStart();
        super.onStart();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        mapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

             ref.child("PlaceLat").addValueEventListener(new ValueEventListener() {
    @Override
           public void onDataChange(@NonNull DataSnapshot napshot) {
                ref.child("PlaceLng").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                   Double.valueOf(napshot.getValue().toString());
                        LatLng sydney = new LatLng(Double.valueOf(napshot.getValue().toString()), Double.valueOf(snapshot.getValue().toString()));
                        Marker marker = googleMap.addMarker(new MarkerOptions().draggable(true).position(sydney));
                        float zoomLevel = 18.0f; //This goes up to 21

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));

                        navigationBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String uri = "http://maps.google.com/maps?daddr=" + Double.valueOf(napshot.getValue().toString()) + "," + Double.valueOf(snapshot.getValue().toString()) + " (" + "Where the party is at" + ")";
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                intent.setPackage("com.google.android.apps.maps");
                                startActivity(intent);

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
             }

          @Override
           public void onCancelled(@NonNull DatabaseError error) {

            }
          });


    }
}









