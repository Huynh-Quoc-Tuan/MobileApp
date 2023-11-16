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

    private HikeAdapter adapter;
    private List<Hiking> hikeList;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hikes);

        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);

        hikeList = db.getAllHikes();

        adapter = new HikeAdapter(this, hikeList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy ID của Hike được chọn
                int selectedHikeId = hikeList.get(position).getId();

                // Chuyển sang màn hình chi tiết của Hike và truyền ID
                Intent intent = new Intent(ShowHikesActivity.this, ViewDetailHikeActivity.class);
                intent.putExtra("HikeID", selectedHikeId);
                startActivity(intent);
            }
        });
    }
}


