package com.example.wheelnavigator.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.wheelnavigator.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.widget.Toast;

public class Create_Rating extends AppCompatActivity {
            int  sumscore ;
            TextView Scoretxt;
            Button ScoreBtn , SendRating;
            Spinner RS1 , RS2 ,RS3 ,RS4 , RS5 , RS6,RS7 ,  RS8 , RS9;

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Place Requests");

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.createrating);
                String value = "1";
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    value = extras.getString("Crn");
                }
                RS1 = findViewById(R.id.RS1);
                RS2 = findViewById(R.id.RS2);
                RS3 = findViewById(R.id.RS3);
                RS4 = findViewById(R.id.RS4);
                RS5 = findViewById(R.id.RS5);
                RS6 = findViewById(R.id.RS6);
                RS7 = findViewById(R.id.RS7);
                RS8 = findViewById(R.id.RS8);
                RS9 = findViewById(R.id.RS9);
                ScoreBtn = findViewById(R.id.showscore);
                Scoretxt = findViewById(R.id.Overall);
                SendRating = findViewById(R.id.SendRating);


                ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                        .createFromResource(this, R.array.RatingArray,
                                android.R.layout.simple_spinner_item);
                ArrayAdapter<CharSequence> staticAdapter2 = ArrayAdapter
                        .createFromResource(this, R.array.RatingArray2,
                                android.R.layout.simple_spinner_item);

                staticAdapter2
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                staticAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


               RS1.setAdapter(staticAdapter);
                RS2.setAdapter(staticAdapter2);
                RS3.setAdapter(staticAdapter2);
                RS4.setAdapter(staticAdapter);
                RS5.setAdapter(staticAdapter2);
                RS6.setAdapter(staticAdapter2);
                RS7.setAdapter(staticAdapter2);
                RS8.setAdapter(staticAdapter2);
                RS9.setAdapter(staticAdapter2);











                ScoreBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sumscore = 0 ;
                        sumscore = Integer.valueOf(RS1.getSelectedItem().toString()) + Integer.valueOf(RS2.getSelectedItem().toString())+ Integer.valueOf(RS3.getSelectedItem().toString())+ Integer.valueOf(RS4.getSelectedItem().toString())+ Integer.valueOf(RS5.getSelectedItem().toString())+ Integer.valueOf(RS6.getSelectedItem().toString())+ Integer.valueOf(RS7.getSelectedItem().toString())+ Integer.valueOf(RS8.getSelectedItem().toString())+ Integer.valueOf(RS9.getSelectedItem().toString()) ;

                        Scoretxt.setText(String.valueOf(sumscore));

                    }
                });


                String finalValue = value;
                SendRating.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDatabase.child(finalValue).child("ApplicationRatingScore").setValue(sumscore);
                        Toast.makeText(Create_Rating.this , "Rating has been Sent ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Create_Rating.this , AdminPanel.class));
                    }
                });









            }




        }

