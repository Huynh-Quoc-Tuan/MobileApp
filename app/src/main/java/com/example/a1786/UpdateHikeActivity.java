package com.example.a1786;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateHikeActivity extends AppCompatActivity {
    private Button backBtn, updateBtn;
    private RadioGroup levelOfDiff, parkingAvailable;
    private RadioButton levelOfDiff1, levelOfDiff2, levelOfDiff3, parkingAvailableYes, parkingAvailableNo;
    private EditText nameOfTheHike, locationOfTheHike, dateOfTheHike, lengthOfTheHike, decription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hike);

        findById();
        viewDetailHike();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InputValidatorEditText.areAllEditTextsFilled(nameOfTheHike, locationOfTheHike, dateOfTheHike, lengthOfTheHike, decription)){
                    if (!InputValidatorEditText.isEditTextLengthValid(nameOfTheHike, 100)) {
                        nameOfTheHike.setError("Name is too long, please enter less than \" + 100 + \" characters.");
                        return;
                    }

                    if (!InputValidatorEditText.isEditTextLengthValid(locationOfTheHike, 100)) {
                        locationOfTheHike.setError("Location is too long, please enter less than \" + 100 + \" characters.");
                        return;
                    }

                    if (!InputValidatorEditText.isEditTextLengthValid(decription, 200)) {
                        decription.setError("Description is too long, please enter less than \" + 200 + \" characters.");
                        return;
                    }

                    if (!InputValidatorEditText.isEditTextLengthValid(decription, 200)) {
                        decription.setError("Description is too long, please enter less than \" + 200 + \" characters.");
                        return;
                    }
                    activityUpdate();

                } else {
                    // Hiển thị thông báo hoặc thông báo lỗi cho người dùng
                    Toast.makeText(UpdateHikeActivity.this, "Please complete all information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void activityUpdate() {
        DatabaseHelper databaseHelper = new DatabaseHelper(UpdateHikeActivity.this);
        parkingAvailable = findViewById(R.id.parkingAvailable);
        levelOfDiff = findViewById(R.id.levelOfDiff);

        int hikeId = hikeid();
        String name = nameOfTheHike.getText().toString();
        String location = locationOfTheHike.getText().toString();
        String date = dateOfTheHike.getText().toString();
        String length = lengthOfTheHike.getText().toString();
        String description = decription.getText().toString();



        String level = "", parking = "";
        int selectedLevelRadioId = levelOfDiff.getCheckedRadioButtonId();
        int selectedParkingRadioId = parkingAvailable.getCheckedRadioButtonId();

        if (selectedLevelRadioId != -1 && selectedParkingRadioId != -1) {
            RadioButton selectedLevelRadioButton = findViewById(selectedLevelRadioId);
            RadioButton selectedParkingRadioButton = findViewById(selectedParkingRadioId);
            level = selectedLevelRadioButton.getText().toString();
            parking = selectedParkingRadioButton.getText().toString();
        }

        // Call the update method in the database
        Boolean updateSuccess  = databaseHelper.updateHike(hikeId, name, location, date, length, level, parking, description);

        if (updateSuccess) {
            Toast.makeText(UpdateHikeActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            onBackPressed();
            finish();
        } else {
            Toast.makeText(UpdateHikeActivity.this, "Data CAN NOT UPDATE", Toast.LENGTH_LONG).show();
        }
    }

    private int hikeid(){
        Intent intent = getIntent();
        int hikeId = intent.getIntExtra("selected_hike", -1);
        return hikeId;
    }

    private void viewDetailHike() {
        int hikeId = hikeid();
        // Check if the ID is valid or not
        if (hikeId != -1) {
            // Use the ID to query the database and retrieve detailed information of the hike
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            Hiking hike = databaseHelper.getHike(hikeId); // Replace with the actual query method

            if (hike != null) {
                // Display detailed information in the layout of DetailActivity
                nameOfTheHike = findViewById(R.id.nameOfTheHike);
                lengthOfTheHike = findViewById(R.id.lengthTheHike);
                locationOfTheHike = findViewById(R.id.location);
                dateOfTheHike = findViewById(R.id.dateOfTheHike);
                decription = findViewById(R.id.decription);

                RadioButton btnEasy = findViewById(R.id.levelOfDiff1);
                RadioButton btnNormal = findViewById(R.id.levelOfDiff2);
                RadioButton btnHard = findViewById(R.id.levelOfDiff3);
                RadioButton btnYes = findViewById(R.id.parkingAvailableYes);
                RadioButton btnNo = findViewById(R.id.parkingAvailableNo);

                nameOfTheHike.setText(hike.getName());
                locationOfTheHike.setText(hike.getLocation());
                dateOfTheHike.setText(hike.getDate());
                decription.setText(hike.getDescription());
                lengthOfTheHike.setText(hike.getLength());

                if ("Easy".equals(hike.getLevel())) {
                    btnEasy.setChecked(true);
                } else if ("Normal".equals(hike.getLevel())) {
                    btnNormal.setChecked(true);
                } else if ("Hard".equals(hike.getLevel())) {
                    btnHard.setChecked(true);
                }

                if ("Yes".equals(hike.getParking())) {
                    btnYes.setChecked(true);
                } else if ("No".equals(hike.getParking())) {
                    btnNo.setChecked(true);
                }
            } else {
                // Handle the case when no hike is found with the specific ID
                Toast.makeText(this, "Hike does not exist", Toast.LENGTH_SHORT).show();
                Intent hikeInfoIntent = new Intent(this, MainActivity.class);
                startActivity(hikeInfoIntent);
            }
        } else {
            // Handle the case when ID is not received from Intent
            Toast.makeText(this, "No ID is passed", Toast.LENGTH_SHORT).show();
            Intent hikeInfoIntent = new Intent(this, MainActivity.class);
            startActivity(hikeInfoIntent);
        }
    }

    private void findById() {
        updateBtn = findViewById(R.id.updateBtn);
        backBtn = findViewById(R.id.backBtn);
    }
}
