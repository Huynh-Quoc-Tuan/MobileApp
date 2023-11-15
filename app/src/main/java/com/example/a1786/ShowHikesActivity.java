package com.example.a1786;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ShowHikesActivity extends AppCompatActivity {

    private ListView listView;
    private List<Hiking> hikeList;
    private ArrayAdapter<String> adapter;
    private HikeAdapter hikingAdapter;
    private ListView mListView;
    private HikeAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hikes);

        listView = findViewById(R.id.hikesListView);
        hikeList = new ArrayList<>();
        hikingAdapter = new HikeAdapter(this, hikeList);
        listView.setAdapter(hikingAdapter);
        loadHikeDataFromDatabase();
        // Khai báo ListView
//        listView = findViewById(R.id.hikesListView);
//
//        // Tạo danh sách chuyến đi bộ (hikeList)
//        DatabaseHelper dbHelper = new DatabaseHelper(this);
//        hikeList = dbHelper.getAllHikes(); // Thay thế bằng truy vấn thực tế
//
//        // Tạo HikeAdapter và đặt nó cho ListView
//        HikeAdapter hikeAdapter = new HikeAdapter(this, hikeList);
//        listView.setAdapter(hikeAdapter);


//        listView = findViewById(R.id.hikesListView);
//        databaseHelper = new DatabaseHelper(this);
//
//        // Lấy danh sách các chuyến đi bộ từ cơ sở dữ liệu
//        List<Hiking> hikeList = databaseHelper.getAllHikes();
//
//        // Chuyển danh sách các chuyến đi bộ thành danh sách tên để hiển thị trong ListView
//        List<String> hikeInfoList = new ArrayList<>();
//        for (Hiking hike : hikeList) {
//            String hikeInfo = "Name: " + hike.getName() +
//                    "\nLocation: " + hike.getLocation() +
//                    "\nDate: " + hike.getDate() +
//                    "\nLength: " + hike.getLength();
//            hikeInfoList.add(hikeInfo);
//        }
//
//        // Tạo và đặt adapter cho ListView
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hikeInfoList);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // Lấy chuyến đi bộ tại vị trí được chọn
//                Hiking selectedHike = hikeList.get(position);
//
//                Intent intent = new Intent(ShowHikesActivity.this, ViewDetailHikeActivity.class);
//                intent.putExtra("hike_id", selectedHike.getId()); // Giả sử ID của chuyến đi bộ là một số long
//                startActivity(intent);
//
//                // Chuyển sang DetailActivity
//                startActivity(intent);
//            }
//        });
    }

    private void loadHikeDataFromDatabase() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        hikeList = databaseHelper.getAllHikes(); // Thay thế bằng truy vấn thực tế
        hikingAdapter.setHikes(hikeList);
    }

//    public void openHikeDetail(Hiking hike) {
//        Intent intent = new Intent(this, ViewDetailHikeActivity.class);
//        intent.putExtra("HIKE_DETAIL_ID", hike.getId());
//        startActivity(intent);
//
}


