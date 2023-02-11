package com.example.wheelnavigator.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wheelnavigator.Admin.AdminPanel;
import com.example.wheelnavigator.MainActivity;
import com.example.wheelnavigator.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                                Toast.makeText(Login.this, "Login Failed Please Check your Email or Password and try again", Toast.LENGTH_SHORT).show();
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