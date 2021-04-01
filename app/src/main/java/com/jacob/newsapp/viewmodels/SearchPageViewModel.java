package com.jacob.newsapp.viewmodels;

import androidx.lifecycle.LiveData;

import com.jacob.newsapp.models.MediaStackResponse;
import com.jacob.newsapp.repositories.ArticleRepository;

public class SearchPageViewModel {
    private final ArticleRepository articleRepository = new ArticleRepository();

    public LiveData<MediaStackResponse> getNewsBySource(String source) {
        return articleRepository.getNewsBySource(source);
    }

    public LiveData<MediaStackResponse> searchForNews(String searchQuery) {
        return articleRepository.getNewsByKeyWord(searchQuery);
    }

    public LiveData<MediaStackResponse> getNewsByCategory(String category) {
        return articleRepository.getNewsByCategory(category);
    }
}
