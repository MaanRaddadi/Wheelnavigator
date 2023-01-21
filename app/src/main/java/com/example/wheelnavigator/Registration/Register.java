package com.example.wheelnavigator.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wheelnavigator.MainActivity;
import com.example.wheelnavigator.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    private FirebaseAuth Auth ;
    private FirebaseUser mCurrentUser;
    private DatabaseReference ref;



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


                //Create User in Firebase Authentication
                Auth = FirebaseAuth.getInstance();
                Auth.createUserWithEmailAndPassword(Emailtxt, Passtxt).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mCurrentUser= task.getResult().getUser();
                            ref=mDatabase.child(mCurrentUser.getUid());
                            ref.child("Username").setValue(Unametxt);
                            ref.child("email").setValue(Emailtxt);
                            ref.child("Password").setValue(Passtxt);
                            ref.child("Disability").setValue(Yestxt);
                        }
                        Toast.makeText(Register.this, "Registration Succeeded", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Register.this, MainActivity.class));
                    }

                });







            }


        });

    }


}
