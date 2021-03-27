package com.jacob.newsapp.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jacob.newsapp.utilities.Constants.BASE_URL;

public class RetrofitService {

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static ArticleAPI getInterface() {
        return retrofit.create(ArticleAPI.class);
    }

}
