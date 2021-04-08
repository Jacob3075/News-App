package com.jacob.newsapp.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.jacob.newsapp.repositories.FirebaseAuthRepository;

public class LoginScreenViewModel extends AndroidViewModel {
    private final FirebaseAuthRepository firebaseAuthRepository;

    public LoginScreenViewModel(Application application) {
        super(application);
        firebaseAuthRepository = FirebaseAuthRepository.getInstance();
    }

    public LiveData<Boolean> getAuthenticationStatus() {
        return firebaseAuthRepository.getAuthenticationResultLiveData();
    }

    public void loginInUser(String email, String password) {
        firebaseAuthRepository.login(email, password);
    }
}
