package com.example.a1786;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ViewDetailHikeActivity extends Activity {
    private TextView nameOfTheHike, locationOfTheHike, dateOfTheHike, lengthOfTheHike, decription;
    private Button removeBtn, editBtn, backBtn;
    private HikeAdapter adapter;
    private List<Hiking> hikeList;
    static final int UPDATE_HIKE_REQUEST = 1;  // Request code to identify the result
    private Handler handler = new Handler(Looper.getMainLooper());



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_HIKE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                // Update successful, update the information of the previous page
                viewDetailHike();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail_hike);

        viewDetailHike();

        removeBtn = findViewById(R.id.removeHikeBtn);
        editBtn = findViewById(R.id.editHikeBtn);
        backBtn = findViewById(R.id.backBtn);
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewDetailHikeActivity.this);
                builder.setMessage("Are you sure you want to delete this hike?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Confirm deletion, proceed to delete selectedHike
                                deleteHike();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User canceled deletion, no action is taken
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

                // Navigate to the Edit Hike activity and send Hike information via Intent
                Intent intent = new Intent(ViewDetailHikeActivity.this, UpdateHikeActivity.class);
                intent.putExtra("hikeId", hikeId);
                startActivity(intent);
                intent.putExtra("selected_hike", hikeId);
                startActivityForResult(intent,UPDATE_HIKE_REQUEST);
            }
        });
        MaterialButton viewObservationButton = findViewById(R.id.viewObservation);
        viewObservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hikeId = hikeid();
                Intent intent = new Intent(ViewDetailHikeActivity.this, ObservationsListActivity.class);
                intent.putExtra("hikeId", hikeId);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                updateHikeList();
//                adapter.notifyDataSetChanged();
                onBackPressed();
            }
        });
    }

    private void enscapData() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        hikeList = dbHelper.getAllHikes();


    }
    private void updateHikeList() {
        // Đặt logic cập nhật danh sách Hike ở đây
        // ...

        // Sau khi cập nhật, sử dụng Handler để thông báo về thay đổi trên luồng UI chính
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

//    private void updateHikeList() {
//        // Place the logic for updating the Hike list here
//        // ...
//
//        // After the update, use a Handler to notify about the changes on the main UI thread
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }


    private void deleteHike() {
        Intent intent = getIntent();
        int hikeId = intent.getIntExtra("HikeID", -1);
        if (hikeId != -1) {
            // Delete selectedHike from the database by Id
            DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
            boolean isDeleted = dbHelper.deleteHikeById(hikeId);
            dbHelper.close();

            if (isDeleted) {
                adapter.notifyDataSetChanged();
                // Go back to MainActivity
                onBackPressed(); // Or use Intent to navigate to another page
            } else {
                // Deletion unsuccessful, handle error
                Toast.makeText(this, "Unable to delete hike.", Toast.LENGTH_SHORT).show();
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
        // Check if the ID is valid or not
        if (hikeId != -1) {
            // Use the ID to query the database and retrieve detailed information of the hike
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            Hiking hike = databaseHelper.getHike(hikeId); // Replace with the actual query method

            if (hike != null) {
                // Display detailed information in the layout of DetailActivity
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
                // Handle the case when no hike is found with the specific ID
                Toast.makeText(this, "Hike does not exist", Toast.LENGTH_SHORT).show();
                Intent hikeInfoIntent = new Intent(this, MainActivity.class);
                startActivity(hikeInfoIntent);
            }
        } else {
            // Handle the case when ID is not received from Intent
            Toast.makeText(this, "No ID is passed", Toast.LENGTH_SHORT).show();
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

