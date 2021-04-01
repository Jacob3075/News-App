package com.jacob.newsapp.ui.fragments.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jacob.newsapp.R;
import com.jacob.newsapp.viewmodels.tabs.SearchCategoriesTabViewModel;

public class SearchCategoriesTab extends Fragment {

    private SearchCategoriesTabViewModel mViewModel;

    public static SearchCategoriesTab newInstance() {
        return new SearchCategoriesTab();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_categories_tab_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchCategoriesTabViewModel.class);
        // TODO: Use the ViewModel
    }

}