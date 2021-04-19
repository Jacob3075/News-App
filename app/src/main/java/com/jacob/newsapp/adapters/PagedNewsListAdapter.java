package com.jacob.newsapp.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.jacob.newsapp.R;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.ui.fragments.HomePageDirections;
import com.jacob.newsapp.ui.fragments.HomePageDirections.HomePageToArticleViewer;
import com.jacob.newsapp.ui.fragments.SavedArticlesDirections;
import com.jacob.newsapp.ui.fragments.SearchPageDirections;
import com.jacob.newsapp.utilities.Utils;
import org.jetbrains.annotations.NotNull;

import static com.jacob.newsapp.adapters.PagedNewsListAdapter.NewsArticleItemViewHolder;

public class PagedNewsListAdapter extends PagedListAdapter<Article, NewsArticleItemViewHolder> {
    private final CardViewModelFunctions viewModelFunctions;
    private final Page page;

    public PagedNewsListAdapter(CardViewModelFunctions viewModelFunctions, Page page) {
        super(DIFF_CALLBACK);
        this.viewModelFunctions = viewModelFunctions;
        this.page = page;
    }

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

    @Override
    public void onBindViewHolder(@NonNull NewsArticleItemViewHolder holder, int position) {
        holder.bind(getItem(position), viewModelFunctions, page);
    }

    @NonNull
    @Override
    public NewsArticleItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.article_card, parent, false);
        return new NewsArticleItemViewHolder(view);
    }

    public enum Page {
        HOME,
        SAVED,
        SEARCH
    }

    public interface CardViewModelFunctions {

        /**
         * @param article to save or remove from the database
         * @return true if the article was saved and false if the article was removed.
         */
        boolean onArticleClicked(Article article);

        /**
         * @param article to check if is already present in the database.
         * @return True if the article is present and false if the article is not present.
         */
        boolean isArticleSaved(Article article);
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

        public void bind(
                @NotNull Article item,
                @NotNull CardViewModelFunctions viewModelFunctions,
                Page page) {
            articleTitle.setText(item.getTitle());
            articleSource.setText(Utils.capitalize(item.getSource()));
            updateSaveIcon(viewModelFunctions.isArticleSaved(item));
            Glide.with(root).load(item.getImage()).fitCenter().into(articleImage);

            root.setOnClickListener(
                    view -> {
                        NavDirections action = getActionForPage(item, page);

                        Navigation.findNavController(view).navigate(action);
                    });

            saveArticle.setOnClickListener(
                    view -> {
                        boolean isArticleSaved = viewModelFunctions.onArticleClicked(item);
                        updateSaveIcon(isArticleSaved);
                    });
        }

        private NavDirections getActionForPage(Article item, @NotNull Page page) {
            Log.d(getClass().getName(), "getActionForPage: " + page);
            switch (page) {
                case SEARCH:
                    return SearchPageDirections.actionSearchPageToArticleViewer(item);
                case SAVED:
                    return SavedArticlesDirections.savedArticlesToArticleViewer(item);
                default:
                    return HomePageDirections.homePageToArticleViewer(item);
            }
        }

        @NotNull
        private HomePageToArticleViewer homePage(@NotNull Article item) {
            return HomePageDirections.homePageToArticleViewer(item);
        }

        private void updateSaveIcon(boolean isArticleSaved) {
            if (isArticleSaved) {
                saveArticle.setImageResource(R.drawable.saved_icon);
            } else {
                saveArticle.setImageResource(R.drawable.save_icon);
            }
        }
    }
}
