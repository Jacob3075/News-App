package com.jacob.newsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jacob.newsapp.R;

public class SavedSourcesAdapter extends RecyclerView.Adapter<SavedSourcesAdapter.MyViewHolder> {

    String[] data;
    Context context;

    public SavedSourcesAdapter(Context ct, String[] s1) {
        context = ct;
        data = s1;
    }

    @NonNull
    @Override
    public SavedSourcesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_saved_source, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull SavedSourcesAdapter.MyViewHolder holder, int position) {
        holder.txtSource.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtSource;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSource = itemView.findViewById(R.id.txtSource);
        }
    }
}
