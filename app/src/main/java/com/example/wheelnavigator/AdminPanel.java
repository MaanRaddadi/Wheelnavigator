package com.example.wheelnavigator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        final Button ViewRequests = findViewById(R.id.View_Requests);
        final Button CreateRating = findViewById(R.id.PostRating);

        ViewRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this, RequestsPage.class));
            }
        });
        CreateRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this, Create_Rating.class));
            }
        });

    }
}