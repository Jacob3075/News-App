package com.jacob.newsapp.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.jacob.newsapp.databinding.SignUpScreenBinding;
import com.jacob.newsapp.viewmodels.SignUpScreenViewModel;

public class SignUpScreen extends AppCompatActivity {

    private SignUpScreenBinding binding;
    private SignUpScreenViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SignUpScreenBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(SignUpScreenViewModel.class);
        setContentView(binding.getRoot());
        setUpUI();
    }

    private void setUpUI() {
        binding.loginButton.setOnClickListener(this::openLoginPage);
        binding.signUpButton.setOnClickListener(this::signUp);
    }

    private void openLoginPage(View view) {
        onBackPressed();
    }

    private void signUp(View view) {
        String userName = binding.userNameInput.getText().toString();
        String email = binding.emailInput.getText().toString();
        String password = binding.passwordInput.getText().toString();
        viewModel.register(userName, email, password);
        viewModel.getAuthenticationStatus().observe(this, this::openHomePage);
    }

    private void openHomePage(boolean authenticationStatus) {
        if (authenticationStatus) {
            onBackPressed();
        } else {
            Toast.makeText(this, "Sign Up Failed, try again later", Toast.LENGTH_SHORT).show();
        }
    }
}
