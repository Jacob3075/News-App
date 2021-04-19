package com.jacob.newsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.models.User;
import com.jacob.newsapp.repositories.FireBaseUserDataRepository;
import com.jacob.newsapp.repositories.FirebaseAuthRepository;

public class ProfilePageViewModel extends ViewModel {
    private final FirebaseAuthRepository firebaseAuthRepository =
            FirebaseAuthRepository.getInstance();

    private final FireBaseUserDataRepository userDataRepository =
            FireBaseUserDataRepository.getInstance();

    public void logout() {
        firebaseAuthRepository.logout();
    }

    public void saveArticle() {
        Article newArticle = new Article();
        newArticle.setAuthor("Author");
        newArticle.setCategory("Category");
        newArticle.setDescription("Description");
        newArticle.setTitle("Title");
        newArticle.setSource("Source");
        newArticle.setUrl("URL");
        newArticle.setImage("Image");
        userDataRepository.saveNewArticle(newArticle);
    }

    public LiveData<User> getLoggedInUser() {

        return firebaseAuthRepository.getUserLiveData();
    }
}
