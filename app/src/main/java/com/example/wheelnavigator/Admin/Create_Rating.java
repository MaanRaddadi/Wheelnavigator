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
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.widget.Toast;

public class Create_Rating extends AppCompatActivity {
            int  score ;
            TextView tvNumbers1,tvNumbers2,tvNumbers3,tvNumbers4,tvNumbers5,tvNumbers6,tvNumbers7,tvNumbers8 , OverallScore;
            Button ScoreBtn , SendRating;
            NumberPicker numberPicker1,numberPicker2,numberPicker3,numberPicker4,numberPicker5,numberPicker6,numberPicker7,numberPicker8;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Place Requests");
    private DatabaseReference Ref ;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.createrating);

                tvNumbers1=findViewById(R.id.tvNumbers1);
                numberPicker1=findViewById(R.id.numberPicker1);
                numberPicker1.setMinValue(0);
                numberPicker1.setMaxValue(5);
                tvNumbers1.setText(String.format("parking: %S",numberPicker1.getValue()));

                numberPicker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        tvNumbers1.setText(String.format("parking: %s",newVal));
                    }
                });

                tvNumbers2=findViewById(R.id.tvNumbers2);
                numberPicker2=findViewById(R.id.numberPicker2);
                numberPicker2.setMinValue(0);
                numberPicker2.setMaxValue(5);
                tvNumbers2.setText(String.format("Automatic opening gates: %S",numberPicker2.getValue()));

                numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        tvNumbers2.setText(String.format("Automatic opening gates: %s",newVal));
                    }
                });
                tvNumbers3=findViewById(R.id.tvNumbers3);
                numberPicker3=findViewById(R.id.numberPicker3);
                numberPicker3.setMinValue(0);
                numberPicker3.setMaxValue(5);
                tvNumbers3.setText(String.format("bathroom: %S",numberPicker3.getValue()));
                numberPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        tvNumbers3.setText(String.format("bathroom: %s",newVal));
                    }
                });
                tvNumbers4=findViewById(R.id.tvNumbers4);
                numberPicker4=findViewById(R.id.numberPicker4);
                numberPicker4.setMinValue(0);
                numberPicker4.setMaxValue(5);
                tvNumbers4.setText(String.format("elevators: %s",numberPicker4.getValue()));
                numberPicker4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        tvNumbers4.setText(String.format("elevators: %s",newVal));
                    }
                });
                tvNumbers5=findViewById(R.id.tvNumbers5);
                numberPicker5=findViewById(R.id.numberPicker5);
                numberPicker5.setMinValue(0);
                numberPicker5.setMaxValue(5);
                tvNumbers5.setText(String.format("Tables & chairs: %s",numberPicker5.getValue()));
                numberPicker5.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        tvNumbers5.setText(String.format("Tables & chairs: %s",newVal));
                    }
                });
                tvNumbers6=findViewById(R.id.tvNumbers6);
                numberPicker6=findViewById(R.id.numberPicker6);
                numberPicker6.setMinValue(0);
                numberPicker6.setMaxValue(5);
                tvNumbers6.setText(String.format("Signs: %s",numberPicker6.getValue()));
                numberPicker6.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        tvNumbers6.setText(String.format("Signs: %s",newVal));
                    }
                });
                tvNumbers7=findViewById(R.id.tvNumbers7);
                numberPicker7=findViewById(R.id.numberPicker7);
                numberPicker7.setMinValue(0);
                numberPicker7.setMaxValue(5);
                tvNumbers7.setText(String.format("Stairs Railings: %s",numberPicker7.getValue()));
                numberPicker7.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        tvNumbers7.setText(String.format("Stairs Railings: %s",newVal));
                    }
                });
                tvNumbers8=findViewById(R.id.tvNumbers8);
                numberPicker8=findViewById(R.id.numberPicker8);
                numberPicker8.setMinValue(0);
                numberPicker8.setMaxValue(5);
                tvNumbers8.setText(String.format("Hallway railings: %s",numberPicker8.getValue()));
                numberPicker8.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        tvNumbers8.setText(String.format("Hallway railings: %s",newVal));
                    }
                });
                OverallScore = findViewById(R.id.Overalltotal);
                ScoreBtn = findViewById(R.id.showscore);
                ScoreBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        score = numberPicker1.getValue() + numberPicker2.getValue()+ numberPicker3.getValue()+ numberPicker4.getValue()+ numberPicker5.getValue()+ numberPicker6.getValue()+ numberPicker7.getValue()+ numberPicker8.getValue();
                        OverallScore.setText(String.valueOf(score));
                    }
                });
                String value = "1";
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    value = extras.getString("Crn");
                }

                SendRating = findViewById(R.id.SendRating);
                Ref = mDatabase.child(value);
                SendRating.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Ref.child("ApplicationRatingScore").setValue(score);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        Toast.makeText(Create_Rating.this , "Application Rating has been sent", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Create_Rating.this , AdminPanel.class));
                    }
                });





            }


        }

