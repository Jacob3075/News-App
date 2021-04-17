package com.jacob.newsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.repositories.FireBaseUserDataRepository;
import com.jacob.newsapp.services.NewsDataFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.jacob.newsapp.utilities.Constants.PAGE_SIZE;

public class HomePageViewModal extends ViewModel {
    private LiveData<PagedList<Article>> pagedListLiveData;
    private final FireBaseUserDataRepository userDataRepository =
            FireBaseUserDataRepository.getInstance();

    public HomePageViewModal() {
        init();
    }

    private void init() {
        Executor executor = Executors.newFixedThreadPool(5);
        NewsDataFactory newsDataFactory = new NewsDataFactory("", "", "");
        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(PAGE_SIZE + 10)
                        .setPageSize(PAGE_SIZE)
                        .build();

        pagedListLiveData =
                new LivePagedListBuilder<>(newsDataFactory, pagedListConfig)
                        .setFetchExecutor(executor)
                        .build();
    }

    public LiveData<PagedList<Article>> getLatestNews() {
        return pagedListLiveData;
    }

    /**
     * @param article to save or remove from the database
     * @return true if the article was saved and false if the article was removed.
     */
    public boolean saveArticleButtonPressed(Article article) {
        if (isArticleSaved(article)) {
            userDataRepository.unSaveArticle(article);
            return false;
        } else {
            userDataRepository.saveNewArticle(article);
            return true;
        }
    }

    /**
     * @param article to check if is already present in the database.
     * @return True if the article is present and false if the article is not present.
     */
    public boolean isArticleSaved(Article article) {
        List<Article> articles = userDataRepository.getArticlesLiveData().getValue();
        if (articles == null) {
            articles = new ArrayList<>();
        }

        return articles.contains(article);
    }
}
