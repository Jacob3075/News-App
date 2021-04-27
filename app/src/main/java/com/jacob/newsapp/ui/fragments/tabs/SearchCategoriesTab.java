package com.jacob.newsapp.ui.fragments.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.jacob.newsapp.adapters.PagedNewsListAdapter;
import com.jacob.newsapp.databinding.SearchCategoriesTabFragmentBinding;
import com.jacob.newsapp.viewmodels.SearchPageViewModel;

public class SearchCategoriesTab extends Fragment {

    private SearchPageViewModel viewModel;
    private SearchCategoriesTabFragmentBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = SearchCategoriesTabFragmentBinding.inflate(inflater, container, false);
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

        PagedNewsListAdapter adapter =
                viewModel.setUpRecyclerView(viewModel.getPagedArticlesByCategoryLiveData());

        viewModel.getPagedArticlesByCategoryLiveData().removeObservers(getViewLifecycleOwner());
        viewModel
                .getPagedArticlesByCategoryLiveData()
                .observe(getViewLifecycleOwner(), adapter::submitList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
