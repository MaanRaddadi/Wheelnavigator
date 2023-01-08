package com.example.wheelnavigator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {
    private FirebaseAuth Auth = FirebaseAuth.getInstance();private FirebaseUser mCurrentUser = Auth.getCurrentUser(); ;
private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
private TextView AccountUsername;
private TextView AccountEmail;
private DatabaseReference ref;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);



        AccountUsername= findViewById(R.id.accountUser);
        AccountEmail = findViewById(R.id.accountEmail);

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





    }


    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = Auth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(AccountActivity.this, Login.class));
        }


    }

}
