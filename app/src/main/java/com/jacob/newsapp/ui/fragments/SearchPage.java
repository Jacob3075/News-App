package com.jacob.newsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jacob.newsapp.adapters.TabBarAdapter;
import com.jacob.newsapp.databinding.SearchPageFragmentBinding;
import com.jacob.newsapp.ui.fragments.tabs.SearchArticlesTab;
import com.jacob.newsapp.ui.fragments.tabs.SearchCategoriesTab;
import com.jacob.newsapp.ui.fragments.tabs.SearchSourcesTab;

import org.jetbrains.annotations.NotNull;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class SearchPage extends Fragment {
    private SearchPageFragmentBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = SearchPageFragmentBinding.inflate(inflater, container, false);
        ConstraintLayout root = binding.getRoot();

        setUpTabBar();

        return root;
    }

    private void setUpTabBar() {
        ViewPager viewPager = binding.viewPager;
        TabLayout tabLayout = binding.tabLayout;

        TabBarAdapter adapter = new TabBarAdapter(getParentFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        adapter.addFragment(new SearchArticlesTab(), "Articles");
        adapter.addFragment(new SearchSourcesTab(), "Sources");
        adapter.addFragment(new SearchCategoriesTab(), "Categories");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}