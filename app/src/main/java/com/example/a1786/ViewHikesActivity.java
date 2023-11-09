package com.example.a1786;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class ViewHikesActivity extends AppCompatActivity {

    private ListView hikesListView;
    private DatabaseHelper dbHelper;
    private SimpleCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hikes);
        // Logic to display list of Hikes will go here
        dbHelper = new DatabaseHelper(this);
        hikesListView = findViewById(R.id.hikesListView);

        loadHikes();
    }

    private void loadHikes() {
        Cursor cursor = dbHelper.getAllHikes();
        String[] from = new String[] {
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_LOCATION,
                DatabaseHelper.COLUMN_DESCRIPTION
        };
        int[] to = new int[] {
                R.id.textViewHikeName,
                R.id.textViewHikeLocation,
                R.id.textViewHikeDescription
        };

        adapter = new SimpleCursorAdapter(this,
                R.layout.hike_list_item, cursor, from, to, 0);
        hikesListView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

}
