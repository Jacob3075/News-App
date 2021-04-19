package com.jacob.newsapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.jacob.newsapp.repositories.FireBaseUserDataRepository;

import java.util.List;

public class SavedSourcesPageViewModel extends ViewModel {
    private final FireBaseUserDataRepository userDataRepository =
            FireBaseUserDataRepository.getInstance();

    public LiveData<List<String>> getSavedSources() {
        return userDataRepository.getSourcesLiveData();
    }
}
