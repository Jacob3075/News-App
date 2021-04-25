package com.jacob.newsapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

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
        setUpUI();
    }

    private void setUpUI() {
        binding.loginButton.setOnClickListener(this::loginUser);
        binding.signUpButton.setOnClickListener(this::openSignUpPage);
        viewModel.getAuthenticationStatus().observe(this, this::openHomePage);
    }

    private void loginUser(View view) {
        String email = binding.emailInput.getText().toString();
        String password = binding.passwordInput.getText().toString();
        viewModel.loginInUser(email, password);
    }

    private void openSignUpPage(View view) {
        Intent intent = new Intent(this, SignUpScreen.class);
        startActivity(intent);
    }

    private void openHomePage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }

    private void openHomePage(@Nullable Boolean authenticationStatus) {
        if (authenticationStatus == null) return;

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
