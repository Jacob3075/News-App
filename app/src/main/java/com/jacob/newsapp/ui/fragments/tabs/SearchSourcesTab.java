package com.jacob.newsapp.ui.fragments.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jacob.newsapp.adapters.PagedNewsListAdapter;
import com.jacob.newsapp.adapters.PagedNewsListAdapter.Page;
import com.jacob.newsapp.databinding.SearchSourcesTabFragmentBinding;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.viewmodels.SearchPageViewModel;

import static com.jacob.newsapp.adapters.PagedNewsListAdapter.CardViewModelFunctions;

public class SearchSourcesTab extends Fragment {

    private SearchPageViewModel viewModel;
    private SearchSourcesTabFragmentBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = SearchSourcesTabFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SearchPageViewModel.class);

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = binding.recyclerView;
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

        PagedNewsListAdapter adapter = new PagedNewsListAdapter(listener, Page.SEARCH);

        viewModel.getPagedListLiveData().removeObservers(getViewLifecycleOwner());
        viewModel.getPagedListLiveData().observe(getViewLifecycleOwner(), adapter::submitList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
