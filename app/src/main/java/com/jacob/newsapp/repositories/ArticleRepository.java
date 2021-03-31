package com.jacob.newsapp.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jacob.newsapp.models.MediaStackResponse;
import com.jacob.newsapp.services.ArticleAPI;
import com.jacob.newsapp.services.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.jacob.newsapp.utilities.Constants.API_KEY;

public class ArticleRepository {
    private static ArticleAPI articleAPI;
    private final MutableLiveData<MediaStackResponse> liveData = new MutableLiveData<>();

    private static ArticleRepository articleRepository;

    public static ArticleRepository getInstance() {
        if (articleRepository == null) {
            articleRepository = new ArticleRepository();
        }
        return articleRepository;
    }

    public ArticleRepository() {
        articleAPI = RetrofitService.getInterface();
    }

    public LiveData<MediaStackResponse> getNewsBySource(String sources) {
        articleAPI.getNewsFromSource(API_KEY, sources)
                .enqueue(new Callback<MediaStackResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MediaStackResponse> call, @NonNull Response<MediaStackResponse> response) {
                        liveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<MediaStackResponse> call, @NonNull Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
        return liveData;
    }

    public LiveData<MediaStackResponse> getTrendingNews() {
        articleAPI.getTrendingNews(API_KEY)
                .enqueue(new Callback<MediaStackResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MediaStackResponse> call, @NonNull Response<MediaStackResponse> response) {
                        liveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<MediaStackResponse> call, @NonNull Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
        return liveData;
    }

    public LiveData<MediaStackResponse> getNewsByCategory(String category) {
        articleAPI.getNewsFromCategory(API_KEY, category)
                .enqueue(new Callback<MediaStackResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MediaStackResponse> call, @NonNull Response<MediaStackResponse> response) {
                        liveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<MediaStackResponse> call, @NonNull Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
        return liveData;
    }


    public LiveData<MediaStackResponse> getNewsByKeyWord(String keyWord) {
        articleAPI.getNewsByKeyWords(API_KEY, keyWord)
                .enqueue(new Callback<MediaStackResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MediaStackResponse> call, @NonNull Response<MediaStackResponse> response) {
                        liveData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<MediaStackResponse> call, @NonNull Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
        return liveData;
    }

}
