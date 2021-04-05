package com.jacob.newsapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.jacob.newsapp.MainActivity;
import com.jacob.newsapp.databinding.ActivityLoginScreenBinding;
import com.jacob.newsapp.viewmodels.LoginScreenViewModel;

public class LoginScreen extends AppCompatActivity {

	private ActivityLoginScreenBinding binding;
	private LoginScreenViewModel       viewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
		ConstraintLayout root = binding.getRoot();
		viewModel = new ViewModelProvider(this).get(LoginScreenViewModel.class);

		setContentView(root);
		setUpButtons();
	}

	private void setUpButtons() {
		binding.homePage.setOnClickListener(this::openHomePage);
	}

	private void openHomePage(View view) {
		Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
//        this.finish();
	}

	private void openSignUpPagek(View view) {
		Intent intent = new Intent(this, SignUpScreen.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
//        this.finish();
	}
}