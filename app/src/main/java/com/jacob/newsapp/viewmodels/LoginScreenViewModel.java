package com.jacob.newsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.jacob.newsapp.repositories.FirebaseAuthRepository;

public class LoginScreenViewModel extends ViewModel {
    private final FirebaseAuthRepository firebaseAuthRepository =
            FirebaseAuthRepository.getInstance();

    public LiveData<Boolean> getAuthenticationStatus() {
        return firebaseAuthRepository.getAuthenticationResultLiveData();
    }

    public void loginInUser(String email, String password) {
        firebaseAuthRepository.login(email, password);
    }
}
