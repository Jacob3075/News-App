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
    private static final String LANGUAGE_ENGLISH = "en";
    private static ArticleAPI articleAPI;
    private final MutableLiveData<MediaStackResponse> newsBySource = new MutableLiveData<>();
    private final MutableLiveData<MediaStackResponse> latestNews = new MutableLiveData<>();
    private final MutableLiveData<MediaStackResponse> newsByCategory = new MutableLiveData<>();
    private final MutableLiveData<MediaStackResponse> newsByKeyWord = new MutableLiveData<>();

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

    public LiveData<MediaStackResponse> getLatestNews() {
        return latestNews;
    }

    public LiveData<MediaStackResponse> getNewsBySource() {
        return newsBySource;
    }

    public LiveData<MediaStackResponse> getNewsByCategory() {
        return newsByCategory;
    }

    public LiveData<MediaStackResponse> getNewsByKeyWord() {
        return newsByKeyWord;
    }

    public void getNewsBySource(String sources) {
        articleAPI.getNewsFromSource(API_KEY, sources, LANGUAGE_ENGLISH)
                .enqueue(new Callback<MediaStackResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MediaStackResponse> call, @NonNull Response<MediaStackResponse> response) {
                        if (response.isSuccessful()) {
                            newsBySource.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MediaStackResponse> call, @NonNull Throwable t) {
                        System.out.println("ERROR: " + t.getMessage());
                    }
                });
    }

    public void getTrendingByNews() {
        articleAPI.getTrendingNews(API_KEY, LANGUAGE_ENGLISH)
                .enqueue(new Callback<MediaStackResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MediaStackResponse> call, @NonNull Response<MediaStackResponse> response) {
                        if (response.isSuccessful()) {
                            latestNews.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MediaStackResponse> call, @NonNull Throwable t) {
                        System.out.println("ERROR: " + t.getMessage());
                    }
                });
    }

    public void getNewsByCategory(String category) {
        articleAPI.getNewsFromCategory(API_KEY, category, LANGUAGE_ENGLISH)
                .enqueue(new Callback<MediaStackResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MediaStackResponse> call, @NonNull Response<MediaStackResponse> response) {
                        if (response.isSuccessful()) {
                            newsByCategory.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MediaStackResponse> call, @NonNull Throwable t) {
                        System.out.println("ERROR: " + t.getMessage());
                    }
                });
    }

    public void getNewsByKeyWord(String keyWord) {
        articleAPI.getNewsByKeyWords(API_KEY, keyWord, LANGUAGE_ENGLISH)
                .enqueue(new Callback<MediaStackResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MediaStackResponse> call, @NonNull Response<MediaStackResponse> response) {
                        if (response.isSuccessful()) {
                            newsByKeyWord.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MediaStackResponse> call, @NonNull Throwable t) {
                        System.out.println("ERROR: " + t.getMessage());
                    }
                });
    }

}
