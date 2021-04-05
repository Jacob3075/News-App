package com.jacob.newsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jacob.newsapp.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Viewholder> {

    private List<RecyclerViewModel> articlecards;

    public RecyclerViewAdapter(List<RecyclerViewModel> articlecards){
        this.articlecards=articlecards;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_article_card,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.Viewholder holder, int position) {

        int articleimg=articlecards.get(position).getImgArticleImage();
        String source=articlecards.get(position).getTvSource();
        String articletitle=articlecards.get(position).getTvTitle();
        int savearticle=articlecards.get(position).getBtnSaveArticle();

        holder.setdata(articleimg,source,articletitle,savearticle);

    }

    @Override
    public int getItemCount() {
        return articlecards.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView articleimg1;
        private Button saveartlcle1;
        private TextView title1;
        private TextView source1;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            articleimg1=itemView.findViewById(R.id.imgArticleImage);
            saveartlcle1=itemView.findViewById(R.id.btnSaveArticle);
            title1=itemView.findViewById(R.id.tvTitle);
            source1=itemView.findViewById(R.id.tvSource);







        }

        public void setdata(int articleimg, String source, String articletitle, int savearticle) {

            articleimg1.setImageResource(articleimg);
            source1.setText(source);
            title1.setText(articletitle);


        }
    }
}
