package com.example.wheelnavigator.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.wheelnavigator.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RequestsPage extends AppCompatActivity {
    ListView ReqList;

    ArrayList<String> arrayList = new ArrayList<>();

    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference CrnRef = rootRef.child("Place Requests");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_page);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RequestsPage.this, android.R.layout.simple_list_item_1, arrayList);

        ReqList = (ListView) findViewById(R.id.Requestlist);
        ReqList.setAdapter(arrayAdapter);





            CrnRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String Crn = snapshot.child("Crn").getValue(String.class);
                        arrayList.add(Crn);
                        arrayAdapter.notifyDataSetChanged();
                    }



                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            ReqList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String mCrn = adapterView.getItemAtPosition(i).toString();
                    Intent intent = new Intent(RequestsPage.this, viewRequest.class);
                    intent.putExtra("Crn", mCrn);
                    startActivity(intent);

                }
            });

        }
    }

