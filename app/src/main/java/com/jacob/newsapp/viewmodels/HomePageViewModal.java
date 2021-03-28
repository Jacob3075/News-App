package com.jacob.newsapp.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jacob.newsapp.models.MediaStackResponse;
import com.jacob.newsapp.repositories.ArticleRepository;

public class HomePageViewModal extends ViewModel {

    private final ArticleRepository articleRepository = new ArticleRepository();
    private final MutableLiveData<MediaStackResponse> liveData;

    public HomePageViewModal() {
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<MediaStackResponse> getTrendingNews() {
        return articleRepository.getTrendingNews();
    }

    public MutableLiveData<MediaStackResponse> getNewsBySource(String source) {
        return articleRepository.getNewsBySource(source);
    }

    public MutableLiveData<MediaStackResponse> searchForNews(String searchQuery) {
        return articleRepository.getNewsBySource(searchQuery);
    }

    public MutableLiveData<MediaStackResponse> searchForNewsFromSource(String searchQuery, String source) {
        return articleRepository.getNewsBySource(source);
    }
}
