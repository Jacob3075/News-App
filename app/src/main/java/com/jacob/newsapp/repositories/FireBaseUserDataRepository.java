package com.jacob.newsapp.repositories;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.jacob.newsapp.utilities.Constants.*;

public class FireBaseUserDataRepository {

    private static final String TAG = "SavedArticleRepository";
    private static FireBaseUserDataRepository userDataRepository;
    private final MutableLiveData<List<Article>> articlesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<String>> sourcesLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<String>> categoriesLiveData = new MutableLiveData<>();
    private DocumentReference userRefByUid;

    private FireBaseUserDataRepository() {
        updateUser();
    }

    public void updateUser() {
        Log.d(TAG, "updateUser: called ");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) return;

        String userUid = currentUser.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        userRefByUid = db.collection(USERS).document(userUid);
        getUserSavedData();
        Log.d(TAG, "updateUser: called for user: " + userUid);
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

    public static FireBaseUserDataRepository getInstance() {
        if (userDataRepository == null) {
            userDataRepository = new FireBaseUserDataRepository();
        }
        return userDataRepository;
    }

    public void saveNewArticle(Article newArticle) {
        List<Article> value = articlesLiveData.getValue();
        if (value == null) {
            value = new ArrayList<>();
        }
        value.add(newArticle);
        updateUserFieldWithValue(SAVED_ARTICLES, value);
    }

    private <T> void updateUserFieldWithValue(String field, List<T> value) {
        userRefByUid
                .update(field, value)
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

        List<Article> updatedArticlesList =
                articlesLiveDataValue.stream()
                        .filter(article::notEquals)
                        .collect(Collectors.toList());

        updateUserFieldWithValue(SAVED_ARTICLES, updatedArticlesList);
    }

    public void saveNewSource(String newSource) {
        List<String> savedSources = sourcesLiveData.getValue();
        if (savedSources == null) {
            savedSources = new ArrayList<>();
        }
        savedSources.add(newSource);
        updateUserFieldWithValue(SAVED_SOURCES, savedSources);
    }

    public void unSaveSource(String sourceToRemove) {
        List<String> sourcesLiveDataValue = sourcesLiveData.getValue();
        if (sourcesLiveDataValue == null) return;

        List<String> updatedSourcesList =
                sourcesLiveDataValue.stream()
                        .filter(source -> !sourceToRemove.equals(source))
                        .collect(Collectors.toList());

        updateUserFieldWithValue(SAVED_SOURCES, updatedSourcesList);
    }

    public void saveNewCategory(String newCategory) {
        List<String> savedCategories = categoriesLiveData.getValue();
        if (savedCategories == null) {
            savedCategories = new ArrayList<>();
        }
        savedCategories.add(newCategory);
        updateUserFieldWithValue(SAVED_CATEGORIES, savedCategories);
    }

    public void unSaveCategory(String categoryToRemove) {
        List<String> categoriesLiveDataValue = categoriesLiveData.getValue();
        if (categoriesLiveDataValue == null) return;

        List<String> updatedCategoriesList =
                categoriesLiveDataValue.stream()
                        .filter(category -> !categoryToRemove.equals(category))
                        .collect(Collectors.toList());

        updateUserFieldWithValue(SAVED_CATEGORIES, updatedCategoriesList);
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
