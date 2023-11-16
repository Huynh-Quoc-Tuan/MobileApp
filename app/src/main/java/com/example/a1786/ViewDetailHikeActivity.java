package com.example.a1786;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewDetailHikeActivity extends Activity {
    private TextView nameOfTheHike, locationOfTheHike, dateOfTheHike, lengthOfTheHike, decription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_hike);

        // Nhận ID của chuyến đi bộ từ Intent
        Intent intent = getIntent();
        int hikeId = intent.getIntExtra("HikeID", -1); // -1 là giá trị mặc định nếu không tìm thấy ID

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

                tvName.setText(hike.getName());
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
            } else {
                // Xử lý trường hợp không tìm thấy chuyến đi bộ với ID cụ thể
                Toast.makeText(this, "Chuyến đi bộ không tồn tại", Toast.LENGTH_SHORT).show();
                Intent hikeInfoIntent = new Intent(this, MainActivity.class);
                startActivity(hikeInfoIntent);
            }
        } else {
            // Xử lý trường hợp không nhận được ID từ Intent
            Toast.makeText(this, "Không có ID được truyền", Toast.LENGTH_SHORT).show();
            Intent hikeInfoIntent = new Intent(this, MainActivity.class);
            startActivity(hikeInfoIntent);
        }
    }

    private void findById() {
        nameOfTheHike = findViewById(R.id.nameOfTheHikeTextView);
        lengthOfTheHike = findViewById(R.id.lengthTheHikeTextView);
        locationOfTheHike = findViewById(R.id.locationTextView);
        dateOfTheHike = findViewById(R.id.dateOfTheHikeTextView);
        decription = findViewById(R.id.decriptionTextView);
    }
}

