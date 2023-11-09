package com.example.a1786;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.ViewHolder> {

    private List<Hiking> hikeList;
    private Context context;
    private DatabaseHelper databaseHelper;

    public HikeAdapter(Context context, List<Hiking> hikeList, DatabaseHelper databaseHelper) {
        this.context = context;
        this.hikeList = hikeList;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public HikeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hike_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeAdapter.ViewHolder holder, int position) {
        Hiking hike = hikeList.get(position);
        holder.textViewHikeName.setText(hike.getName());
        holder.textViewHikeLocation.setText(hike.getLocation());

    }

    @Override
    public int getItemCount() {
        return hikeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewHikeName, textViewHikeLocation, textViewHikeDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewHikeName = itemView.findViewById(R.id.textViewHikeName);
            textViewHikeLocation = itemView.findViewById(R.id.textViewHikeLocation);
            textViewHikeDescription = itemView.findViewById(R.id.textViewHikeDescription);
        }
    }
}

