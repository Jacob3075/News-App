package com.jacob.newsapp.models;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class User implements Serializable {
    private String uid;
    private String name;
    private String email;

    public User() {}

    public User(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    @Override
    public String toString() {
        return String.format("User{uid='%s', name='%s', email='%s'}", uid, name, email);
    }
}
