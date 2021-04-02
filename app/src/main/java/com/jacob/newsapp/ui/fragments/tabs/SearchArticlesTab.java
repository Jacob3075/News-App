package com.jacob.newsapp.ui.fragments.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jacob.newsapp.databinding.SearchArticlesTabFragmentBinding;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.models.MediaStackResponse;
import com.jacob.newsapp.viewmodels.SearchPageViewModel;

import java.util.List;

public class SearchArticlesTab extends Fragment {

    private SearchPageViewModel viewModel;
    private SearchArticlesTabFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SearchArticlesTabFragmentBinding.inflate(inflater, container, false);
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
//        viewModel.getSubmitted().observe(getViewLifecycleOwner(), this::submitQueryIfNotEmpty);
        viewModel.getNewsByKeyWord().observe(getViewLifecycleOwner(), this::updateRecyclerViewWithResults);
    }

    private void setUpTextView() {
        viewModel.getSubmitted().observe(getViewLifecycleOwner(), this::submitQueryIfNotEmpty);
    }

    private void submitQueryIfNotEmpty(Boolean submitted) {
        String query = viewModel.getQuery().getValue();
        if (submitted) {
            binding.textView.setText(query);
//        if (submitted && !query.isEmpty()) {
//            viewModel.searchForNews(query);
        } else if (query.isEmpty()) {
            Toast.makeText(getContext(), "Enter a search query", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateRecyclerViewWithResults(MediaStackResponse mediaStackResponse) {
        List<Article> articles = mediaStackResponse.getArticles();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}