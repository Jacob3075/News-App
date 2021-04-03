package com.jacob.newsapp.ui.fragments.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jacob.newsapp.databinding.SearchCategoriesTabFragmentBinding;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.models.MediaStackResponse;
import com.jacob.newsapp.viewmodels.SearchPageViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class SearchCategoriesTab extends Fragment {

    private SearchPageViewModel viewModel;
    private SearchCategoriesTabFragmentBinding binding;

    public static SearchCategoriesTab newInstance() {
        return new SearchCategoriesTab();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SearchCategoriesTabFragmentBinding.inflate(inflater, container, false);
        ConstraintLayout root = binding.getRoot();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SearchPageViewModel.class);

        setUpTextView();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        viewModel.getNewsByCategory().observe(getViewLifecycleOwner(), this::updateRecyclerViewWithResults);
    }

    private void setUpTextView() {
        viewModel.getSubmitted().observe(getViewLifecycleOwner(), this::submitQueryIfNotEmpty);
    }

    private void submitQueryIfNotEmpty(Boolean submitted) {
        String categoryQuery = viewModel.getQuery().getValue();
        if (submitted && !categoryQuery.isEmpty()) {
            viewModel.searchNewsByCategory(categoryQuery);
        } else if (categoryQuery.isEmpty()) {
//            Toast.makeText(getContext(), "Enter a search query", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateRecyclerViewWithResults(MediaStackResponse mediaStackResponse) {
        List<Article> articles = mediaStackResponse.getArticles();
        List<String> collect = articles.stream()
                .filter(Article::notContainsNull)
                .limit(15)
                .map(Article::getCategory)
                .collect(Collectors.toList());
//                .forEach(x -> Log.d("CATEGORY", x.getTitle() + ", " + x.getSource() + ", " + x.getCategory()));
        binding.textView.setText(collect.toString());
    }
}