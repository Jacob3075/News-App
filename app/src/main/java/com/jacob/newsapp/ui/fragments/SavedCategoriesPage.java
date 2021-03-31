package com.jacob.newsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jacob.newsapp.R;
import com.jacob.newsapp.viewmodels.SavedCategoriesPageViewModel;

public class SavedCategoriesPage extends Fragment {

    private SavedCategoriesPageViewModel mViewModel;

    public static SavedCategoriesPage newInstance() {
        return new SavedCategoriesPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.saved_categories_page_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SavedCategoriesPageViewModel.class);
        // TODO: Use the ViewModel
    }

}