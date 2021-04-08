package com.jacob.newsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.jacob.newsapp.repositories.FirebaseAuthRepository;

public class SignUpScreenViewModel extends ViewModel {
    private final FirebaseAuthRepository firebaseAuthRepository =
            FirebaseAuthRepository.getInstance();

    public void register(String userName, String email, String password) {
        firebaseAuthRepository.register(userName, email, password);
    }

    public LiveData<Boolean> getAuthenticationStatus() {
        return firebaseAuthRepository.getAuthenticationResultLiveData();
    }
}
