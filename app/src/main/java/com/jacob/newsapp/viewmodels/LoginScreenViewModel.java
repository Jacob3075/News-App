package com.jacob.newsapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.AuthCredential;
import com.jacob.newsapp.models.User;
import com.jacob.newsapp.repositories.FirebaseAuthRepository;

public class LoginScreenViewModel extends AndroidViewModel {
	private final FirebaseAuthRepository firebaseAuthRepository;
	private       LiveData<User>         authenticatedUserLiveData;
	private       LiveData<User>         createdUserLiveData;

	public LoginScreenViewModel(Application application) {
		super(application);
		firebaseAuthRepository = FirebaseAuthRepository.getInstance();
	}

	public void signInWithGoogle(AuthCredential googleAuthCredential) {
		authenticatedUserLiveData = firebaseAuthRepository.firebaseSignInWithGoogle(googleAuthCredential);
	}

	public void createUser(User authenticatedUser) {
		createdUserLiveData = firebaseAuthRepository.createUserInFireStoreIfNotExists(authenticatedUser);
	}

	public LiveData<User> getAuthenticatedUserLiveData() {
		return authenticatedUserLiveData;
	}

	public LiveData<User> getCreatedUserLiveData() {
		return createdUserLiveData;
	}
}