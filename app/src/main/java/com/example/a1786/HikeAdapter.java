package com.example.a1786;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HikeAdapter extends ArrayAdapter<Hiking> {

    public HikeAdapter(Context context, List<Hiking> hikes) {
        super(context, 0, hikes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hiking hike = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_view_hiking, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.textViewName);
        TextView tvLocation = convertView.findViewById(R.id.textViewLocation);
        TextView tvDate = convertView.findViewById(R.id.textViewDate);
        TextView tvParking = convertView.findViewById(R.id.textViewParking);
        TextView tvLength = convertView.findViewById(R.id.textViewLength);



        tvName.setText(hike.getName());
        tvLocation.setText(hike.getLocation());
        tvDate.setText(hike.getDate());
        tvParking.setText(hike.getParking());
        tvLength.setText(hike.getLength());
        // Đặt các trường dữ liệu khác tại đây

        return convertView;
    }
}