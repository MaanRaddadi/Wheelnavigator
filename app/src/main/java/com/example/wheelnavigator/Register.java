package com.example.wheelnavigator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth Auth ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        final EditText Username;
        final EditText Email;
        final EditText Password;
        final CheckBox Yes;
        final CheckBox No;
        final Button RegisterButton;

        Username= findViewById(R.id.editTextUsername);
        Email= findViewById(R.id.EditTextEmail);
        Password = findViewById(R.id.editTextPassword);
        Yes = findViewById(R.id.checkYes);
        No = findViewById(R.id.checkNo);



          RegisterButton = findViewById(R.id.regbutton);
          RegisterButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {


                  final String Unametxt = Username.getText().toString();
                  final String Emailtxt = Email.getText().toString();
                  final String Passtxt = Password.getText().toString();
                  final boolean Yestxt = Yes.isChecked();


                  //Create User in Firebase Authntication
                  Auth = FirebaseAuth.getInstance();
                  Auth.createUserWithEmailAndPassword(Emailtxt, Passtxt).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                       Toast.makeText(Register.this, "Registration Succeeded", Toast.LENGTH_LONG).show();
                      }

                  });


                  // Store user data in firebase database
                      mDatabase.child("users").child(Unametxt).child("Email").setValue(Emailtxt);
                      mDatabase.child("users").child(Unametxt).child("Password").setValue(Passtxt);
                      mDatabase.child("users").child(Unametxt).child("Disability").setValue(Yestxt);



                  }


          });

    }


    }
