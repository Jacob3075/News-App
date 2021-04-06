package com.jacob.newsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.jacob.newsapp.models.User;
import com.jacob.newsapp.repositories.FirebaseAuthRepository;

public class ProfilePageViewModel extends ViewModel {
	private final FirebaseAuthRepository firebaseAuthRepository = FirebaseAuthRepository.getInstance();

	public void logout() {
		firebaseAuthRepository.logout();
	}

	public LiveData<User> getLoggedInUser() {
		return firebaseAuthRepository.getUserLiveData();
	}
}
