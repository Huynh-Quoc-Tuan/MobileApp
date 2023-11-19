package com.example.a1786;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

public class ShowHikesActivity extends AppCompatActivity {
    private ListView listView;

    private HikeAdapter adapter;
    private List<Hiking> hikeList;
    private DatabaseHelper db;
    private SearchView searchView;
    private List<Hiking> filteredHikes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hikes);

        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);

        hikeList = db.getAllHikes();

        adapter = new HikeAdapter(this, hikeList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the ID of the selected Hike
                int selectedHikeId = hikeList.get(position).getId();

                // Switch to the detail screen of the Hike and pass the ID
                Intent intent = new Intent(ShowHikesActivity.this, ViewDetailHikeActivity.class);
                intent.putExtra("HikeID", selectedHikeId);
                startActivity(intent);
            }
        });

        filteredHikes = new ArrayList<>();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle when the user clicks the search button (if necessary)
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle when the user changes the search content
                filterHikes(newText);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }


    private void filterHikes(String query) {
        filteredHikes.clear();
        for (Hiking hike : hikeList) {
            if (hike.getName().toLowerCase().contains(query.toLowerCase()) || hike.getLocation().toLowerCase().contains(query.toLowerCase())
                || hike.getLength().toLowerCase().contains(query.toLowerCase()) || hike.getDate().toLowerCase().contains(query.toLowerCase())) {
                filteredHikes.add(hike);
            }
        }
        adapter = new HikeAdapter(this, filteredHikes);
        listView.setAdapter(adapter);
    }
}


