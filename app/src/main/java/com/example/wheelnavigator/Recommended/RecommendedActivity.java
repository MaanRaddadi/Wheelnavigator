package com.example.wheelnavigator.Recommended;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheelnavigator.Admin.RequestsPage;
import com.example.wheelnavigator.Admin.viewRequest;
import com.example.wheelnavigator.PlacePagePackage.PlacePage;
import com.example.wheelnavigator.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecommendedActivity extends AppCompatActivity implements PlaceSelectListener{
     private RecyclerView recyclerView;
     ArrayList <PlaceDataModle> list ;
     private DatabaseReference Ref;

     private PlaceAdapter placeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommended_fragment);

        recyclerView =findViewById(R.id.PlaceRecyclerView);
        Ref = FirebaseDatabase.getInstance().getReference("Place Requests");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeAdapter = new PlaceAdapter(this , list , this);
        recyclerView.setAdapter(placeAdapter);

        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if (snapshot.exists()) {
                                PlaceDataModle place = dataSnapshot.getValue(PlaceDataModle.class);

                                if(place.Approved == true)
                                    list.add(place);
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