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
    private EditText Username;
    private EditText Email;
    private EditText Password;
    private CheckBox Yes;
    private CheckBox No;
    private Button RegisterButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        Username= findViewById(R.id.editTextUsername);
        Email= findViewById(R.id.EditTextEmail);
        Password = findViewById(R.id.editTextPassword);
        Yes = findViewById(R.id.checkYes);
        No = findViewById(R.id.checkNo);

          RegisterButton = findViewById(R.id.regbutton);
          RegisterButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  String Unametxt = Username.getText().toString();
                  String Emailtxt = Email.getText().toString();
                  String Passtxt = Password.getText().toString();
                  boolean Yestxt = Yes.isChecked();

                  if(Unametxt.isEmpty() || Emailtxt.isEmpty() || Passtxt.isEmpty()){
                      Toast.makeText(Register.this,"Please fill all the fields", Toast.LENGTH_SHORT).show();
                  }
                  else{
                      mDatabase.child("users").child("Email").setValue(Emailtxt);
                      mDatabase.child("users").child("Password").setValue(Passtxt);
                      mDatabase.child("users").child("Disability").setValue(Yestxt);



                  }

              }
          });

    }


    }
