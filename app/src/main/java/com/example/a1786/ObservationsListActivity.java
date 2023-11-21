package com.example.a1786;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ObservationsListActivity extends AppCompatActivity {
    private RecyclerView observationsRecyclerView;
    private List<Observation> observations;
    private DatabaseHelper databaseHelper;
    private ObservationListAdapter observationListAdapter;

    private static final int EDIT_OBSERVATION_REQUEST_CODE = 123;


    protected void onResume() {
        super.onResume();
        int hikeId = getIntent().getIntExtra("hikeId", -1);
        loadObservationList(hikeId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.observations_list);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the Activity when the back button is pressed
            }
        });


        observationsRecyclerView = findViewById(R.id.observationsRecyclerView);
        databaseHelper = new DatabaseHelper(this);

        int hikeId = getIntent().getIntExtra("hikeId", -1);

        if (hikeId != -1) {
            observations = databaseHelper.getAllObservationsForHike(hikeId);
            observationListAdapter = new ObservationListAdapter(this, observations, observation -> {
                Intent intent = new Intent(ObservationsListActivity.this, ObservationDetailActivity.class);
                intent.putExtra("observation", observation);
                startActivity(intent);
            }, databaseHelper);
            observationsRecyclerView.setAdapter(observationListAdapter);
            observationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            Toast.makeText(this, "Invalid Hike ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        FloatingActionButton buttonAddObservation = findViewById(R.id.buttonAddObservation);
        buttonAddObservation.setOnClickListener(view -> {
            Intent intent = new Intent(ObservationsListActivity.this, AddObservationActivity.class);
            intent.putExtra("hikeId", getIntent().getIntExtra("hikeId", -1));
            startActivity(intent);
            finish();
        });

        observationListAdapter = new ObservationListAdapter(this, observations, observation -> {
            showPopupMenu(observationsRecyclerView, observation);
        }, databaseHelper);
        observationListAdapter.setOnItemClickListener(observation -> {
            showPopupMenu(observationsRecyclerView, observation);
        });
    }

    private void showPopupMenu(View view, Observation observation) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.observation_menu);

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_edit_observation) {
                openEditObservationActivity(observation);
                return true;
            } else if (item.getItemId() == R.id.menu_delete_observation) {
                deleteObservation(observation);
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

    private void openEditObservationActivity(Observation observation) {
        Intent intent = new Intent(this, EditObservationActivity.class);
        intent.putExtra("observation", observation);
        startActivityForResult(intent, EDIT_OBSERVATION_REQUEST_CODE);
        finish();
    }

    private void deleteObservation(final Observation observation) {
        if (observation != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to delete this observation?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            databaseHelper.deleteObservation(observation.getObservationId());
                            observations.remove(observation);
                            observationListAdapter.notifyDataSetChanged();
                            Toast.makeText(ObservationsListActivity.this, "Observation deleted", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            // Tạo và hiển thị AlertDialog
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            Toast.makeText(this, "Failed to delete observation", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadObservationList(int hikeId) {
        if (hikeId != -1) {
            observations = databaseHelper.getAllObservationsForHike(hikeId);
            if (observations != null && !observations.isEmpty()) {
                observationListAdapter.setObservations(observations);
                observationListAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "No observations available", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Invalid Hike ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


}
