package com.example.wheelnavigator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.wheelnavigator.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replacefragment(new RecommendedFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {


            switch (item.getItemId()) {
                case R.id.Recommended:
                    replacefragment(new RecommendedFragment());
                    break;
                case R.id.Explore:
                    replacefragment(new ExploreFragment());
                    break;
                case R.id.Contribute:
                    replacefragment(new CountributeFragment());
                    break;
                case R.id.Account:
                    replacefragment(new AccountFragment());
                    break;


            }

            return true;
        });


    }

    private void replacefragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_id, fragment);
        fragmentTransaction.commit();
    }
}