package com.jacob.newsapp.ui.fragments;

import android.content.Intent;
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
import com.jacob.newsapp.databinding.ProfilePageFragmentBinding;
import com.jacob.newsapp.ui.activities.LoginScreen;
import com.jacob.newsapp.viewmodels.ProfilePageViewModel;

import org.jetbrains.annotations.NotNull;

public class ProfilePage extends Fragment {

	private ProfilePageFragmentBinding binding;
	private ProfilePageViewModel       viewModel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(@NotNull LayoutInflater inflater,
	                         ViewGroup container,
	                         Bundle savedInstanceState) {
		binding = ProfilePageFragmentBinding.inflate(inflater, container, false);

		ConstraintLayout root = binding.getRoot();

		setUpButtons();

		return root;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		viewModel = new ViewModelProvider(this).get(ProfilePageViewModel.class);
	}

	private void setUpButtons() {
		binding.savedCategoriesButton.setOnClickListener(this::openSavedCategoriesPage);
		binding.savedSourcesButton.setOnClickListener(this::openSavedSourcesPage);
		binding.logoutButton.setOnClickListener(this::logout);
	}

	private void openSavedCategoriesPage(View view) {
		Navigation.findNavController(view).navigate(R.id.profilePage_to_savedCategoriesPage);
	}

	private void openSavedSourcesPage(View view) {
		Navigation.findNavController(view).navigate(R.id.profilePage_to_savedCategoriesPage);
	}

	private void logout(View view) {
		viewModel.logout();
		Intent intent = new Intent(getContext(), LoginScreen.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		getActivity().finish();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}