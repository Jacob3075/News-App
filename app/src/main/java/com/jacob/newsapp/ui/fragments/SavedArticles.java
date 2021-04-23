package com.jacob.newsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jacob.newsapp.R;
import com.jacob.newsapp.adapters.NewsListAdapter;
import com.jacob.newsapp.databinding.SavedArticlesFragmentBinding;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.viewmodels.SavedArticlesViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.jacob.newsapp.adapters.PagedNewsListAdapter.CardViewModelFunctions;

public class SavedArticles extends Fragment {

    private SavedArticlesFragmentBinding binding;
    private SavedArticlesViewModel viewModel;

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = SavedArticlesFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SavedArticlesViewModel.class);
        setUpUI();
    }

    private void setUpUI() {
        binding.searchButton.setOnClickListener(this::openSearchPage);
        viewModel.getSavedArticles().removeObservers(getViewLifecycleOwner());
        viewModel.getSavedArticles().observe(getViewLifecycleOwner(), this::updateSavedListItems);
    }

    private void updateSavedListItems(List<Article> articles) {
        CardViewModelFunctions listener =
                new CardViewModelFunctions() {
                    @Override
                    public boolean onArticleClicked(Article article) {
                        return viewModel.saveArticleButtonPressed(article);
                    }

                    @Override
                    public boolean isArticleSaved(Article article) {
                        return viewModel.isArticleSaved(article);
                    }
                };

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerView.setLayoutManager(layoutManager);

        NewsListAdapter adapter = new NewsListAdapter(articles, listener);
        binding.recyclerView.setAdapter(adapter);
    }

    private void openSearchPage(View view) {
        Navigation.findNavController(view).navigate(R.id.savedArticles_to_searchPage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
