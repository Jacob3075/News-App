package com.jacob.newsapp.ui.fragments.tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jacob.newsapp.databinding.SearchArticlesTabFragmentBinding;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.models.MediaStackResponse;
import com.jacob.newsapp.viewmodels.SearchPageViewModel;

import java.util.List;
import java.util.stream.Collectors;

public class SearchArticlesTab extends Fragment {

	private SearchPageViewModel              viewModel;
	private SearchArticlesTabFragmentBinding binding;

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater,
	                         @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		binding = SearchArticlesTabFragmentBinding.inflate(inflater, container, false);

		return binding.getRoot();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		viewModel = new ViewModelProvider(requireActivity()).get(SearchPageViewModel.class);

		setUpTextView();
		setUpRecyclerView();
	}

	private void setUpRecyclerView() {
		viewModel.getNewsByKeyWord()
		         .observe(getViewLifecycleOwner(), this::updateRecyclerViewWithResults);
	}

	private void setUpTextView() {
		viewModel.getSubmitted().observe(getViewLifecycleOwner(), this::submitQueryIfNotEmpty);
	}

	private void submitQueryIfNotEmpty(Boolean submitted) {
		String keywordQuery = viewModel.getQuery().getValue();
		if (submitted && !keywordQuery.isEmpty()) {
			viewModel.searchNewsByKeyWord(keywordQuery);
		} else if (keywordQuery.isEmpty()) {
//            Toast.makeText(getContext(), "Enter a search query", Toast.LENGTH_SHORT).show();
		}
	}

	private void updateRecyclerViewWithResults(MediaStackResponse mediaStackResponse) {
		List<Article> articles = mediaStackResponse.getArticles();
		List<String> collect = articles.stream()
		                               .filter(Article::notContainsNull)
		                               .limit(15)
		                               .map(Article::getTitle)
		                               .collect(Collectors.toList());
//                .forEach(x -> Log.d("ARTICLE", x.getTitle() + ", " + x.getSource() + ", " + x.getCategory()));
		binding.textView.setText(collect.toString());
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}