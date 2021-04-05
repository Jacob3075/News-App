package com.jacob.newsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jacob.newsapp.R;

import java.util.ArrayList;

public class SavedSourcesAdapter extends RecyclerView.Adapter<SavedSourcesAdapter.SavedSourceRowItemViewHolder> {

    private final ArrayList<String> data;

    public SavedSourcesAdapter(ArrayList<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public SavedSourceRowItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_source_row_item, parent, false);
        return new SavedSourceRowItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedSourceRowItemViewHolder holder, int position) {
        holder.txtSource.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class SavedSourceRowItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtSource;

        public SavedSourceRowItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSource = itemView.findViewById(R.id.txtSource);
        }
    }
}
