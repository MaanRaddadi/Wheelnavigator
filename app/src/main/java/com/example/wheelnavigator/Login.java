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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth Auth;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final Button Skip = findViewById(R.id.Skip);
        final EditText LoginEmail = findViewById(R.id.LoginEmail);
        final EditText LoginPass = findViewById(R.id.LoginPassword);
        final Button LoginBtn = findViewById(R.id.LoginButton);
        final Button Regnow = findViewById(R.id.RegNow);
        Auth = FirebaseAuth.getInstance();

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String LoginEmailTxt = LoginEmail.getText().toString();
                final String LoginPassTxt = LoginPass.getText().toString();

                if (LoginEmailTxt.isEmpty() || LoginPassTxt.isEmpty()) {
                    Toast.makeText(Login.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Auth.signInWithEmailAndPassword(LoginEmailTxt, LoginPassTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Login Succeeded ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, MainActivity.class));
                            } else {
                                Toast.makeText(Login.this, "Login Failed Please Check your Email & Password ", Toast.LENGTH_SHORT).show();
                            }
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

        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });
    }
}