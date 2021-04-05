package com.jacob.newsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jacob.newsapp.R;

import java.util.ArrayList;

public class SavedCategoriesAdapter extends RecyclerView.Adapter<SavedCategoriesAdapter.SaveCategoryRowItemViewHolder> {

    private final ArrayList<String> data;

    public SavedCategoriesAdapter(ArrayList<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public SaveCategoryRowItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_category_row_item, parent, false);
        return new SaveCategoryRowItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaveCategoryRowItemViewHolder holder, int position) {
        holder.txtCategory.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class SaveCategoryRowItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory;

        public SaveCategoryRowItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
        }
    }
}
