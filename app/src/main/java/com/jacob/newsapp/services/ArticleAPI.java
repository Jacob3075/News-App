package com.jacob.newsapp.services;

import com.jacob.newsapp.models.MediaStackResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleAPI {
    @GET("news")
    Call<MediaStackResponse> getNews(
            @Query("access_key") String apiKey,
            @Query("keywords") String keywords,
            @Query("sources") String sources,
            @Query("categories") String category,
            @Query("languages") String language,
            @Query("limit") int pageSize,
            @Query("offset") int pageOffset);
}
