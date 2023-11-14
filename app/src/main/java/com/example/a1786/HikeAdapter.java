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

import java.util.List;

public class HikeAdapter extends ArrayAdapter<Hiking> {

    public HikeAdapter(Context context, List<Hiking> hikes) {
        super(context, 0, hikes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hiking hike = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hike_item, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.textViewName);
        TextView tvLocation = convertView.findViewById(R.id.textViewHikeLocation);


        tvName.setText(hike.getName());
        tvLocation.setText(hike.getLocation());
        // Đặt các trường dữ liệu khác tại đây

        return convertView;
    }
}