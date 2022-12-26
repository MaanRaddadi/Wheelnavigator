package com.example.wheelnavigator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private EditText EditText1;
    private EditText EditText2;
    private EditText EditText3;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private Button RegisterButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        mDatabase = FirebaseDatabase.getInstance().getReference();


        EditText EditText1;
        EditText EditText2;
        EditText EditText3;
        CheckBox checkBox1;
        CheckBox checkBox2;


        EditText1= findViewById(R.id.editTextUsername);
        EditText2= findViewById(R.id.EditTextEmail);
        EditText3 = findViewById(R.id.editTextPassword);
        checkBox1 = findViewById(R.id.checkYes);
        checkBox2 = findViewById(R.id.checkNo);

          Button btn = findViewById(R.id.regbutton);
          btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  sendData();
              }
          });

    }
    private void writeNewUser() {
        User user = new User (EditText1.getText().toString(),EditText2.getText().toString(), EditText3.getText().toString(), checkBox1.isChecked());

        mDatabase.child("users").child(user.getUserid()).setValue(user);
    }

    public void sendData() {
        writeNewUser();
    }
    }
