package com.jacob.newsapp.services;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import com.jacob.newsapp.models.Article;
import com.jacob.newsapp.models.MediaStackResponse;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.stream.Collectors;

import static com.jacob.newsapp.utilities.Constants.*;

public class NewsDataSource extends PageKeyedDataSource<Integer, Article> {

    private static final String TAG = NewsDataSource.class.getName();
    private final ArticleAPI articleAPI;
    private final String keywordsQuery;
    private final String sourceQuery;
    private final String categoryQuery;

    public NewsDataSource(String keywordsQuery, String sourceQuery, String categoryQuery) {
        this.keywordsQuery = keywordsQuery;
        this.sourceQuery = sourceQuery;
        this.categoryQuery = categoryQuery;
        articleAPI = RetrofitService.create();
    }

    @Override
    public void loadInitial(
            @NonNull LoadInitialParams<Integer> params,
            @NonNull LoadInitialCallback<Integer, Article> callback) {
        articleAPI
                .getNews(
                        API_KEY,
                        keywordsQuery,
                        sourceQuery,
                        categoryQuery,
                        LANGUAGE_ENGLISH,
                        PAGE_SIZE,
                        0)
                .enqueue(
                        new Callback<MediaStackResponse>() {
                            @Override
                            public void onResponse(
                                    @NotNull Call<MediaStackResponse> call,
                                    @NotNull Response<MediaStackResponse> response) {
                                if (response.isSuccessful()) {
                                    callback.onResult(
                                            response.body().getArticles().stream()
                                                    .filter(Article::notContainsNull)
                                                    .collect(Collectors.toList()),
                                            null,
                                            PAGE_SIZE * 2);
                                }
                            }

                            @Override
                            public void onFailure(
                                    @NotNull Call<MediaStackResponse> call, @NotNull Throwable t) {
                                Log.e(TAG, "onFailure: " + t);
                            }
                        });
    }

    @Override
    public void loadBefore(
            @NonNull LoadParams<Integer> params,
            @NonNull LoadCallback<Integer, Article> callback) {}

    @Override
    public void loadAfter(
            @NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Article> callback) {
        articleAPI
                .getNews(
                        API_KEY,
                        keywordsQuery,
                        sourceQuery,
                        categoryQuery,
                        LANGUAGE_ENGLISH,
                        PAGE_SIZE,
                        params.key)
                .enqueue(
                        new Callback<MediaStackResponse>() {
                            @Override
                            public void onResponse(
                                    @NotNull Call<MediaStackResponse> call,
                                    @NotNull Response<MediaStackResponse> response) {
                                if (response.isSuccessful()) {
                                    callback.onResult(
                                            response.body().getArticles().stream()
                                                    .filter(Article::notContainsNull)
                                                    .collect(Collectors.toList()),
                                            params.key + PAGE_SIZE);
                                }
                            }

                            @Override
                            public void onFailure(
                                    @NotNull Call<MediaStackResponse> call, @NotNull Throwable t) {
                                Log.e(TAG, "onFailure: " + t);
                            }
                        });
    }
}
