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
import com.jacob.newsapp.adapters.SavedCategoriesAdapter;
import com.jacob.newsapp.databinding.SavedCategoriesPageFragmentBinding;
import com.jacob.newsapp.viewmodels.SavedCategoriesPageViewModel;

import java.util.List;

public class SavedCategoriesPage extends Fragment {

    private SavedCategoriesPageViewModel viewModel;
    private SavedCategoriesPageFragmentBinding binding;

    public static SavedCategoriesPage newInstance() {
        return new SavedCategoriesPage();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        binding = SavedCategoriesPageFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SavedCategoriesPageViewModel.class);
        setUpUI();
    }

    private void setUpUI() {
        viewModel.getSavedCategories().observe(getViewLifecycleOwner(), this::updateCategoriesList);
    }

    private void updateCategoriesList(List<String> savedCategories) {
        RecyclerView recyclerView = binding.recyclerView;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        SavedCategoriesAdapter savedCategoriesAdapter = new SavedCategoriesAdapter(savedCategories);
        recyclerView.setAdapter(savedCategoriesAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
