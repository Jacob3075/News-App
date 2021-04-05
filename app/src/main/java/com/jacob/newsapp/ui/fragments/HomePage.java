package com.jacob.newsapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jacob.newsapp.adapters.PagedNewsListAdapter;
import com.jacob.newsapp.databinding.HomePageFragmentBinding;
import com.jacob.newsapp.viewmodels.HomePageViewModal;

import org.jetbrains.annotations.NotNull;

public class HomePage extends Fragment {

	private HomePageFragmentBinding binding;
	private HomePageViewModal       viewModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(@NotNull LayoutInflater inflater,
	                         ViewGroup container,
	                         Bundle savedInstanceState) {

		binding = HomePageFragmentBinding.inflate(inflater, container, false);

		return binding.getRoot();
	}

	private void setUpButtons() {
		binding.searchButton.setOnClickListener(viewModel::searchButtonClicked);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		viewModel = new ViewModelProvider(requireActivity()).get(HomePageViewModal.class);

		setUpButtons();
		setUpRecyclerView();
	}

	private void setUpRecyclerView() {

		RecyclerView        recyclerView  = binding.recyclerview;
		LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
		layoutManager.setOrientation(RecyclerView.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);

		PagedNewsListAdapter adapter = new PagedNewsListAdapter();

		viewModel.getLatestNews().observe(getViewLifecycleOwner(), adapter::submitList);
		recyclerView.setAdapter(adapter);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}