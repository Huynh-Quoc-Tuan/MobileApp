package com.example.a1786;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfirmHikingActivity extends AppCompatActivity {

    private Button backBtn, addHikeButton;
    private RadioGroup levelOfDiff, parkingAvailable;
    private RadioButton levelOfDiff1, levelOfDiff2, levelOfDiff3, parkingAvailableYes, parkingAvailableNo;
    private TextView nameOfTheHike, locationOfTheHike, dateOfTheHike, lengthOfTheHike, decription;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_main layout
        setContentView(R.layout.confirm_add_hike);
        databaseHelper = new DatabaseHelper(this);

        findById();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (intent != null) {
            // Get Bundle from Intent
            if (bundle != null) {

                // Retrieve information from Bundle
                String name = bundle.getString("NAME");
                String location = bundle.getString("LOCATION");
                String date = bundle.getString("DATE", "");
                String lenght = bundle.getString("LENGTH", "");
                String decrip = bundle.getString("DESCRIP", "");
                String level = bundle.getString("LEVEL", "");
                String parking = bundle.getString("PARKING", "");


                // Show infor
                nameOfTheHike.setText(name);
                locationOfTheHike.setText(location);
                dateOfTheHike.setText(date);
                lengthOfTheHike.setText(lenght);
                decription.setText(decrip);

                if ("Easy".equals(level)) {
                    levelOfDiff1.setChecked(true);
                } else if ("Normal".equals(level)) {
                    levelOfDiff2.setChecked(true);
                } else if ("Hard".equals(level)) {
                    levelOfDiff3.setChecked(true);
                }

                if ("Yes".equals(parking)) {
                    parkingAvailableYes.setChecked(true);
                } else if ("No".equals(parking)) {
                    parkingAvailableNo.setChecked(true);
                }

                addHikeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Create new Hike
                        Hiking newHike = new Hiking();
                        newHike.setName(name);
                        newHike.setLocation(location);
                        newHike.setDate(date);
                        newHike.setLength(lenght);
                        newHike.setLevel(level);
                        newHike.setParking(parking);
                        newHike.setDescription(decrip);

                        DatabaseHelper dbHelper = new DatabaseHelper(ConfirmHikingActivity.this); // The context here is the Context from your Activity or Fragment
                        boolean result = dbHelper.addHike(newHike);
                        if(result) {

                            Toast.makeText(ConfirmHikingActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ConfirmHikingActivity.this, ShowHikesActivity.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(ConfirmHikingActivity.this,"FAILED, MY DEAR =(((",Toast.LENGTH_LONG).show();
                        dbHelper.close();
                    }
                });

                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
            }
        }
    }

    private void findById() {
        nameOfTheHike = findViewById(R.id.nameOfTheHikeTextView);
        lengthOfTheHike = findViewById(R.id.lengthTheHikeTextView);
        locationOfTheHike = findViewById(R.id.locationTextView);
        dateOfTheHike = findViewById(R.id.dateOfTheHikeTextView);
        decription = findViewById(R.id.decriptionTextView);
        addHikeButton = findViewById(R.id.addHikeButton);
        backBtn = findViewById(R.id.backBtn);


        parkingAvailable = findViewById(R.id.parkingAvailable);
        parkingAvailableYes = findViewById(R.id.parkingAvailableYes);
        parkingAvailableNo = findViewById(R.id.parkingAvailableNo);

        levelOfDiff = findViewById(R.id.levelOfDiff);
        levelOfDiff1= findViewById(R.id.levelOfDiff1);
        levelOfDiff2 = findViewById(R.id.levelOfDiff2);
        levelOfDiff3 = findViewById(R.id.levelOfDiff3);
    }

    private void goToViewPage() {
        Intent intent = new Intent(ConfirmHikingActivity.this, ShowHikesActivity.class);
        startActivity(intent);
    }
}
    


