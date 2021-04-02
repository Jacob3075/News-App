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
import androidx.navigation.Navigation;

import com.jacob.newsapp.R;
import com.jacob.newsapp.databinding.HomePageFragmentBinding;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.models.MediaStackResponse;
import com.jacob.newsapp.ui.ArticleCard;
import com.jacob.newsapp.viewmodels.HomePageViewModal;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends Fragment {

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

        setUpButtons();

        return root;
    }

    private void setUpButtons() {
        //        binding.btnOpenArticle.setOnClickListener(this::openArticle);
        binding.btnOpenArticle.setOnClickListener(this::openArticleCard);
        binding.button.setOnClickListener(this::openArticle);
        binding.searchButton.setOnClickListener(this::openSearchPage);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HomePageViewModal.class);

//        getLatestNews();
    }

    private void openArticleCard(View onClick) {
        Intent intent = new Intent(getActivity(), ArticleCard.class);
        startActivity(intent);
    }

    private void openArticle(View view) {
        Navigation.findNavController(view).navigate(R.id.homePage_to_articleViewer);
    }

    private void getLatestNews() {
        viewModel.getTrendingNews();
        viewModel.getLastestNews().observe(getViewLifecycleOwner(), this::setUpRecyclerView);
    }

    private void setUpRecyclerView(MediaStackResponse mediaStackResponse) {
        articles = mediaStackResponse.getArticles();
        articles.stream()
                .filter(Article::notContainsNull)
                .limit(15)
                .forEach(System.out::println);
    }

    private void openSearchPage(View view) {
        Navigation.findNavController(view).navigate(R.id.homePage_to_searchPage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}