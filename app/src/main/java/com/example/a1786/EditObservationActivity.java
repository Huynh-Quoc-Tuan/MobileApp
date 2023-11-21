package com.example.a1786;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditObservationActivity extends AppCompatActivity {
    private EditText editTextObservation;
    private EditText editTextTimeOfObservation;
    private EditText editTextAdditionalComments;
    private Button buttonSaveObservation;
    private ImageView imageViewObservation;
    private DatabaseHelper databaseHelper;
    private Observation observation;
    private static final int PICK_IMAGE_REQUEST = 1;

    private static final String OBSERVATION_REGEX = "^[a-zA-Z0-9 _-]{1,150}$";

    private static final String ADDITIONAL_COMMENTS_REGEX = ".{0,100}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_observation);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the Activity when the back button is pressed
            }
        });

        editTextObservation = findViewById(R.id.editTextObservation);
        editTextTimeOfObservation = findViewById(R.id.editTextTimeOfObservation);
        editTextTimeOfObservation.setFocusable(false);
        editTextTimeOfObservation.setFocusableInTouchMode(false);
        editTextAdditionalComments = findViewById(R.id.editTextAdditionalComments);
        buttonSaveObservation = findViewById(R.id.buttonSaveObservation);
        imageViewObservation = findViewById(R.id.imageViewObservation); // Add ImageView
        databaseHelper = new DatabaseHelper(this);

        observation = (Observation) getIntent().getSerializableExtra("observation");

        if (observation != null) {
            editTextObservation.setText(observation.getObservation());
            editTextTimeOfObservation.setText(observation.getTimeOfObservation());
            editTextAdditionalComments.setText(observation.getAdditionalComments());

            // Display image from the observation
            byte[] imageBytes = observation.getImageBytes();
            if (imageBytes != null && imageBytes.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imageViewObservation.setImageBitmap(bitmap);
            }
        }

        editTextTimeOfObservation.setOnClickListener(v -> showDateTimePickerDialog());
        buttonSaveObservation.setOnClickListener(view -> saveUpdatedObservation());

        imageViewObservation.setOnClickListener(view -> selectImage());
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                imageViewObservation.setImageBitmap(bitmap);

                // Convert Bitmap to byte array to store in the Observation object
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // Update the observation object with the new image
                if (observation != null) {
                    observation.setImageBytes(byteArray);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveUpdatedObservation() {
        // Get hike ID from the observation
        int hikeId = observation.getHikeId();

        // Save data from EditText to observation
        String updatedObservation = editTextObservation.getText().toString();
        String updatedTimeOfObservation = editTextTimeOfObservation.getText().toString();
        String updatedAdditionalComments = editTextAdditionalComments.getText().toString();

        if (observation != null) {
            observation.setObservation(updatedObservation);
            observation.setTimeOfObservation(updatedTimeOfObservation);
            observation.setAdditionalComments(updatedAdditionalComments);
            if (!updatedObservation.matches(OBSERVATION_REGEX)) {
                Toast.makeText(EditObservationActivity.this, "Invalid observation format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!updatedAdditionalComments.matches(ADDITIONAL_COMMENTS_REGEX)) {
                Toast.makeText(EditObservationActivity.this, "Invalid additional comments format", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(updatedObservation)) {
                Toast.makeText(EditObservationActivity.this, "Please enter observation", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(updatedTimeOfObservation)) {
                Toast.makeText(EditObservationActivity.this, "Please select time of observation", Toast.LENGTH_SHORT).show();
                return;
            }


            // Convert Bitmap to byte array to store in the Observation object
            Bitmap bitmap = ((BitmapDrawable) imageViewObservation.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            // Update the observation object with the new image
            observation.setImageBytes(byteArray);

            // Save observation to the database
            boolean isUpdated = databaseHelper.updateObservation(observation);

            if (isUpdated) {
                // Check and display Hike ID
                Toast.makeText(EditObservationActivity.this, "Observation update successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditObservationActivity.this, ObservationsListActivity.class);
                intent.putExtra("hikeId", getIntent().getIntExtra("hikeId", -1));
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(EditObservationActivity.this, "Failed to update observation", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("observation", observation);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && observation == null) {
            observation = (Observation) savedInstanceState.getSerializable("observation");
        }
    }
    private void showDateTimePickerDialog() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int currentYear = calendar.get(java.util.Calendar.YEAR);
        int currentMonth = calendar.get(java.util.Calendar.MONTH);
        int currentDay = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        int currentHour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(java.util.Calendar.MINUTE);

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
                                EditObservationActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        // Save the selected time
                                        final int selectedHour = hourOfDay;
                                        final int selectedMinute = minute;

                                        // Display the selected date and time
                                        String selectedDateTime ="Date:" + " " + selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear + "\nTime:" + " " + selectedHour + ":" + selectedMinute;
                                        editTextTimeOfObservation.setText(selectedDateTime);
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
}
