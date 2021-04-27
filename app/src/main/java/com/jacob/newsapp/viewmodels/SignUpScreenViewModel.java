package com.jacob.newsapp.viewmodels;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.jacob.newsapp.repositories.FirebaseAuthRepository;
import org.jetbrains.annotations.NotNull;

public class SignUpScreenViewModel extends AndroidViewModel {
    private final FirebaseAuthRepository firebaseAuthRepository =
            FirebaseAuthRepository.getInstance();

    public SignUpScreenViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public boolean verifyInputs(@NotNull String userName, String email, String password) {
        Context context = getApplication().getApplicationContext();

        if (userName.isEmpty()) {
            Toast.makeText(context, "User Name can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.isEmpty()) {
            Toast.makeText(context, "Email can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(context, "Password can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 8) {
            Toast.makeText(
                            context,
                            "Password length must be greater than 8 character",
                            Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

    public void register(String userName, String email, String password) {
        firebaseAuthRepository.register(userName, email, password);
    }

    public LiveData<Boolean> getAuthenticationStatus() {
        return firebaseAuthRepository.getAuthenticationResultLiveData();
    }
}
