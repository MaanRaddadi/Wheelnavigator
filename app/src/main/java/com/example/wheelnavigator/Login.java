package com.example.wheelnavigator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        final EditText LoginUsr = findViewById(R.id.LoginUsername);
        final EditText LoginPass= findViewById(R.id.LoginPassword);
        final Button LoginBtn = findViewById(R.id.LoginButton);
        final Button Regnow = findViewById(R.id.RegNow);


        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String LoginUsrTxt = LoginUsr.getText().toString();
                final String LoginPassTxt = LoginPass.getText().toString();

                if(LoginUsrTxt.isEmpty() || LoginPassTxt.isEmpty()){
                    Toast.makeText(Login.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
                }

                else{
                mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // Check if entered username exist in the database
                          if(snapshot.hasChild(LoginUsrTxt)){

                                 final String getpassword = snapshot.child(LoginUsrTxt).child("Password").getValue(String.class);

                                 if(getpassword.equals(LoginPassTxt)){
                                     Toast.makeText(Login.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                                     startActivity(new Intent(Login.this, Register.class));
                                 }
                                 else{
                                     Toast.makeText(Login.this, "Check Entered Data and try again ", Toast.LENGTH_SHORT).show();
                                 }
                          }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }


                });

                }
            }



        });

        Regnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

    }
}