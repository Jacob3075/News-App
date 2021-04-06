package com.jacob.newsapp.viewmodels;

import androidx.lifecycle.ViewModel;

import com.jacob.newsapp.repositories.FirebaseAuthRepository;

public class SplashScreenViewModel extends ViewModel {
	private final FirebaseAuthRepository firebaseAuthRepository = new FirebaseAuthRepository();

	public boolean isUserLoggedIn() {
		return firebaseAuthRepository.isUserLoggedIn();
	}

}
