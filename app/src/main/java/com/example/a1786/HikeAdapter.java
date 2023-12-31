package com.example.a1786;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HikeAdapter extends ArrayAdapter<Hiking> {
    private Context context;
    private List<Hiking> hikes;

    public HikeAdapter(Context context, List<Hiking> hikes) {
        super(context, 0, hikes);
        this.context = context;
        this.hikes = hikes;
    }

    @Override
    public int getCount() {
        return hikes.size();
    }

    @Override
    public Hiking getItem(int position) {
        return hikes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.hike_item_view, parent, false);
        }

        Hiking hike = getItem(position);
        int id = hike.getId();

        // Update data into convertView based on the hike object
        TextView tvName = convertView.findViewById(R.id.textViewName);
        TextView tvLocation = convertView.findViewById(R.id.textViewLocation);
        TextView tvDate = convertView.findViewById(R.id.textViewDate);
        TextView tvParking = convertView.findViewById(R.id.textViewParking);
        TextView tvLength = convertView.findViewById(R.id.textViewLength);

        tvName.setText(hike.getName()); // Set the value of the hike's Id
        tvLocation.setText(hike.getLocation());
        tvDate.setText(hike.getDate());
        tvParking.setText(hike.getParking());
        tvLength.setText(hike.getLength());

        return convertView;
    }

    public void setHikes(List<Hiking> hikeList) {
        this.hikes = hikeList;
        notifyDataSetChanged(); // Notify the adapter to update the interface
    }
}