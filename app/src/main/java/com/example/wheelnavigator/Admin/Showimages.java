package com.example.wheelnavigator.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.wheelnavigator.Admin.ImgAdapter;
import com.example.wheelnavigator.Admin.ImgModel;
import com.example.wheelnavigator.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Showimages extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ImgModel> list;

    private ImgAdapter adapter;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Place Requests");
    private DatabaseReference Ref ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimages);
        String Crn = "1";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Crn = extras.getString("Crn");

        }


            recyclerView = findViewById(R.id.ImgRecyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            list = new ArrayList<>();
            adapter = new ImgAdapter(this , list);
            recyclerView.setAdapter(adapter);



            Ref= root.child(Crn).child("Pictures");

            Ref.addValueEventListener(new ValueEventListener() {
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
        }
    }

