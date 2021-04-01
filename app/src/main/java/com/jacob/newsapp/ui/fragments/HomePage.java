package com.jacob.newsapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = HomePageFragmentBinding.inflate(inflater, container, false);
        ConstraintLayout root = binding.getRoot();

//        binding.btnOpenArticle.setOnClickListener(this::openArticle);
        binding.btnOpenArticle.setOnClickListener(this::getArticlesFromSource);
        binding.button.setOnClickListener(this::openArticleCard);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HomePageViewModal.class);
    }

    private void openArticleCard(View onClick) {
        Intent intent = new Intent(getActivity(), ArticleCard.class);
        startActivity(intent);
    }

    private void openArticle(View view) {
        Navigation.findNavController(view).navigate(R.id.homePage_to_articleViewer);
    }

    private void getArticlesFromSource(View view) {
        LiveData<MediaStackResponse> newsBySource = viewModel.getTrendingNews();
        newsBySource.observe(getViewLifecycleOwner(), mediaStackResponse -> {
            List<Article> articles = mediaStackResponse.getArticles();
            articles.stream()
                    .filter(Article::notContainsNull)
                    .limit(15)
                    .forEach(System.out::println);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}