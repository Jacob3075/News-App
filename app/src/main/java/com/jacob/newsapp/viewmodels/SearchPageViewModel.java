package com.jacob.newsapp.viewmodels;

import android.util.Pair;
import androidx.lifecycle.*;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.jacob.newsapp.adapters.PagedNewsListAdapter;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.repositories.FireBaseUserDataRepository;
import com.jacob.newsapp.services.NewsDataFactory;
import com.jacob.newsapp.ui.fragments.SearchPage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.jacob.newsapp.adapters.PagedNewsListAdapter.CardViewModelFunctions;
import static com.jacob.newsapp.adapters.PagedNewsListAdapter.Page;

public class SearchPageViewModel extends ViewModel {

    private final FireBaseUserDataRepository userDataRepository =
            FireBaseUserDataRepository.getInstance();
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

    public PagedNewsListAdapter setUpRecyclerView() {
        CardViewModelFunctions listener =
                new CardViewModelFunctions() {
                    @Override
                    public boolean onArticleClicked(Article article) {
                        return saveArticleButtonPressed(article);
                    }

                    @Override
                    public boolean isArticleSaved(Article article) {
                        return isArticleAlreadySaved(article);
                    }
                };

        PagedNewsListAdapter adapter = new PagedNewsListAdapter(listener, Page.SEARCH);
        adapter.submitList(pagedListLiveData.getValue());
        return adapter;
    }

    /**
     * @param article to save or remove from the database
     * @return true if the article was saved and false if the article was removed.
     */
    public boolean saveArticleButtonPressed(Article article) {
        if (isArticleAlreadySaved(article)) {
            userDataRepository.unSaveArticle(article);
            return false;
        } else {
            userDataRepository.saveNewArticle(article);
            return true;
        }
    }

    public boolean isArticleAlreadySaved(Article article) {
        List<Article> articles = userDataRepository.getArticlesLiveData().getValue();
        if (articles == null) {
            articles = new ArrayList<>();
        }

        return articles.contains(article);
    }
}
