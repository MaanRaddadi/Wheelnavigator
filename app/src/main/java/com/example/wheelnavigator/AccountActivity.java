package com.example.wheelnavigator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheelnavigator.Admin.AdminPanel;
import com.example.wheelnavigator.Recommended.RecommendedActivity;
import com.example.wheelnavigator.Registration.Login;
import com.example.wheelnavigator.UserFeedback.usrFeedbackDataModel;
import com.example.wheelnavigator.UserFeedback.usrfeedbackadapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {

private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
private TextView AccountUsername;
private TextView AccountEmail;
private DatabaseReference ref;
private Button Signout , AdminBtn;
    private DatabaseReference feedbackRef = FirebaseDatabase.getInstance().getReference("UserFeedbacks");
private RecyclerView FeedbackRecyclerView;
    private usrfeedbackadapter feedbackadapter;
    private ArrayList<usrFeedbackDataModel> feedbacklist;
    ProgressDialog progressDialog;
    private   FirebaseAuth Auth = FirebaseAuth.getInstance();

    private String [] adminUids= {"ouLMhRAAZsZ9eN36TZMky4pw8Gp2", "YEv2GI7mkCXphXCmpNJOsCZVTDM2"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
         AdminBtn = findViewById(R.id.AdminBtn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        AccountUsername= findViewById(R.id.AccountUsrname);
        AccountEmail = findViewById(R.id.AccountEmail);
        Bundle extras = getIntent().getExtras();


        FirebaseUser mCurrentUser = Auth.getCurrentUser();

           if(mCurrentUser == null  ){
               startActivity(new Intent(AccountActivity.this, Login.class));

           }

           isAdmin();


           AdminBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   startActivity(new Intent(AccountActivity.this , AdminPanel.class));
               }
           });

        ref = mDatabase.child(mCurrentUser.getUid());

        ref.child("Username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String AccountUsernametxt = snapshot.getValue().toString();
                    AccountUsername.setText(AccountUsernametxt);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



         String AccountEmailtxt = Auth.getCurrentUser().getEmail();

        AccountEmail.setText(AccountEmailtxt);
        Signout = findViewById(R.id.signout);
        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(AccountActivity.this , "Sign out Succeeded" , Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AccountActivity.this , MainActivity.class));
            }
        });
        FeedbackRecyclerView = findViewById(R.id.usrFeedbacklist);
        FeedbackRecyclerView.setLayoutManager( new LinearLayoutManager(this));
        feedbacklist = new ArrayList<>();
        feedbackadapter = new usrfeedbackadapter(this , feedbacklist);

        FeedbackRecyclerView.setAdapter(feedbackadapter);

        feedbackRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if (snapshot.exists()) {
                        usrFeedbackDataModel feedback = dataSnapshot.getValue(usrFeedbackDataModel.class);
                        if (feedback.getUid() != null && Auth.getCurrentUser().getUid() != null) {
                            if (feedback.getUid().equalsIgnoreCase(Auth.getCurrentUser().getUid()) == true && feedback.getUid() != null) {
                                feedbacklist.add(feedback);
                            }
                        }
                    }
                    }
                feedbackadapter.notifyDataSetChanged();

                }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BottomNavigationView bottomNavigationView ;
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.Account);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Recommended: {
                        Intent intent = new Intent(getApplicationContext(), RecommendedActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    }
                    case R.id.Explore:
                        startActivity(new Intent(getApplicationContext(), ExploreActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Contribute:
                        startActivity(new Intent(getApplicationContext(), CountributeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Account:

                        return true;

                }
                return false;
            }
        });

        progressDialog.dismiss();
    }



    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = Auth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(AccountActivity.this, Login.class));

        }


    }
    private void isAdmin(){
        for(int i = 0 ; i<adminUids.length ; i++){
            if(Auth.getCurrentUser().getUid().equalsIgnoreCase(adminUids [i]) ){
                    AdminBtn.setVisibility(View.VISIBLE);
            }
        }
    }

}
