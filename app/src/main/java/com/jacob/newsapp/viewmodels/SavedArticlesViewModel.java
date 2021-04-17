package com.jacob.newsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.repositories.FireBaseUserDataRepository;

import java.util.ArrayList;
import java.util.List;

public class SavedArticlesViewModel extends ViewModel {
    private final FireBaseUserDataRepository userDataRepository =
            FireBaseUserDataRepository.getInstance();

    public LiveData<List<Article>> getSavedArticles() {
        return userDataRepository.getArticlesLiveData();
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
