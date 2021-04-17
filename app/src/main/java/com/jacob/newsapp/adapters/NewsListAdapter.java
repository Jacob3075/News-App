package com.jacob.newsapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jacob.newsapp.R;
import com.jacob.newsapp.models.Article;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.jacob.newsapp.adapters.PagedNewsListAdapter.CardViewModelFunctions;
import static com.jacob.newsapp.adapters.PagedNewsListAdapter.NewsArticleItemViewHolder;

public class NewsListAdapter extends RecyclerView.Adapter<NewsArticleItemViewHolder> {

    private final CardViewModelFunctions viewModelFunctions;
    private List<Article> articles = new ArrayList<>();

    public NewsListAdapter(List<Article> articles, CardViewModelFunctions viewModelFunctions) {
        this.articles = articles;
        this.viewModelFunctions = viewModelFunctions;
    }

    @NonNull
    @NotNull
    @Override
    public NewsArticleItemViewHolder onCreateViewHolder(
            @NonNull @NotNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.article_card, parent, false);
        return new NewsArticleItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NewsArticleItemViewHolder holder, int position) {
        holder.bind(articles.get(position), viewModelFunctions);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
