package com.jacob.newsapp.viewmodels;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.services.NewsDataFactory;
import com.jacob.newsapp.ui.fragments.SearchPage;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SearchPageViewModel extends ViewModel {

	private final MutableLiveData<Pair<SearchPage.SEARCH_PAGES, String>> query     = new MediatorLiveData<>();
	private final MutableLiveData<Boolean>                               submitted = new MutableLiveData<>();
	private       LiveData<PagedList<Article>>                           pagedListLiveData;

	public SearchPageViewModel() {
		init();
	}

	public LiveData<PagedList<Article>> getPagedListLiveData() {
		return pagedListLiveData;
	}

	private void init() {
		Executor executor = Executors.newFixedThreadPool(5);
		PagedList.Config pagedListConfig = new PagedList.Config
				                                       .Builder()
				                                   .setEnablePlaceholders(false)
				                                   .setInitialLoadSizeHint(35)
				                                   .setPageSize(25)
				                                   .build();

		pagedListLiveData = Transformations.switchMap(query, newQuery -> {
			if (newQuery.second.isEmpty()) return pagedListLiveData;

			String keywordsQuery = "";
			String sourceQuery   = "";
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

			NewsDataFactory newsDataFactoryWithQuery = new NewsDataFactory(keywordsQuery,
					sourceQuery,
					categoryQuery);

			return new LivePagedListBuilder<>(newsDataFactoryWithQuery, pagedListConfig)
					       .setFetchExecutor(executor)
					       .build();

		});
	}

	public LiveData<Boolean> getSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean newSubmittedValue) {
		submitted.setValue(newSubmittedValue);
	}

	public LiveData<Pair<SearchPage.SEARCH_PAGES, String>> getQuery() {
		return query;
	}

	public void setQuery(Pair<SearchPage.SEARCH_PAGES, String> newQuery) {
		query.setValue(newQuery);
	}
}
