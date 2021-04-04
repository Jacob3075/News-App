package com.jacob.newsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jacob.newsapp.models.MediaStackResponse;
import com.jacob.newsapp.repositories.ArticleRepository;

public class SearchPageViewModel extends ViewModel {
    private final ArticleRepository articleRepository = new ArticleRepository();
    private final MutableLiveData<String> query = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> submitted = new MutableLiveData<>();

    public LiveData<MediaStackResponse> getTrendingNews() {
        return articleRepository.getLatestNews();
    }

    public LiveData<MediaStackResponse> getNewsBySource() {
        return articleRepository.getNewsBySource();
    }

    public LiveData<MediaStackResponse> getNewsByCategory() {
        return articleRepository.getNewsByCategory();
    }

    public LiveData<MediaStackResponse> getNewsByKeyWord() {
        return articleRepository.getNewsByKeyWord();
    }


    public LiveData<Boolean> getSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean newSubmittedValue) {
        submitted.setValue(newSubmittedValue);
    }

    public LiveData<String> getQuery() {
        return query;
    }

    public void setQuery(String newQuery) {
        query.setValue(newQuery);
    }

    public void searchNewsBySource(String source) {
        articleRepository.getNewsBySource(source);
    }

    public void searchNewsByKeyWord(String searchQuery) {
        articleRepository.getNewsByKeyWord(searchQuery);
    }

    public void searchNewsByCategory(String category) {
        articleRepository.getNewsByCategory(category);
    }
}
