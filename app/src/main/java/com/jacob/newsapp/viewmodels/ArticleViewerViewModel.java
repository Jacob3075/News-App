package com.jacob.newsapp.viewmodels;

import androidx.lifecycle.ViewModel;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.repositories.FireBaseUserDataRepository;

import java.util.ArrayList;
import java.util.List;

public class ArticleViewerViewModel extends ViewModel {

    private final FireBaseUserDataRepository userDataRepository =
            FireBaseUserDataRepository.getInstance();

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
    /**
     * @param source save or remove from the database
     * @return true if the source was saved and false if the source was removed.
     */
    public boolean saveSourceMenuItemPressed(String source) {
        if (isSourceSaved(source)) {
            userDataRepository.unSaveSource(source);
            return false;
        } else {
            userDataRepository.saveNewSource(source);
            return true;
        }
    }

    /**
     * @param source to check if is already present in the database.
     * @return True if the source is present and false if the source is not present.
     */
    public boolean isSourceSaved(String source) {
        List<String> sources = userDataRepository.getSourcesLiveData().getValue();
        if (sources == null) {
            sources = new ArrayList<>();
        }

        return sources.contains(source);
    }

    /**
     * @param category to save or remove from the database
     * @return true if the category was saved and false if the category was removed.
     */
    public boolean saveCategoryMenuItemPressed(String category) {
        if (isCategorySaved(category)) {
            userDataRepository.unSaveSource(category);
            return false;
        } else {
            userDataRepository.saveNewSource(category);
            return true;
        }
    }

    /**
     * @param category to check if is already present in the database.
     * @return True if the category is present and false if the category is not present.
     */
    public boolean isCategorySaved(String category) {
        List<String> categories = userDataRepository.getCategoriesLiveData().getValue();
        if (categories == null) {
            categories = new ArrayList<>();
        }

        return categories.contains(category);
    }
}
