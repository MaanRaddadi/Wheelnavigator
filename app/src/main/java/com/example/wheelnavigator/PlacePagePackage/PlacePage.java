package com.example.wheelnavigator.PlacePagePackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wheelnavigator.Admin.ImgAdapter;
import com.example.wheelnavigator.Admin.ImgModel;
import com.example.wheelnavigator.Recommended.PlaceDataModle;
import com.example.wheelnavigator.UserFeedback.usrFeedbackDataModel;
import com.example.wheelnavigator.UserFeedback.usrfeedback;
import com.example.wheelnavigator.R;
import com.example.wheelnavigator.UserFeedback.usrfeedbackadapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlacePage extends AppCompatActivity {
    private RecyclerView recyclerView , feedbackrecyclerview;
    private ArrayList<usrFeedbackDataModel> feedbacklist;
    private ArrayList<ImgModel> list;
    private usrfeedbackadapter feedbackadapter;
    private TextView PlaceName;
    private ImageView PlaceLogo , applicationRatingIcon;
    private ImgAdapter adapter;
    private Button WriteFeedback;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Place Requests");
    private DatabaseReference feedbackRef = FirebaseDatabase.getInstance().getReference("UserFeedbacks");
    private DatabaseReference ref, Imgref;
    @SuppressLint("SuspiciousIndentation")
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
                if(Integer.valueOf(snapshot.getValue().toString()) >= 30){
                    applicationRatingIcon.setImageResource(R.drawable.ic_baseline_accessible_24_green);
                }
                if(Integer.valueOf(snapshot.getValue().toString()) >= 20&& Integer.valueOf(snapshot.getValue().toString()) < 30){
                    applicationRatingIcon.setImageResource(R.drawable.ic_baseline_accessible_24_yellew);
                }
                if(Integer.valueOf(snapshot.getValue().toString()) >= 10 && Integer.valueOf(snapshot.getValue().toString()) < 20){
                    applicationRatingIcon.setImageResource(R.drawable.ic_baseline_accessible_24_orange);
                }
                if(Integer.valueOf(snapshot.getValue().toString()) < 10 ){
                    applicationRatingIcon.setImageResource(R.drawable.ic_baseline_accessible_24_red);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        feedbackrecyclerview = findViewById(R.id.feedbackRecyclerview);

        feedbackrecyclerview.setLayoutManager( new LinearLayoutManager(this));
        feedbacklist = new ArrayList<>();
        feedbackadapter = new usrfeedbackadapter(this , feedbacklist);
        feedbackrecyclerview.setAdapter(feedbackadapter);

        feedbackRef.child(value).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if (snapshot.exists()) {
                        usrFeedbackDataModel feedback = dataSnapshot.getValue(usrFeedbackDataModel.class);
                        feedbacklist.add(feedback);
                    }
                }
                feedbackadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




}
}






