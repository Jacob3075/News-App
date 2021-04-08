package com.jacob.newsapp.viewmodels;

import android.util.Pair;
import androidx.lifecycle.*;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.services.NewsDataFactory;
import com.jacob.newsapp.ui.fragments.SearchPage;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SearchPageViewModel extends ViewModel {

    private final MutableLiveData<Pair<SearchPage.SEARCH_PAGES, String>> query =
            new MediatorLiveData<>();
    private LiveData<PagedList<Article>> pagedListLiveData;

    public SearchPageViewModel() {
        init();
    }

    private void init() {
        Executor executor = Executors.newFixedThreadPool(5);
        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(35)
                        .setPageSize(25)
                        .build();

        pagedListLiveData =
                Transformations.switchMap(
                        query,
                        newQuery -> {
                            if (newQuery.second.isEmpty()) return pagedListLiveData;

                            String keywordsQuery = "";
                            String sourceQuery = "";
                            String categoryQuery = "";

                            switch (newQuery.first) {
                                case SOURCES:
                                    sourceQuery = newQuery.second;
                                    break;
                                case KEYWORDS:
                                    keywordsQuery = newQuery.second;
                                    break;
                                case CATEGORIES:
                                    categoryQuery = newQuery.second;
                            }

                            NewsDataFactory newsDataFactoryWithQuery =
                                    new NewsDataFactory(keywordsQuery, sourceQuery, categoryQuery);

                            return new LivePagedListBuilder<>(
                                            newsDataFactoryWithQuery, pagedListConfig)
                                    .setFetchExecutor(executor)
                                    .build();
                        });
    }

    public LiveData<PagedList<Article>> getPagedListLiveData() {
        return pagedListLiveData;
    }

    public void setQuery(Pair<SearchPage.SEARCH_PAGES, String> newQuery) {
        query.setValue(newQuery);
    }
}
