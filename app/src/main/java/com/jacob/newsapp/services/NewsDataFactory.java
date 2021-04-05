package com.jacob.newsapp.services;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.jacob.newsapp.models.Article;

public class NewsDataFactory extends DataSource.Factory<Integer, Article> {

    private final MutableLiveData<NewsDataSource> dataSourceMutableLiveData = new MutableLiveData<>();
    private final String keywordsQuery;
    private final String sourceQuery;
    private final String categoryQuery;

    public NewsDataFactory(String keywordsQuery, String sourceQuery, String categoryQuery) {
        this.keywordsQuery = keywordsQuery;
        this.sourceQuery = sourceQuery;
        this.categoryQuery = categoryQuery;
    }

    @Override
    public DataSource<Integer, Article> create() {
        NewsDataSource newsDataSource = new NewsDataSource(keywordsQuery, sourceQuery, categoryQuery);
        dataSourceMutableLiveData.postValue(newsDataSource);
        return newsDataSource;
    }

    public LiveData<NewsDataSource> getDataSource() {
        return dataSourceMutableLiveData;
    }
}
