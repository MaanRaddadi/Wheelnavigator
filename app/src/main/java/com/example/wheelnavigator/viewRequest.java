package com.example.wheelnavigator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class viewRequest extends AppCompatActivity {
      TextView Name, Email,Telephone, DoS, Crn;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Place Requests");
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        Name = findViewById(R.id.PlaceName);
        Email = findViewById(R.id.PlaceEmail);
        Telephone = findViewById(R.id.PlaceTelephone);
        DoS = findViewById(R.id.DescriptionOfServices);
        Crn = findViewById(R.id.PlaceCrn);

        String value = "1";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
           value = extras.getString("Crn");
        }

       ref = mDatabase.child(value);
       ref.child("Name").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               Name.setText(snapshot.getValue().toString());
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

        ref.child("Telephone Number").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Telephone.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Crn.setText(value);

        ref.child("Email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Email.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref.child("Details of Services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DoS.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });












    }
}