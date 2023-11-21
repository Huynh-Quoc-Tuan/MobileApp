package com.example.a1786;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ObservationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.observation_detail);

        // Initialize views
        TextView textObservationText = findViewById(R.id.textObservationText);
        TextView textTimeOfObservation = findViewById(R.id.textTimeOfObservation);
        TextView textAdditionalComments = findViewById(R.id.textAdditionalComments);
        ImageView imageObservation = findViewById(R.id.imageObservation);

        // Get Observation data from Intent
        Observation observation = (Observation) getIntent().getSerializableExtra("observation");

        // Check if the observation is not null and populate the views with data
        if (observation != null) {
            textObservationText.setText(observation.getObservation());
            textTimeOfObservation.setText(observation.getTimeOfObservation());
            textAdditionalComments.setText(observation.getAdditionalComments());

            // Display the image (only if there is image data)
            byte[] imageBytes = observation.getImageBytes();
            if (imageBytes != null && imageBytes.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                imageObservation.setImageBitmap(bitmap);
            } else {
                // If no image is available, use a placeholder image
                imageObservation.setImageResource(R.drawable.ic_placeholder);
            }
        }

        // Handle click event for the back button
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finish the Activity when the back button is pressed
            }
        });
    }
}
