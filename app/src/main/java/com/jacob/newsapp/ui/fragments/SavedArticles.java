package com.jacob.newsapp.ui.fragments;

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
import com.jacob.newsapp.databinding.SavedArticlesFragmentBinding;
import com.jacob.newsapp.viewmodels.SavedArticlesViewModel;
import org.jetbrains.annotations.NotNull;

public class SavedArticles extends Fragment {

    private SavedArticlesFragmentBinding binding;
    private SavedArticlesViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = SavedArticlesFragmentBinding.inflate(inflater, container, false);
        ConstraintLayout root = binding.getRoot();

        setUpButtons();

        return root;
    }

    private void setUpButtons() {
        binding.searchButton.setOnClickListener(this::openSearchPage);
    }

    private void openSearchPage(View view) {
        Navigation.findNavController(view).navigate(R.id.savedArticles_to_searchPage);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SavedArticlesViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
