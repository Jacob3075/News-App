package com.jacob.newsapp.services;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.jacob.newsapp.utilities.Constants.BASE_URL;

public class RetrofitService {

    public static ArticleAPI create() {
        OkHttpClient okHttpClient = getOkHttpClient();

        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        return retrofit.create(ArticleAPI.class);
    }

    @NotNull
    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        builder.networkInterceptors().add(httpLoggingInterceptor);
        return builder.build();
    }
}
