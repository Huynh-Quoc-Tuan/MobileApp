package com.example.a1786;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ObservationListAdapter extends RecyclerView.Adapter<ObservationListAdapter.ObservationViewHolder> {
    private Context context;
    private List<Observation> observationList;
    private OnItemClickListener onItemClickListener;

    private DatabaseHelper databaseHelper;

    public ObservationListAdapter(Context context, List<Observation> observationList, OnItemClickListener listener, DatabaseHelper dbHelper) {
        this.context = context;
        this.observationList = observationList;
        this.onItemClickListener = listener;
        this.databaseHelper = dbHelper;
    }
    public void setObservations(List<Observation> observations) {
        this.observationList = observations;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ObservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.observation_item, parent, false);
        return new ObservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationViewHolder holder, int position) {
        Observation observation = observationList.get(position);

        holder.observationText.setText(observation.getObservation());
        holder.observationTime.setText(observation.getTimeOfObservation());
        holder.additionalComments.setText(observation.getAdditionalComments());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(observation);
                }
            }
        });

        TextView textViewMenu = holder.itemView.findViewById(R.id.textViewMenu);
        // Set up popup menu
        textViewMenu.setOnClickListener(view -> showPopupMenu(view, observation));
    }

    @Override
    public int getItemCount() {
        return observationList.size();
    }

    public static class ObservationViewHolder extends RecyclerView.ViewHolder {
        TextView observationText;
        TextView observationTime;
        TextView additionalComments;

        public ObservationViewHolder(@NonNull View itemView) {
            super(itemView);
            observationText = itemView.findViewById(R.id.observationText);
            observationTime = itemView.findViewById(R.id.observationTime);
            additionalComments = itemView.findViewById(R.id.additionalComments);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Observation observation);
    }

    private void showPopupMenu(View view, Observation observation) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.observation_menu);

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_edit_observation) {
                openEditObservationActivity(observation);
                return true;
            } else if (item.getItemId() == R.id.menu_delete_observation) {
                deleteObservation(observation);
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

    private void openEditObservationActivity(Observation observation) {
        Intent intent = new Intent(context, EditObservationActivity.class);
        intent.putExtra("observation", observation);
        context.startActivity(intent);
    }

    private void deleteObservation(final Observation observation) {
        if (observation != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context); // Sử dụng context của adapter
            builder.setMessage("Are you sure you want to delete this observation?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            databaseHelper.deleteObservation(observation.getObservationId());
                            observationList.remove(observation); // Sử dụng observationList thay vì observations
                            notifyDataSetChanged(); // Gọi notifyDataSetChanged trực tiếp từ adapter
                            Toast.makeText(context, "Observation deleted", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            // Tạo và hiển thị AlertDialog
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            Toast.makeText(context, "Failed to delete observation", Toast.LENGTH_SHORT).show();
        }
    }

}
