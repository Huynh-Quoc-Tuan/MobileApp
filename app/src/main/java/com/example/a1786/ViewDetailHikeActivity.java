package com.example.a1786;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewDetailHikeActivity extends AppCompatActivity {
    private TextView nameOfTheHike, locationOfTheHike, dateOfTheHike, lengthOfTheHike, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_hike);

        // Nhận ID của chuyến đi bộ từ Intent
        Intent intent = getIntent();
        long hikeId = intent.getLongExtra("hike_id", -1); // -1 là giá trị mặc định nếu không tìm thấy ID

        // Kiểm tra xem ID có hợp lệ hay không
        if (hikeId != -1) {
            // Sử dụng ID để truy vấn cơ sở dữ liệu và lấy thông tin chi tiết của chuyến đi bộ
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            Hiking hike = databaseHelper.getHikeById((int) hikeId); // Thay thế bằng phương thức truy vấn thực tế

            if (hike != null) {
                // Hiển thị thông tin chi tiết trong layout của DetailActivity
                TextView tvName = findViewById(R.id.nameOfTheHikeTextView);
                TextView tvLocation = findViewById(R.id.locationTextView);
                TextView tvDate = findViewById(R.id.dateOfTheHikeTextView);
                TextView tvDescription = findViewById(R.id.descriptionTextView);
                TextView tvLength = findViewById(R.id.lengthTheHikeTextView);

                tvName.setText(hike.getName());
                tvLocation.setText(hike.getLocation());
                tvDate.setText(hike.getDate());
                tvDescription.setText(hike.getDecription());
                tvLength.setText(hike.getLength());
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



        // Lấy dữ liệu được truyền từ Intent
//        int hikeId = getIntent().getIntExtra("HIKE_DETAIL_ID", -1);
//
//        if (hikeId != -1) {
//            DatabaseHelper dbHelper = new DatabaseHelper(this);
//            Hiking hike = dbHelper.getHikeById(hikeId);
//
//            if (hike != null) {
//                TextView tvName = findViewById(R.id.nameOfTheHikeTextView);
//                TextView tvLocation = findViewById(R.id.locationTextView);
//                TextView tvDate = findViewById(R.id.dateOfTheHikeTextView);
//                TextView tvDescription = findViewById(R.id.descriptionTextView);
//                TextView tvLength = findViewById(R.id.lengthTheHikeTextView);
//
//                tvName.setText(hike.getName());
//                tvLocation.setText(hike.getLocation());
//                tvDate.setText(hike.getDate());
//                tvDescription.setText(hike.getDiscrip());
//                tvLength.setText(hike.getLength());
//            } else {
//                Toast.makeText(ViewDetailHikeActivity.this, "ERRORRRRRRR!!!", Toast.LENGTH_LONG).show();
//            }
//        }
    }

    private void findById() {
        nameOfTheHike = findViewById(R.id.nameOfTheHikeTextView);
        lengthOfTheHike = findViewById(R.id.lengthTheHikeTextView);
        locationOfTheHike = findViewById(R.id.locationTextView);
        dateOfTheHike = findViewById(R.id.dateOfTheHikeTextView);
        description = findViewById(R.id.descriptionTextView);
    }
}

