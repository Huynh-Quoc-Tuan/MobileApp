package com.example.a1786;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ViewDetailHikeActivity extends Activity {
    private TextView nameOfTheHike, locationOfTheHike, dateOfTheHike, lengthOfTheHike, decription;
    private Button removeBtn, editBtn;
    private HikeAdapter adapter;
    private List<Hiking> hikeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_hike);

        viewDetailHike();

        removeBtn = findViewById(R.id.removeHikeBtn);
        editBtn = findViewById(R.id.editHikeBtn);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewDetailHikeActivity.this);
                builder.setMessage("Bạn có chắc chắn muốn xóa hike này?")
                        .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Xác nhận xóa, tiến hành xóa selectedHike
                                deleteHike();
                            }
                        })
                        .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Người dùng đã hủy xóa, không thực hiện thao tác nào
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hikeId = hikeid();

                // Chuyển đến hoạt động sửa đổi Hike và gửi thông tin Hike qua Intent
                Intent intent = new Intent(ViewDetailHikeActivity.this, UpdateHikeActivity.class);
                intent.putExtra("hikeId", hikeId);
                startActivity(intent);
            }
        });
    }

    private void enscapData() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        hikeList = dbHelper.getAllHikes();


    }

    private void deleteHike() {
        Intent intent = getIntent();
        int hikeId = intent.getIntExtra("HikeID", -1);
        if (hikeId != -1) {
            // Xóa selectedHike khỏi cơ sở dữ liệu bằng Id
            DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
            boolean isDeleted = dbHelper.deleteHikeById(hikeId);
            dbHelper.close();

            if (isDeleted) {
                adapter.notifyDataSetChanged();
                // Quay trở lại MainActivity
                onBackPressed(); // Hoặc có thể sử dụng Intent để chuyển trang
            } else {
                // Xóa không thành công, xử lý lỗi
                Toast.makeText(this, "Không thể xóa hike.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private int hikeid(){
        Intent intent = getIntent();
        int hikeId = intent.getIntExtra("HikeID", -1);
        return hikeId;
    }

    private void viewDetailHike() {
        int hikeId = hikeid();
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

