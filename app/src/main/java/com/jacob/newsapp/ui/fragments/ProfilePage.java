package com.jacob.newsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.jacob.newsapp.R;
import com.jacob.newsapp.databinding.ProfilePageFragmentBinding;

import org.jetbrains.annotations.NotNull;

public class ProfilePage extends Fragment {

    private ProfilePageFragmentBinding binding;

    private void openSavedCategoriesPage(View view) {
        Navigation.findNavController(view).navigate(R.id.profilePage_to_savedCategoriesPage);
    }

    private void openSavedSourcesPage(View view) {
        Navigation.findNavController(view).navigate(R.id.profilePage_to_savedCategoriesPage);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ProfilePageFragmentBinding.inflate(inflater, container, false);

        ConstraintLayout root = binding.getRoot();

        setUpButtons();

        return root;
    }

    private void setUpButtons() {
        binding.savedCategoriesButton.setOnClickListener(this::openSavedCategoriesPage);
        binding.savedSourcesButton.setOnClickListener(this::openSavedSourcesPage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}