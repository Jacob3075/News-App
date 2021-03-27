package com.jacob.newsapp.services;

import com.jacob.newsapp.models.MediaStackResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleAPI {

    @GET("top-headlines")
    Call<MediaStackResponse> getNewsList(@Query("sources") String newsSource, @Query("apiKey") String apiKey);
}
