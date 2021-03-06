package com.jacob.newsapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class User implements Parcelable {
    public static final Creator<User> CREATOR =
            new Creator<User>() {
                @NotNull
                @Contract("_ -> new")
                @Override
                public User createFromParcel(Parcel in) {
                    return new User(in);
                }

                @NotNull
                @Contract(value = "_ -> new", pure = true)
                @Override
                public User[] newArray(int size) {
                    return new User[size];
                }
            };
    private String uid;
    private String name;
    private String email;
    private List<Article> savedArticles;
    private List<String> savedCategories;
    private List<String> savedSources;

    public User() {}

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public User(
            String uid,
            String name,
            String email,
            List<String> savedCategories,
            List<String> savedSources,
            List<Article> savedArticles) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.savedCategories = savedCategories;
        this.savedSources = savedSources;
        this.savedArticles = savedArticles;
    }

    protected User(@NotNull Parcel in) {
        uid = in.readString();
        name = in.readString();
        email = in.readString();
        savedCategories = in.createStringArrayList();
        savedSources = in.createStringArrayList();
        savedArticles = in.createTypedArrayList(Article.CREATOR);
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public List<Article> getSavedArticles() {
        return savedArticles;
    }

    public void setSavedArticles(List<Article> savedArticles) {
        this.savedArticles = savedArticles;
    }

    public List<String> getSavedCategories() {
        return savedCategories;
    }

    public void setSavedCategories(List<String> savedCategories) {
        this.savedCategories = savedCategories;
    }

    public List<String> getSavedSources() {
        return savedSources;
    }

    public void setSavedSources(List<String> savedSources) {
        this.savedSources = savedSources;
    }

    @NotNull
    @Override
    public String toString() {
        return String.format(
                "User{uid='%s', name='%s', email='%s', savedCategories=%s, savedSources=%s, savedArticles=%s}",
                uid, name, email, savedCategories, savedSources, savedArticles);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NotNull Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeStringList(savedCategories);
        dest.writeStringList(savedSources);
        dest.writeTypedList(savedArticles);
    }
}
