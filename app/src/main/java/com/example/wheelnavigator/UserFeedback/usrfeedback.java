package com.example.wheelnavigator.UserFeedback;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheelnavigator.MainActivity;
import com.example.wheelnavigator.PlacePagePackage.PlacePage;
import com.example.wheelnavigator.R;
import com.example.wheelnavigator.Recommended.RecommendedActivity;
import com.example.wheelnavigator.Registration.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class usrfeedback extends AppCompatActivity {
    private DatabaseReference Ref = FirebaseDatabase.getInstance().getReference("UserFeedbacks");
    private DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference("users");
    private FirebaseAuth Auth =FirebaseAuth.getInstance();;
    private EditText FeedbackText;
    private RatingBar ratingBar;
    private TextView ratingTextvalue;
    private Button post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usrfeedback);

        ratingBar = findViewById(R.id.userRatingBar);
       FeedbackText = findViewById(R.id.DescriptionOfServices);
       ratingTextvalue =findViewById(R.id.ratingTextValue);
       post = findViewById(R.id.PostFeedBack);

      ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
          @Override
          public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
              if(v >= 0 && v < 2.0){
                  ratingTextvalue.setText("Bad");
                  ratingTextvalue.setTextColor(getResources().getColor(R.color.Red));
              }
              if(v >= 2.0 && v < 4.0){
                  ratingTextvalue.setText("Medium");
                  ratingTextvalue.setTextColor(getResources().getColor(R.color.Orange));
              }
              if(v >= 4.0 && v <= 5.0){
                  ratingTextvalue.setText("Excellent");
                  ratingTextvalue.setTextColor(getResources().getColor(R.color.Green));
              }
          }
      });


      post.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(FeedbackText.getText().toString().isEmpty() == true || ratingBar.getRating() == 0 )
                  Toast.makeText(usrfeedback.this , "Please write your feedback ", Toast.LENGTH_SHORT).show();
              else {
                  CreateUsrFeedback(FeedbackText.getText().toString(), ratingBar.getRating());

              }

          }
      });




    }



    private void CreateUsrFeedback (String FeedbackTxt , float RatingValue){
        DatabaseReference mRef = Ref.child(UUID.randomUUID().toString());
        mRef.child("Feedbacktxt").setValue(FeedbackTxt);
        mRef.child("RatingValue").setValue(RatingValue);
        mRef.child("Uid").setValue(Auth.getUid());
        mRef.child("Crn").setValue(GetplaceCrn());


                UsersRef.child(Auth.getUid()).child("Username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mRef.child("Username").setValue(snapshot.getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



         UsersRef.child(Auth.getUid()).child("Disability").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 mRef.child("Disability").setValue(Boolean.valueOf(snapshot.getValue().toString()));
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });


        Toast.makeText(this , "Feedback has been Sent", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(usrfeedback.this , RecommendedActivity.class));

    }


    private String GetplaceCrn(){
        String value = "1";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("Crn");
        }
       return  value;
    }




    @Override
        public void onStart() {
            super.onStart();
            FirebaseUser currentUser = Auth.getCurrentUser();
            if(currentUser == null){
                startActivity(new Intent(usrfeedback.this, Login.class));
            }


        }


    }
