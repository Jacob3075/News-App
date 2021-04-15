package com.jacob.newsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.jacob.newsapp.R;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.ui.fragments.HomePageDirections;

import static com.jacob.newsapp.adapters.PagedNewsListAdapter.NewsArticleItemViewHolder;

public class PagedNewsListAdapter extends PagedListAdapter<Article, NewsArticleItemViewHolder> {
    private static final DiffUtil.ItemCallback<Article> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Article>() {
                @Override
                public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull Article oldItem, @NonNull Article newItem) {
                    return true;
                }
            };

    public PagedNewsListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public NewsArticleItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.article_card, parent, false);

        return new NewsArticleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsArticleItemViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class NewsArticleItemViewHolder extends RecyclerView.ViewHolder {
        private final MaterialCardView root;
        private final ConstraintLayout rootLayout;
        private final ImageView articleImage;
        private final TextView articleTitle;
        private final TextView articleSource;
        private final ImageButton saveArticle;

        public NewsArticleItemViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.articleCard);
            rootLayout = itemView.findViewById(R.id.cardLayout);
            articleTitle = itemView.findViewById(R.id.tvTitle);
            articleSource = itemView.findViewById(R.id.tvSource);
            saveArticle = itemView.findViewById(R.id.btnSaveArticle);
            articleImage = itemView.findViewById(R.id.imgArticleImage);
        }

        public void bind(Article item) {
            articleTitle.setText(item.getTitle());
            articleSource.setText(item.getSource());
            Glide.with(itemView).load(item.getImage()).fitCenter().into(articleImage);

            root.setOnClickListener(view -> {

                HomePageDirections.HomePageToArticleViewer action = HomePageDirections.homePageToArticleViewer();
                Navigation.findNavController(view).navigate(action);
            });
            saveArticle.setOnClickListener(view -> {
            });
        }
    }


}

