package com.jacob.newsapp.viewmodels;

import androidx.lifecycle.ViewModel;

import com.jacob.newsapp.repositories.FirebaseAuthRepository;

public class ProfilePageViewModel extends ViewModel {
	private final FirebaseAuthRepository firebaseAuthRepository = new FirebaseAuthRepository();

	public void logout() {
		firebaseAuthRepository.logout();
	}
}
