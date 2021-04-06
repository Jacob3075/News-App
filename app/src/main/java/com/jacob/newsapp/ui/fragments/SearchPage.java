package com.jacob.newsapp.ui.fragments;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jacob.newsapp.adapters.TabBarAdapter;
import com.jacob.newsapp.databinding.SearchPageFragmentBinding;
import com.jacob.newsapp.ui.fragments.tabs.SearchArticlesTab;
import com.jacob.newsapp.ui.fragments.tabs.SearchCategoriesTab;
import com.jacob.newsapp.ui.fragments.tabs.SearchSourcesTab;
import com.jacob.newsapp.viewmodels.SearchPageViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class SearchPage extends Fragment {

	private void setUpSearchBar() {
		SearchView searchView = binding.searchView;

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				int          currentPage = binding.viewPager.getCurrentItem();
				SEARCH_PAGES search_page = SEARCH_PAGES.valueOf(currentPage);
				viewModel.setQuery(new Pair<>(search_page, query));
				Toast.makeText(getContext(), "Submitted, " + currentPage, Toast.LENGTH_SHORT)
				     .show();
				viewModel.setSubmitted(true);
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newQuery) {
				viewModel.setSubmitted(false);
				return false;
			}
		});
	}

	private SearchPageFragmentBinding binding;
	private SearchPageViewModel       viewModel;

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

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		viewModel = new ViewModelProvider(requireActivity()).get(SearchPageViewModel.class);
		setUpSearchBar();
	}

	public enum SEARCH_PAGES {
		KEYWORDS(0), SOURCES(1), CATEGORIES(2);
		private final int value;

		SEARCH_PAGES(int value) {
			this.value = value;
		}

		public static SEARCH_PAGES valueOf(int value) {
			return Arrays.stream(values())
			             .filter(page -> page.value == value)
			             .findFirst()
			             .get();
		}
	}

	private void setUpTabBar() {
		ViewPager viewPager = binding.viewPager;
		TabLayout tabLayout = binding.tabLayout;

		TabBarAdapter adapter = new TabBarAdapter(getParentFragmentManager(),
				BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

		adapter.addFragment(new SearchArticlesTab(), "Articles");
		adapter.addFragment(new SearchSourcesTab(), "Sources");
		adapter.addFragment(new SearchCategoriesTab(), "Categories");

		viewPager.setAdapter(adapter);
		tabLayout.setupWithViewPager(viewPager);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}