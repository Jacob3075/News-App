package com.jacob.newsapp.ui.fragments.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jacob.newsapp.databinding.SearchArticlesTabFragmentBinding;
import com.jacob.newsapp.viewmodels.SearchPageViewModel;

public class SearchArticlesTab extends Fragment {

    private SearchPageViewModel viewModel;
    private SearchArticlesTabFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SearchArticlesTabFragmentBinding.inflate(inflater, container, false);
        ConstraintLayout root = binding.getRoot();

        return root;
    }

    private void setUpTextView() {
        viewModel.getSubmitted().observe(getViewLifecycleOwner(), submitted -> {
            String value = viewModel.getQuery().getValue();
            if (submitted) {
                value = value != null ? value : "";
                binding.textView.setText(value);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SearchPageViewModel.class);

        setUpTextView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}