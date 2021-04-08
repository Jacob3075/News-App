package com.jacob.newsapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.jacob.newsapp.MainActivity;
import com.jacob.newsapp.R;
import com.jacob.newsapp.viewmodels.SplashScreenViewModel;

public class SplashScreen extends AppCompatActivity {

    private SplashScreenViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        viewModel = new ViewModelProvider(this).get(SplashScreenViewModel.class);

        new Handler(Looper.getMainLooper())
                .postDelayed(
                        () -> {
                            if (viewModel.isUserLoggedIn()) {
                                Intent intent = new Intent(this, MainActivity.class);
                                intent.addFlags(
                                        Intent.FLAG_ACTIVITY_CLEAR_TOP
                                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                this.finish();
                            } else {
                                Intent intent = new Intent(this, LoginScreen.class);
                                intent.addFlags(
                                        Intent.FLAG_ACTIVITY_CLEAR_TOP
                                                | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                this.finish();
                            }
                        },
                        2000);
    }
}
