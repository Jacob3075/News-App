package com.jacob.newsapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class User implements Parcelable {
    private String uid;
    private String name;
    private String email;
    public static final Creator<User> CREATOR =
            new Creator<User>() {
                @Override
                public User createFromParcel(Parcel in) {
                    return new User(in);
                }

                @Override
                public User[] newArray(int size) {
                    return new User[size];
                }
            };
    private List<String> savedCategories;
    private List<String> savedSources;

    public User() {}

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    private List<Article> savedArticles;

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

    protected User(Parcel in) {
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

    public List<String> getSavedCategories() {
        return savedCategories;
    }

    public List<String> getSavedSources() {
        return savedSources;
    }

    public List<Article> getSavedArticles() {
        return savedArticles;
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeStringList(savedCategories);
        dest.writeStringList(savedSources);
        dest.writeTypedList(savedArticles);
    }
}
