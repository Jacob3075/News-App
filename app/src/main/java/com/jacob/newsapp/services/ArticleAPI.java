package com.jacob.newsapp.services;

import com.jacob.newsapp.models.MediaStackResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleAPI {

    @GET("news")
    Call<MediaStackResponse> getNewsFromSource(@Query("sources") String newsSource, @Query("access_key") String apiKey, @Query("languages") String language);

    @GET("news")
    Call<MediaStackResponse> getTrendingNews(@Query("access_key") String apiKey, @Query("languages") String language);

    @GET("news")
    Call<MediaStackResponse> getNewsByKeyWords(@Query("access_key") String apiKey, @Query("keywords") String keywords, @Query("languages") String language);

    @GET("news")
    Call<MediaStackResponse> getNewsFromCategory(@Query("access_key") String apiKey, @Query("category") String category, @Query("languages") String language);
}
