package com.example.a1786;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddHikingActivity extends AppCompatActivity {
    private Button viewHikesButton, addHikeButton;
    private RadioGroup levelOfDiff, parkingAvailable;
    private RadioButton levelOfDiff1, levelOfDiff2, levelOfDiff3, parkingAvailableYes, parkingAvailableNo;
    private TextView nameOfTheHike, locationOfTheHike, dateOfTheHike, lengthOfTheHike, description;
    private DatabaseHelper databaseHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_main layout
        setContentView(R.layout.confirm_add_hike);
        databaseHelper = new DatabaseHelper(this);

    }
}
