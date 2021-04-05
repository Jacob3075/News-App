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
import com.jacob.newsapp.viewmodels.SavedSourcesPageViewModel;

public class SavedSourcesPage extends Fragment {

	private SavedSourcesPageViewModel mViewModel;

	public static SavedSourcesPage newInstance() {
		return new SavedSourcesPage();
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.saved_sources_page_fragment, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mViewModel = new ViewModelProvider(this).get(SavedSourcesPageViewModel.class);
		// TODO: Use the ViewModel
	}

}