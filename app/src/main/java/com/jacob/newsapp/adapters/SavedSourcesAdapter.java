package com.jacob.newsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jacob.newsapp.R;
import com.jacob.newsapp.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

public class SavedSourcesAdapter
        extends RecyclerView.Adapter<SavedSourcesAdapter.SavedSourceRowItemViewHolder> {

    private List<String> data = new ArrayList<>();

    public SavedSourcesAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public SavedSourceRowItemViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.saved_source_row_item, parent, false);
        return new SavedSourceRowItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedSourceRowItemViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class SavedSourceRowItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtSource;

        public SavedSourceRowItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSource = itemView.findViewById(R.id.txtSource);
        }

        public void bind(String source) {
            txtSource.setText(Utils.capitalize(source));
        }
    }
}
