package com.jacob.newsapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.annotations.NotNull;
import com.jacob.newsapp.MainActivity;
import com.jacob.newsapp.databinding.LoginScreenBinding;
import com.jacob.newsapp.viewmodels.LoginScreenViewModel;

public class LoginScreen extends AppCompatActivity {

    private LoginScreenBinding binding;
    private LoginScreenViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginScreenBinding.inflate(getLayoutInflater());
        ConstraintLayout root = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(LoginScreenViewModel.class);

        setContentView(root);
        initGoogleSignInClient();
        setUpButtons();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInAccount googleSignInAccount = GoogleSignIn.getSignedInAccountFromIntent(data)
                    .getResult();
            if (googleSignInAccount != null) {
                getGoogleAuthCredential(googleSignInAccount);
            }
        }
    }

    private void getGoogleAuthCredential(@NotNull GoogleSignInAccount googleSignInAccount) {
        String googleTokenId = googleSignInAccount.getIdToken();
        AuthCredential googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null);
        signInWithGoogleAuthCredential(googleAuthCredential);
    }

    private void setUpUI() {
        binding.loginButton.setOnClickListener(this::loginUser);
        binding.signUpButton.setOnClickListener(this::openSignUpPage);
        binding.homePage.setOnClickListener(this::openHomePage);
    }

    private void loginUser(View view) {
        String email = binding.emailInput.getText().toString();
        String password = binding.passwordInput.getText().toString();
        viewModel.loginInUser(email, password);
        viewModel.getAuthenticationStatus().observe(this, this::openHomePage);
    }

    private void openSignUpPage(Object o) {
        Intent intent = new Intent(this, SignUpScreen.class);
        startActivity(intent);
    }

    private void openHomePage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }

    private void openHomePage(boolean authenticationStatus) {
        if (authenticationStatus) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        } else {
            Toast.makeText(this, "Authentication Failed, try again later", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
