package com.example.wheelnavigator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wheelnavigator.Admin.Create_Rating;
import com.example.wheelnavigator.Admin.RequestsPage;
import com.example.wheelnavigator.Admin.viewRequest;

public class Creat_RatingCrn extends AppCompatActivity {
    EditText Crn ;
    Button Next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_rating_crn);

        Crn = findViewById(R.id.Crn);
        Next = findViewById(R.id.next);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Creat_RatingCrn.this, Create_Rating.class);
                intent.putExtra("Crn", Crn.getText().toString());
                startActivity(intent);
            }
        });


    }
}