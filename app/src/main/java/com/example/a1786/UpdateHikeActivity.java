package com.example.a1786;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateHikeActivity extends AppCompatActivity {
    private Button viewHikesButton, addHikeButton;
    private RadioGroup levelOfDiff, parkingAvailable;
    private RadioButton levelOfDiff1, levelOfDiff2, levelOfDiff3, parkingAvailableYes, parkingAvailableNo;
    private TextView nameOfTheHike, locationOfTheHike, dateOfTheHike, lengthOfTheHike, decription;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_main layout
        setContentView(R.layout.activity_update_hike);
        databaseHelper = new DatabaseHelper(this);

        findById();

        Intent intent = getIntent();
        int hikeId = intent.getIntExtra("selected_hike", -1); // -1 là giá trị mặc định nếu không tìm thấy ID
        // Kiểm tra xem ID có hợp lệ hay không
        if (hikeId != -1) {
            // Sử dụng ID để truy vấn cơ sở dữ liệu và lấy thông tin chi tiết của chuyến đi bộ
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            Hiking hike = databaseHelper.getHike(hikeId); // Thay thế bằng phương thức truy vấn thực tế

            if (hike != null) {
                // Hiển thị thông tin chi tiết trong layout của DetailActivity
                TextView tvName = findViewById(R.id.nameOfTheHikeTextView);
                TextView tvLocation = findViewById(R.id.locationTextView);
                TextView tvDate = findViewById(R.id.dateOfTheHikeTextView);
                TextView tvDecription = findViewById(R.id.decriptionTextView);
                TextView tvLength = findViewById(R.id.lengthTheHikeTextView);
                RadioButton btnEasy = findViewById(R.id.levelOfDiff1);
                RadioButton btnNormal = findViewById(R.id.levelOfDiff2);
                RadioButton btnHard = findViewById(R.id.levelOfDiff3);
                RadioButton btnYes = findViewById(R.id.parkingAvailableYes);
                RadioButton btnNo = findViewById(R.id.parkingAvailableNo);

                tvName.setText(String.valueOf(hike.getId()));
                tvLocation.setText(hike.getLocation());
                tvDate.setText(hike.getDate());
                tvDecription.setText(hike.getDescription());
                tvLength.setText(hike.getLength());

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

        parkingAvailable = findViewById(R.id.parkingAvailable);
        parkingAvailableYes = findViewById(R.id.parkingAvailableYes);
        parkingAvailableNo = findViewById(R.id.parkingAvailableNo);

        levelOfDiff = findViewById(R.id.levelOfDiff);
        levelOfDiff1= findViewById(R.id.levelOfDiff1);
        levelOfDiff2 = findViewById(R.id.levelOfDiff2);
        levelOfDiff3 = findViewById(R.id.levelOfDiff3);
    }
}
