package com.example.wheelnavigator.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheelnavigator.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class viewRequest extends AppCompatActivity {
      TextView Name, Email,Telephone, DoS, Crn ,  Approval;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Place Requests");
    private DatabaseReference ref;
    Button viewBtn , ApproveBtn , DeclineBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        Name = findViewById(R.id.PlaceName);
        Email = findViewById(R.id.PlaceEmail);
        Telephone = findViewById(R.id.PlaceTelephone);
        DoS = findViewById(R.id.DescriptionOfServices);
        Crn = findViewById(R.id.PlaceCrn);
        Approval = findViewById(R.id.Approval);


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

        ref.child("Approved").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean AprovalStatus = (Boolean) snapshot.getValue();
                Approval.setText(AprovalStatus.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        viewBtn = findViewById(R.id.ViewImg);

        String finalValue = value;
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(viewRequest.this, Showimages.class);
                i.putExtra("Crn", finalValue);
                startActivity(i);
            }
        });

        ApproveBtn = findViewById(R.id.ApproveBtn);
        DeclineBtn  = findViewById(R.id.DeclineBtn);

        ApproveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child("Approved").setValue(true);
                Toast.makeText(viewRequest.this , "Request has been Approved", Toast.LENGTH_SHORT).show();
            }
        });

        DeclineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child("Approved").setValue(false);
                Toast.makeText(viewRequest.this , "Request has been Declined", Toast.LENGTH_SHORT).show();
            }
        });






    }
}