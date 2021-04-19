package com.jacob.newsapp.ui.fragments;

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
import com.jacob.newsapp.adapters.SavedSourcesAdapter;
import com.jacob.newsapp.databinding.SavedSourcesPageFragmentBinding;
import com.jacob.newsapp.viewmodels.SavedSourcesPageViewModel;

import java.util.List;

public class SavedSourcesPage extends Fragment {

    private SavedSourcesPageViewModel viewModel;
    private SavedSourcesPageFragmentBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = SavedSourcesPageFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SavedSourcesPageViewModel.class);
        setUpUI();
    }

    private void setUpUI() {
        viewModel.getSavedSources().observe(getViewLifecycleOwner(), this::updateSavedSourcesList);
    }

    private void updateSavedSourcesList(List<String> savedSources) {
        RecyclerView recyclerView = binding.recyclerView;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        SavedSourcesAdapter savedCategoriesAdapter = new SavedSourcesAdapter(savedSources);
        recyclerView.setAdapter(savedCategoriesAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
