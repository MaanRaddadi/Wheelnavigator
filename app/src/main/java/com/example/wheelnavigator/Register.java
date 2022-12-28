package com.example.wheelnavigator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();




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




                      mDatabase.child("users").child(Unametxt).child("Email").setValue(Emailtxt);
                      mDatabase.child("users").child(Unametxt).child("Password").setValue(Passtxt);
                      mDatabase.child("users").child(Unametxt).child("Disability").setValue(Yestxt);



                  }


          });

    }


    }
