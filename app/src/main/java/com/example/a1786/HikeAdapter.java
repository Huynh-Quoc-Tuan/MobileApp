package com.example.a1786;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class HikeAdapter extends BaseAdapter {
    private Context context;
    private List<Hiking> hikes;

    public HikeAdapter(Context context, List<Hiking> hikes) {
        super();
    }

    @Override
    public int getCount() {
        return hikes.size();
    }

    @Override
    public Object getItem(int position) {
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

        Hiking hike = (Hiking) getItem(position);
        // Cập nhật dữ liệu vào convertView dựa trên đối tượng hike
        TextView tvName = convertView.findViewById(R.id.textViewName);
//        TextView tvLocation = convertView.findViewById(R.id.textViewLocation);
//        TextView tvDate = convertView.findViewById(R.id.textViewDate);
//        TextView tvParking = convertView.findViewById(R.id.textViewParking);
//        TextView tvLength = convertView.findViewById(R.id.textViewLength);

        tvName.setText(hike.getName());
//        tvLocation.setText(hike.getLocation());
//        tvDate.setText(hike.getDate());
//        tvParking.setText(hike.getParking());
//        tvLength.setText(hike.getLength());

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Mở màn hình chi tiết khi item được nhấn
//                Intent intent = new Intent(context, ViewDetailHikeActivity.class);
//                intent.putExtra("selected_hike", hike); // Truyền đối tượng hike vào Intent
//                context.startActivity(intent);
//            }
//        });

        return convertView;
    }

    public void setHikes(List<Hiking> hikeList) {
        this.hikes = hikeList;
        notifyDataSetChanged(); // Thông báo cho adapter cập nhật giao diện
    }


//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Hiking hike = getItem(position);
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hike_item_view, parent, false);
//        }
//
//        TextView tvName = convertView.findViewById(R.id.textViewName);
//        TextView tvLocation = convertView.findViewById(R.id.textViewLocation);
//        TextView tvDate = convertView.findViewById(R.id.textViewDate);
//        TextView tvParking = convertView.findViewById(R.id.textViewParking);
//        TextView tvLength = convertView.findViewById(R.id.textViewLength);
//
//        tvName.setText(hike.getName());
//        tvLocation.setText(hike.getLocation());
//        tvDate.setText(hike.getDate());
//        tvParking.setText(hike.getParking());
//        tvLength.setText(hike.getLength());
//        // Đặt các trường dữ liệu khác tại đây
//
//        return convertView;
//    }
}