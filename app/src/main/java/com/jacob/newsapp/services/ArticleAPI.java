package com.jacob.newsapp.services;

import com.jacob.newsapp.models.MediaStackResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleAPI {

	@GET("news")
	Call<MediaStackResponse> getNewsFromSource(@Query("access_key") String apiKey,
	                                           @Query("sources") String newsSource,
	                                           @Query("languages") String language,
	                                           @Query("limit") int pageSize,
	                                           @Query("offset") int pageOffset);

	@GET("news")
	Call<MediaStackResponse> getTrendingNews(@Query("access_key") String apiKey,
	                                         @Query("languages") String language,
	                                         @Query("limit") int pageSize,
	                                         @Query("offset") int pageOffset);

	@GET("news")
	Call<MediaStackResponse> getNewsByKeyWords(@Query("access_key") String apiKey,
	                                           @Query("keywords") String keywords,
	                                           @Query("languages") String language,
	                                           @Query("limit") int pageSize,
	                                           @Query("offset") int pageOffset);

	@GET("news")
	Call<MediaStackResponse> getNewsFromCategory(@Query("access_key") String apiKey,
	                                             @Query("categories") String category,
	                                             @Query("languages") String language,
	                                             @Query("limit") int pageSize,
	                                             @Query("offset") int pageOffset);

	@GET("news")
	Call<MediaStackResponse> getNews(@Query("access_key") String apiKey,
	                                 @Query("keywords") String keywords,
	                                 @Query("sources") String sources,
	                                 @Query("categories") String category,
	                                 @Query("languages") String language,
	                                 @Query("limit") int pageSize,
	                                 @Query("offset") int pageOffset);
}
