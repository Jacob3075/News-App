package com.jacob.newsapp.viewmodels;

import androidx.lifecycle.*;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.jacob.newsapp.adapters.PagedNewsListAdapter;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.repositories.FireBaseUserDataRepository;
import com.jacob.newsapp.services.NewsDataFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.jacob.newsapp.adapters.PagedNewsListAdapter.CardViewModelFunctions;
import static com.jacob.newsapp.adapters.PagedNewsListAdapter.Page;

public class SearchPageViewModel extends ViewModel {

    private final FireBaseUserDataRepository userDataRepository =
            FireBaseUserDataRepository.getInstance();
    private final MutableLiveData<String> query = new MediatorLiveData<>();
    private LiveData<PagedList<Article>> pagedArticlesByNameLiveData;
    private LiveData<PagedList<Article>> pagedArticlesBySourceLiveData;
    private LiveData<PagedList<Article>> pagedArticlesByCategoryLiveData;

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

        pagedArticlesByNameLiveData =
                Transformations.switchMap(
                        query,
                        newQuery -> {
                            if (newQuery.isEmpty()) return pagedArticlesByNameLiveData;

                            NewsDataFactory newsDataFactoryWithQuery =
                                    new NewsDataFactory(newQuery, "", "");

                            return new LivePagedListBuilder<>(
                                            newsDataFactoryWithQuery, pagedListConfig)
                                    .setFetchExecutor(executor)
                                    .build();
                        });

        pagedArticlesBySourceLiveData =
                Transformations.switchMap(
                        query,
                        newQuery -> {
                            if (newQuery.isEmpty()) return pagedArticlesBySourceLiveData;

                            NewsDataFactory newsDataFactoryWithQuery =
                                    new NewsDataFactory("", newQuery, "");

                            return new LivePagedListBuilder<>(
                                            newsDataFactoryWithQuery, pagedListConfig)
                                    .setFetchExecutor(executor)
                                    .build();
                        });

        pagedArticlesByCategoryLiveData =
                Transformations.switchMap(
                        query,
                        newQuery -> {
                            if (newQuery.isEmpty()) return pagedArticlesByCategoryLiveData;

                            NewsDataFactory newsDataFactoryWithQuery =
                                    new NewsDataFactory("", "", newQuery);

                            return new LivePagedListBuilder<>(
                                            newsDataFactoryWithQuery, pagedListConfig)
                                    .setFetchExecutor(executor)
                                    .build();
                        });
    }

    public LiveData<PagedList<Article>> getPagedArticlesByNameLiveData() {
        return pagedArticlesByNameLiveData;
    }

    public LiveData<PagedList<Article>> getPagedArticlesBySourceLiveData() {
        return pagedArticlesBySourceLiveData;
    }

    public LiveData<PagedList<Article>> getPagedArticlesByCategoryLiveData() {
        return pagedArticlesByCategoryLiveData;
    }

    public void setQuery(String newQuery) {
        query.setValue(newQuery);
    }

    public PagedNewsListAdapter setUpRecyclerView(
            @NotNull LiveData<PagedList<Article>> pagedListLiveData) {
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
