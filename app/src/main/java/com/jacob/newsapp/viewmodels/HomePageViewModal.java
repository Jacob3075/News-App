package com.jacob.newsapp.viewmodels;

import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.jacob.newsapp.R;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.services.NewsDataFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomePageViewModal extends ViewModel {
    private LiveData<PagedList<Article>> pagedListLiveData;

    public HomePageViewModal() {
        init();
    }

    private void init() {
        Executor executor = Executors.newFixedThreadPool(5);
        NewsDataFactory newsDataFactory = new NewsDataFactory("", "", "");
        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(35)
                        .setPageSize(25)
                        .build();

        pagedListLiveData =
                new LivePagedListBuilder<>(newsDataFactory, pagedListConfig)
                        .setFetchExecutor(executor)
                        .build();
    }

    public LiveData<PagedList<Article>> getLatestNews() {
        return pagedListLiveData;
    }

    public void searchButtonClicked(View view) {
        Navigation.findNavController(view).navigate(R.id.homePage_to_searchPage);
    }
}
