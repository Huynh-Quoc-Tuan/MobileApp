package com.example.a1786;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewHikesActivity extends AppCompatActivity {

    private ListView mListView;
    private HikeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hikes);

        mListView = findViewById(R.id.hikesListView);

        // Lấy dữ liệu từ Database
        DatabaseHelper dbHelper = new DatabaseHelper(ViewHikesActivity.this);
        List<Hiking> hikes = dbHelper.getAllHikes(); // Giả định bạn có phương thức này trong dbHelper

        mAdapter = new HikeAdapter(ViewHikesActivity.this, hikes);
        mListView.setAdapter(mAdapter);
    }
}


