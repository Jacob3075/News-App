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

import com.jacob.newsapp.databinding.SearchSourcesTabFragmentBinding;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.models.MediaStackResponse;
import com.jacob.newsapp.viewmodels.SearchPageViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class SearchSourcesTab extends Fragment {

	private SearchPageViewModel             viewModel;
	private SearchSourcesTabFragmentBinding binding;

	public static SearchSourcesTab newInstance() {
		return new SearchSourcesTab();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		binding = SearchSourcesTabFragmentBinding.inflate(inflater, container, false);
		ConstraintLayout root = binding.getRoot();

		return root;

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		viewModel = new ViewModelProvider(requireActivity()).get(SearchPageViewModel.class);

		setUpTextView();
		setUpRecyclerView();
	}

	private void setUpRecyclerView() {
		viewModel.getNewsBySource()
		         .observe(getViewLifecycleOwner(), this::updateRecyclerViewWithResults);
	}

	private void setUpTextView() {
		viewModel.getSubmitted().observe(getViewLifecycleOwner(), this::submitQueryIfNotEmpty);
	}

	private void submitQueryIfNotEmpty(Boolean submitted) {
		String sourceQuery = viewModel.getQuery().getValue();
		if (submitted && !sourceQuery.isEmpty()) {
			viewModel.searchNewsBySource(sourceQuery);
		} else if (sourceQuery.isEmpty()) {
//            Toast.makeText(getContext(), "Enter a search query", Toast.LENGTH_SHORT).show();
		}
	}

	private void updateRecyclerViewWithResults(MediaStackResponse mediaStackResponse) {
		List<Article> articles = mediaStackResponse.getArticles();
		List<String> collect = articles.stream()
		                               .filter(Article::notContainsNull)
		                               .limit(15)
		                               .map(Article::getSource)
		                               .collect(Collectors.toList());
		binding.textView.setText(collect.toString());
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}
