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
import com.jacob.newsapp.adapters.PagedNewsListAdapter;
import com.jacob.newsapp.databinding.HomePageFragmentBinding;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.viewmodels.HomePageViewModal;
import org.jetbrains.annotations.NotNull;

import static com.jacob.newsapp.adapters.PagedNewsListAdapter.CardViewModelFunctions;

public class HomePage extends Fragment {

    private HomePageFragmentBinding binding;
    private HomePageViewModal viewModel;

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = HomePageFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HomePageViewModal.class);

        setUpUI();
    }

    private void setUpUI() {
        binding.searchButton.setOnClickListener(this::searchButtonClicked);
        setUpRecyclerView();
    }

    public void searchButtonClicked(View view) {
        Navigation.findNavController(view).navigate(R.id.homePage_to_searchPage);
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = binding.recyclerview;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

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

        PagedNewsListAdapter adapter = new PagedNewsListAdapter(listener);

        viewModel.getLatestNews().removeObservers(getViewLifecycleOwner());
        viewModel.getLatestNews().observe(getViewLifecycleOwner(), adapter::submitList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
