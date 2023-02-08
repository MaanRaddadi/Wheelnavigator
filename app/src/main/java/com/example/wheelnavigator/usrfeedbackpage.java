package com.example.wheelnavigator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.wheelnavigator.UserFeedback.usrFeedbackDataModel;
import com.example.wheelnavigator.UserFeedback.usrfeedbackadapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class usrfeedbackpage extends AppCompatActivity {
    RecyclerView feedbackrecyclerview;
    private usrfeedbackadapter feedbackadapter;
    private ArrayList<usrFeedbackDataModel> feedbacklist;
    private DatabaseReference feedbackRef = FirebaseDatabase.getInstance().getReference("UserFeedbacks");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usrfeedbackspage);


        String value = "1";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("Crn");
        }


        feedbackrecyclerview = findViewById(R.id.feedbackrecyclerview);

        feedbackrecyclerview.setLayoutManager( new LinearLayoutManager(this));
        feedbacklist = new ArrayList<>();
        feedbackadapter = new usrfeedbackadapter(this , feedbacklist);

        feedbackrecyclerview.setAdapter(feedbackadapter);

        String finalValue = value;

        feedbackRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if (snapshot.exists()) {
                        usrFeedbackDataModel feedback = dataSnapshot.getValue(usrFeedbackDataModel.class);


                        if (feedback.getCrn().equalsIgnoreCase(finalValue) == true ){
                            feedbacklist.add(feedback);
                        }

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