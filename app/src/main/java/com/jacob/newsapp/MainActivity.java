package com.jacob.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.jacob.newsapp.databinding.ActivityMainBinding;
import com.jacob.newsapp.ui.activities.HomePage;
import com.jacob.newsapp.ui.activities.SavedArticlesPage;
import com.jacob.newsapp.ui.activities.SearchPage;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.btnHomePage.setOnClickListener(button -> {
            Intent intent = new Intent(MainActivity.this, HomePage.class);
            startActivity(intent);
        });
        binding.btnSearchPage.setOnClickListener(button -> {
            Intent intent = new Intent(MainActivity.this, SearchPage.class);
            startActivity(intent);
        });
        binding.btnSavedArticles.setOnClickListener(button -> {
            Intent intent = new Intent(MainActivity.this, SavedArticlesPage.class);
            startActivity(intent);
        });
    }
}