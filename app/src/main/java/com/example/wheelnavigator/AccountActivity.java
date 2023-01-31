package com.example.wheelnavigator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wheelnavigator.Registration.Login;
import com.example.wheelnavigator.UserFeedback.usrFeedbackDataModel;
import com.example.wheelnavigator.UserFeedback.usrfeedbackadapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {
    private FirebaseAuth Auth = FirebaseAuth.getInstance();
    private FirebaseUser mCurrentUser = Auth.getCurrentUser(); ;
private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
private TextView AccountUsername;
private TextView AccountEmail;
private DatabaseReference ref;
private Button Signout ;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);



        AccountUsername= findViewById(R.id.AccountUsrname);
        AccountEmail = findViewById(R.id.AccountEmail);

        ref=mDatabase.child(mCurrentUser.getUid());

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



    }



    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = Auth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(AccountActivity.this, Login.class));
        }


    }

}
