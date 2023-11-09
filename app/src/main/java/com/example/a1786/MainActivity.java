package com.example.a1786;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button viewHikesButton, addHikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_main layout
        setContentView(R.layout.activity_main);

        // Initialize buttons
        viewHikesButton = findViewById(R.id.viewHikesButton);
        addHikeButton = findViewById(R.id.addHikeButton);

        // Set the onClick listener for the view hikes button
        viewHikesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the ViewHikesActivity when the button is clicked
                Intent intent = new Intent(MainActivity.this, ViewHikesActivity.class);
                startActivity(intent);
            }
        });

        // Set the onClick listener for the add hike button
        addHikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the AddHikeActivity when the button is clicked
                Intent intent = new Intent(MainActivity.this, AddHikeActivity.class);
                startActivity(intent);
            }
        });
    }
}


