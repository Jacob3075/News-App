package com.jacob.newsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.jacob.newsapp.repositories.FireBaseUserDataRepository;

import java.util.List;

public class SavedCategoriesPageViewModel extends ViewModel {
    private final FireBaseUserDataRepository userDataRepository =
            FireBaseUserDataRepository.getInstance();

    public LiveData<List<String>> getSavedCategories() {
        return userDataRepository.getCategoriesLiveData();
    }
}
