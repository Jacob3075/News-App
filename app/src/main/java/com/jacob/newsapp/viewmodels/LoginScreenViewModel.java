package com.jacob.newsapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.AuthCredential;
import com.jacob.newsapp.models.User;
import com.jacob.newsapp.repositories.FirebaseAuthRepository;

public class LoginScreenViewModel extends AndroidViewModel {
	private final FirebaseAuthRepository authRepository;
	private       LiveData<User>         authenticatedUserLiveData;
	private       LiveData<User>         createdUserLiveData;

	public LoginScreenViewModel(Application application) {
		super(application);
		authRepository = new FirebaseAuthRepository();
	}

	public void signInWithGoogle(AuthCredential googleAuthCredential) {
		authenticatedUserLiveData = authRepository.firebaseSignInWithGoogle(googleAuthCredential);
	}

	public void createUser(User authenticatedUser) {
		createdUserLiveData = authRepository.createUserInFireStoreIfNotExists(authenticatedUser);
	}

	public LiveData<User> getAuthenticatedUserLiveData() {
		return authenticatedUserLiveData;
	}

	public LiveData<User> getCreatedUserLiveData() {
		return createdUserLiveData;
	}
}