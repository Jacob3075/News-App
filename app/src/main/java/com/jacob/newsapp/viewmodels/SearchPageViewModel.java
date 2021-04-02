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

    public LiveData<Boolean> getSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean newSubmittedValue) {
        submitted.setValue(newSubmittedValue);
    }

    public void setQuery(String newQuery) {
        query.setValue(newQuery);
    }

    public LiveData<String> getQuery() {
        return query;
    }

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
