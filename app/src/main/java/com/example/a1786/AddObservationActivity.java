package com.example.a1786;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import android.widget.DatePicker;


public class AddObservationActivity extends AppCompatActivity{

    private EditText observationEditText;
    private EditText dateTimeObservation;
    private EditText commentsEditText;
    private ImageView selectedImageView;
    private Button addObservationButton;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int MAX_IMAGE_WIDTH = 800;
    private static final int MAX_IMAGE_HEIGHT = 800;

    private static final int MAX_OBSERVATION_LENGTH = 100;
    private static final int MAX_COMMENTS_LENGTH = 200;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_observation);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the Activity when the back button is pressed
            }
        });

        observationEditText = findViewById(R.id.observationEditText);
        dateTimeObservation = findViewById(R.id.dateTimeObservation);
        commentsEditText = findViewById(R.id.commentsEditText);
        selectedImageView = findViewById(R.id.selectedImageView);
        addObservationButton = findViewById(R.id.addObservationButton);

        // Set an OnClickListener for the DateTime EditText
        dateTimeObservation.setFocusable(false);
        dateTimeObservation.setFocusableInTouchMode(false);
        dateTimeObservation.setOnClickListener(v -> showDateTimePickerDialog());

        // Set an OnClickListener for the ImageView to pick an image
        selectedImageView.setOnClickListener(view -> pickImage());

        // Set an OnClickListener for the Add Observation Button
        addObservationButton.setOnClickListener(view -> addObservation());
    }


    // Callback method when the date is set by the user in DatePickerDialog
    private void showDateTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // Show DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Save the selected date
                        final int selectedYear = year;
                        final int selectedMonth = month;
                        final int selectedDay = dayOfMonth;

                        // Show TimePickerDialog after selecting the date
                        TimePickerDialog timePickerDialog = new TimePickerDialog(
                                AddObservationActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        // Save the selected time
                                        final int selectedHour = hourOfDay;
                                        final int selectedMinute = minute;

                                        // Display the selected date and time
                                        String selectedDateTime ="Date:" + " " + selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear + "\nTime:" + " " + selectedHour + ":" + selectedMinute;
                                        dateTimeObservation.setText(selectedDateTime);
                                    }
                                },
                                currentHour, currentMinute, true
                        );

                        timePickerDialog.show();
                    }
                },
                currentYear, currentMonth, currentDay
        );

        datePickerDialog.show();
    }


    private void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                if (bitmap.getWidth() > MAX_IMAGE_WIDTH || bitmap.getHeight() > MAX_IMAGE_HEIGHT) {
                    Toast.makeText(this, "The image size is too large, please select a smaller image", Toast.LENGTH_SHORT).show();
                    return;
                }

                selectedImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addObservation() {
        String observationText = observationEditText.getText().toString().trim();
        String dateTime = dateTimeObservation.getText().toString().trim();
        String comments = commentsEditText.getText().toString().trim();
        byte[] photo = getPhotoByteArray();
        int hikeId = getIntent().getIntExtra("hikeId", -1);

        if (observationText.isEmpty() || dateTime.isEmpty() || comments.isEmpty() || photo == null || hikeId == -1) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }if(observationText.length() > MAX_OBSERVATION_LENGTH ){
            Toast.makeText(this, "Observation must have less or equal than 100 character", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Observation newObservation = new Observation(hikeId, observationText, dateTime, comments, photo);
        dbHelper.addObservation(newObservation);

        Toast.makeText(this, "Observation added successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(AddObservationActivity.this, ObservationsListActivity.class);
        intent.putExtra("hikeId", getIntent().getIntExtra("hikeId", -1));
        startActivity(intent);
        // You can finish the activity or navigate to another screen as needed
        finish();
    }

    private byte[] getPhotoByteArray() {
        Bitmap photoBitmap = ((BitmapDrawable) selectedImageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    // You've previously called this method to retrieve Hike by its ID
    private Hiking getHikeById(int hikeId) {
        // Implement your logic to retrieve Hike from the data source here
        // You might use a DatabaseHelper or another method to fetch the Hike
        // For simplicity, it's assumed you have a method like dbHelper.getHikeById(hikeId)
        return dbHelper.getHikeById(hikeId);
    }
}
