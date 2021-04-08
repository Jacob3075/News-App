package com.jacob.newsapp.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class User implements Serializable {
    private final String uid;
    private String name;
    private final String email;
    @Exclude private boolean isAuthenticated;
    @Exclude private boolean isNew;
    @Exclude private boolean isCreated;

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public void setCreated(boolean created) {
        isCreated = created;
    }

    public String getEmail() {
        return email;
    }

    public boolean isCreated() {
        return isCreated;
    }

    public void setName(String userName) {
        name = userName;
    }
}
