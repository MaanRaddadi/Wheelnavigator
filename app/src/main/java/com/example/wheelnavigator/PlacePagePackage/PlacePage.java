package com.example.wheelnavigator.PlacePagePackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.wifi.rtt.WifiRttManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wheelnavigator.Admin.ImgAdapter;
import com.example.wheelnavigator.Admin.ImgModel;
import com.example.wheelnavigator.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PlacePage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<ImgModel> list;
    private TextView PlaceName;
    private ImageView PlaceLogo;
    private ImgAdapter adapter;
    private Button WriteFeedback;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Place Requests");
    private DatabaseReference ref, Imgref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_page);


        String value = "1";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("Crn");
        }


        WriteFeedback = findViewById(R.id.WriteFeedBack);
        PlaceName = findViewById(R.id.PagePlaceName);
        PlaceLogo = findViewById(R.id.PagePlaceLogo);




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

        WriteFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlacePage.this , usrfeedback.class));
            }
        });



                recyclerView = findViewById(R.id.PicRecyclerview);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));



            list = new ArrayList<>();
            adapter = new ImgAdapter(this , list);
        recyclerView.setAdapter(adapter);



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
}
}






