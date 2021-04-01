package com.jacob.newsapp.viewmodels;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import com.jacob.newsapp.R;
import com.jacob.newsapp.models.MediaStackResponse;
import com.jacob.newsapp.repositories.ArticleRepository;

public class HomePageViewModal extends ViewModel {

    private final ArticleRepository articleRepository = new ArticleRepository();

    public void searchButtonClicked(View view) {
        Navigation.findNavController(view).navigate(R.id.homePage_to_searchPage);
    }

    public LiveData<MediaStackResponse> getTrendingNews() {
        return articleRepository.getTrendingNews();
    }
}
