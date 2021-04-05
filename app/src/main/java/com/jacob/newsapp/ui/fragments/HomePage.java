package com.jacob.newsapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jacob.newsapp.adapters.RecyclerViewAdapter;
import com.jacob.newsapp.databinding.HomePageFragmentBinding;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.models.MediaStackResponse;
import com.jacob.newsapp.ui.ArticleCard;
import com.jacob.newsapp.viewmodels.HomePageViewModal;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends Fragment {


    RecyclerViewAdapter adapter;

    private HomePageFragmentBinding binding;
    private HomePageViewModal viewModel;
    private List<Article> articles = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = HomePageFragmentBinding.inflate(inflater, container, false);
        ConstraintLayout root = binding.getRoot();

        return root;
    }

    private void setUpButtons() {
        binding.searchButton.setOnClickListener(viewModel::searchButtonClicked);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HomePageViewModal.class);

        setUpButtons();
        getLatestNews();
    }

    private void openArticleCard(View onClick) {
        Intent intent = new Intent(getActivity(), ArticleCard.class);
        startActivity(intent);
    }

    private void getLatestNews() {
        viewModel.getTrendingNews();
        viewModel.getLatestNews().observe(getViewLifecycleOwner(), this::setUpRecyclerView);
    }

    private void setUpRecyclerView(MediaStackResponse mediaStackResponse) {
        articles = mediaStackResponse.getArticles();

        RecyclerView recyclerView = binding.recyclerview;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter(articles
                .stream()
                .filter(Article::notContainsNull)
                .collect(Collectors.toList()));

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}