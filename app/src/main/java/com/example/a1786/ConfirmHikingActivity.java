package com.example.a1786;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmHikingActivity extends AppCompatActivity {

    private Button viewHikesButton, addHikeButton;
    private RadioGroup levelOfDiff, parkingAvailable;
    private RadioButton levelOfDiff1, levelOfDiff2, levelOfDiff3, parkingAvailableYes, parkingAvailableNo;
    private EditText dateOfTheHike, lengthOfTheHike, description;
    private TextView nameHi, localHi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_main layout
        setContentView(R.layout.confirm_add_hike);
        findById();

        // Get Iten from Main Activity
        Intent intent = getIntent();
        if (intent != null) {
            // Lấy Bundle từ Intent
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                // Lấy thông tin từ Bundle
                String name = bundle.getString("NAME", "");
                String location = bundle.getString("LOCATION", "");
                String date = bundle.getString("DATE", "");
                String lenght = bundle.getString("LENGTH", "");
                String descrip = bundle.getString("DESCRIP", "");
                String level = bundle.getString("LEVEL", "");
                String parking = bundle.getString("PARKING", "");

                // Showw infor
                nameHi.setText(name);
                localHi.setText(location);

            }
        }
    }
    private void findById() {
//        nameOfTheHike = findViewById(R.id.nameOfTheHike);
        lengthOfTheHike = findViewById(R.id.lengthTheHike);
//        locationOfTheHike = findViewById(R.id.location);
        dateOfTheHike = findViewById(R.id.dateOfTheHike);
        description = findViewById(R.id.description);
        addHikeButton = findViewById(R.id.submitBtn);

        nameHi = findViewById(R.id.nameOfTheHikeTestView);
        localHi = findViewById(R.id.locationTestView);

        parkingAvailable = findViewById(R.id.parkingAvailable);
        parkingAvailableYes = findViewById(R.id.parkingAvailableYes);
        parkingAvailableNo = findViewById(R.id.parkingAvailableNo);

        levelOfDiff = findViewById(R.id.levelOfDiff);
        levelOfDiff1= findViewById(R.id.levelOfDiff1);
        levelOfDiff2 = findViewById(R.id.levelOfDiff2);
        levelOfDiff3 = findViewById(R.id.levelOfDiff3);

    }
}
    


