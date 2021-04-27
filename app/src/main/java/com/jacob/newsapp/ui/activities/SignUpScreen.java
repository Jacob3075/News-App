package com.jacob.newsapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.jacob.newsapp.MainActivity;
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
        viewModel.getAuthenticationStatus().observe(this, this::openHomePage);
    }

    private void openLoginPage(View view) {
        onBackPressed();
    }

    private void signUp(View view) {
        String userName = binding.userNameInput.getText().toString();
        String email = binding.emailInput.getText().toString();
        String password = binding.passwordInput.getText().toString();
        if (!viewModel.verifyInputs(userName, email, password)) return;

        viewModel.register(userName, email, password);
    }

    private void openHomePage(Boolean authenticationStatus) {
        if (authenticationStatus == null) return;

        if (authenticationStatus) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        } else {
            Toast.makeText(this, "Sign Up Failed, try again later", Toast.LENGTH_SHORT).show();
        }
    }
}
