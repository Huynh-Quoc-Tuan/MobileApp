package com.example.a1786;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Button viewBtn, submitBtn;
    private RadioGroup levelOfDiff, parkingAvailable;
    private RadioButton levelOfDiff1, levelOfDiff2, levelOfDiff3, parkingAvailableYes, parkingAvailableNo;
    private EditText nameOfTheHike, locationOfTheHike, dateOfTheHike, lengthOfTheHike, decription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_main layout
        setContentView(R.layout.hiking123);
        findById();

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowHikesActivity.class);
//                intent.putExtra("HikeID", 3);
                startActivity(intent);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InputValidatorEditText.areAllEditTextsFilled(nameOfTheHike, locationOfTheHike, dateOfTheHike, lengthOfTheHike, decription)){
                    if (!InputValidatorEditText.isEditTextLengthValid(nameOfTheHike, 100)) {
                        nameOfTheHike.setError("Tên quá dài, vui lòng nhập ít hơn " + 100 + " ký tự.");
                        return;
                    }

                    if (!InputValidatorEditText.isEditTextLengthValid(locationOfTheHike, 100)) {
                        locationOfTheHike.setError("Địa điểm quá dài, vui lòng nhập ít hơn " + 100 + " ký tự.");
                        return;
                    }

                    if (!InputValidatorEditText.isEditTextLengthValid(decription, 200)) {
                        decription.setError("Mô tả quá dài, vui lòng nhập ít hơn " + 200 + " ký tự.");
                        return;
                    }

                    if (!InputValidatorEditText.isEditTextLengthValid(decription, 200)) {
                        decription.setError("Mô tả quá dài, vui lòng nhập ít hơn " + 200 + " ký tự.");
                        return;
                    }
                    switchConfirmPage();

                } else {
                    // Hiển thị thông báo hoặc thông báo lỗi cho người dùng
                    Toast.makeText(MainActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void switchConfirmPage() {
        // Get information from Edit Text
        String name = nameOfTheHike.getText().toString();
        String location = locationOfTheHike.getText().toString();
        String dateofhike = dateOfTheHike.getText().toString();
        String decrip = decription.getText().toString();
        String lenght = lengthOfTheHike.getText().toString();

        // Get infor from Radio Button
        String level = "", parking = "";
        int selectedLevelRadioId = levelOfDiff.getCheckedRadioButtonId();
        int selectedParkingRadioId = parkingAvailable.getCheckedRadioButtonId();

        if (selectedLevelRadioId != -1 && selectedParkingRadioId != -1) {
            RadioButton selectedLevelRadioButton = findViewById(selectedLevelRadioId);
            RadioButton selectedParkingRadioButton = findViewById(selectedParkingRadioId);
            level = selectedLevelRadioButton.getText().toString();
            parking = selectedParkingRadioButton.getText().toString();
        }

        // Start the ViewHikesActivity when the button is clicked
        Intent intent = new Intent(MainActivity.this, ConfirmHikingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("NAME", name);
        bundle.putString("LOCATION", location);
        bundle.putString("DATE", dateofhike);
        bundle.putString("LENGTH", lenght);
        bundle.putString("DESCRIP", decrip);
        bundle.putString("LEVEL", level);
        bundle.putString("PARKING", parking);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private void findById() {
        nameOfTheHike = findViewById(R.id.nameOfTheHike);
        lengthOfTheHike = findViewById(R.id.lengthTheHike);
        locationOfTheHike = findViewById(R.id.location);
        dateOfTheHike = findViewById(R.id.dateOfTheHike);
        decription = findViewById(R.id.decription);
        submitBtn = findViewById(R.id.submitBtn);
        viewBtn = findViewById(R.id.viewBtn);

        parkingAvailable = findViewById(R.id.parkingAvailable);
        parkingAvailableYes = findViewById(R.id.parkingAvailableYes);
        parkingAvailableNo = findViewById(R.id.parkingAvailableNo);

        levelOfDiff = findViewById(R.id.levelOfDiff);
        levelOfDiff1= findViewById(R.id.levelOfDiff1);
        levelOfDiff2 = findViewById(R.id.levelOfDiff2);
        levelOfDiff3 = findViewById(R.id.levelOfDiff3);

        parkingAvailableYes.setChecked(true);
        levelOfDiff1.setChecked(true);
    }
}


