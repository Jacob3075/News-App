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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jacob.newsapp.R;
import com.jacob.newsapp.adapters.RecyclerViewAdapter;
import com.jacob.newsapp.adapters.RecyclerViewModel;
import com.jacob.newsapp.databinding.HomePageFragmentBinding;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.models.MediaStackResponse;
import com.jacob.newsapp.ui.ArticleCard;
import com.jacob.newsapp.viewmodels.HomePageViewModal;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends Fragment {


    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    List<RecyclerViewModel> articlecards;
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

    private void initData() {




    }


    private void initRecyclerView() {


        articlecards=new ArrayList<>();


        articlecards.add(new RecyclerViewModel(R.drawable.levi_bald,"abc.com","lets get it",12));
        articlecards.add(new RecyclerViewModel(R.drawable.levi_bald,"abc.com","lets get it",12));


        recyclerView=binding.recyclerview;
        layoutManager= new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new RecyclerViewAdapter(articlecards);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void setUpButtons() {
//        binding.btnOpenArticle.setOnClickListener(viewModel::testFireStoreRead);
        binding.btnOpenArticle.setOnClickListener(this::openArticleCard);
        binding.button.setOnClickListener(viewModel::openArticle);
        binding.searchButton.setOnClickListener(viewModel::searchButtonClicked);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HomePageViewModal.class);

//        getLatestNews();
        setUpButtons();
        initRecyclerView();
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