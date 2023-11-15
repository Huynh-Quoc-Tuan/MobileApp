package com.example.a1786;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ShowHikesActivity extends AppCompatActivity {

    private ListView listView;
    private List<Hiking> hikeList;
    private HikeAdapter hikingAdapter;
    private Button viewDetailBtn;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hikes);
//
//        viewDetailBtn = findViewById(R.id.viewDetailBtn);
//        mListView = findViewById(R.id.hikesListView);

        // Lấy dữ liệu từ Database
        // Khởi tạo danh sách chuyến đi bộ và adapter
        listView = findViewById(R.id.hikesListView);
        hikeList = new ArrayList<>();
        hikingAdapter = new HikeAdapter(this, hikeList);

        listView.setAdapter(hikingAdapter);
        // Thực hiện truy vấn dữ liệu từ cơ sở dữ liệu
       // loadHikeDataFromDatabase();

//        mAdapter = new HikeAdapter(this, hikes);
//        mListView.setAdapter(mAdapter);

    }

    private void loadHikeDataFromDatabase() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        hikeList = databaseHelper.getAllHikes(); // Thay thế bằng truy vấn thực tế
        hikingAdapter.setHikes(hikeList);
    }

    public void openHikeDetail(Hiking hike) {
        Intent intent = new Intent(this, ViewDetailHikeActivity.class);
        intent.putExtra("HIKE_DETAIL_ID", hike.getId());
        startActivity(intent);

    }
}


