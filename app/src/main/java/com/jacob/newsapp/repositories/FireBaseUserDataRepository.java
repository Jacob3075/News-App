package com.jacob.newsapp.repositories;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.jacob.newsapp.utilities.Constants.*;

// TODO: DELETE DATA FUNCTIONALITY
public class FireBaseUserDataRepository {

    private static final String TAG = "SavedArticleRepository";
    private static FireBaseUserDataRepository userDataRepository;
    private final DocumentReference userRefByUid;
    private final MutableLiveData<List<Article>> articlesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<String>> sourcesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<String>> categoriesLiveData = new MutableLiveData<>();

    private FireBaseUserDataRepository() {
        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userRefByUid = db.collection(USERS).document(userUid);
        getUserSavedData();
    }

    public static FireBaseUserDataRepository getInstance() {
        if (userDataRepository == null) {
            userDataRepository = new FireBaseUserDataRepository();
        }
        return userDataRepository;
    }

    private void getUserSavedData() {
        userRefByUid.addSnapshotListener(
                (documentSnapshot, exception) -> {
                    if (exception != null) {
                        Log.e(TAG, "ERROR IN SNAPSHOT LISTENER: " + exception.getMessage());
                        return;
                    }

                    if (documentSnapshot == null || !documentSnapshot.exists()) return;

                    User user = documentSnapshot.toObject(User.class);
                    if (user == null) return;

                    List<Article> savedArticles = user.getSavedArticles();
                    List<String> savedCategories = user.getSavedCategories();
                    List<String> savedSources = user.getSavedSources();
                    articlesLiveData.setValue(savedArticles);
                    sourcesLiveData.setValue(savedSources);
                    categoriesLiveData.setValue(savedCategories);
                });
    }

    public void saveNewArticle(Article newArticle) {
        List<Article> value = articlesLiveData.getValue();
        if (value == null) {
            value = new ArrayList<>();
        }
        value.add(newArticle);
        updateSavedArticles(value);
    }

    private void updateSavedArticles(List<Article> value) {
        userRefByUid
                .update(SAVED_ARTICLES, value)
                .addOnSuccessListener(voidTask -> {})
                .addOnFailureListener(
                        exception ->
                                Log.e(
                                        TAG,
                                        ("ERROR WHILE UPDATING SAVED ARTICLES: "
                                                + exception.getMessage())));
    }

    public void unSaveArticle(Article article) {
        List<Article> articlesLiveDataValue = articlesLiveData.getValue();
        if (articlesLiveDataValue == null) return;

        List<Article> articles =
                articlesLiveDataValue.stream()
                        .filter(article::notEquals)
                        .collect(Collectors.toList());

        updateSavedArticles(articles);
    }

    public void saveNewSource(String newCategory) {
        List<String> savedSources = sourcesLiveData.getValue();
        if (savedSources == null) {
            savedSources = new ArrayList<>();
        }
        savedSources.add(newCategory);
        updateSavedSources(savedSources);
    }

    private void updateSavedSources(List<String> newSavedSources) {
        userRefByUid
                .update(SAVED_SOURCES, newSavedSources)
                .addOnSuccessListener(voidTask -> {})
                .addOnFailureListener(
                        exception ->
                                Log.e(
                                        TAG,
                                        ("ERROR WHILE UPDATING SAVED SOURCES: "
                                                + exception.getMessage())));
    }

    public void saveNewCategory(String newCategory) {
        List<String> savedCategories = categoriesLiveData.getValue();
        if (savedCategories == null) {
            savedCategories = new ArrayList<>();
        }
        savedCategories.add(newCategory);
        updateSavedCategories(savedCategories);
    }

    private void updateSavedCategories(List<String> newSavedCategories) {
        userRefByUid
                .update(SAVED_CATEGORIES, newSavedCategories)
                .addOnSuccessListener(voidTask -> {})
                .addOnFailureListener(
                        exception ->
                                Log.e(
                                        TAG,
                                        ("ERROR WHILE UPDATING SAVED CATEGORIES: "
                                                + exception.getMessage())));
    }

    public LiveData<List<Article>> getArticlesLiveData() {
        return articlesLiveData;
    }

    public LiveData<List<String>> getSourcesLiveData() {
        return sourcesLiveData;
    }

    public LiveData<List<String>> getCategoriesLiveData() {
        return categoriesLiveData;
    }
}
