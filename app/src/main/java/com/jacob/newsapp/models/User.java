package com.jacob.newsapp.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class User implements Serializable {
	private String  uid;
	private String  name;
	@SuppressWarnings("WeakerAccess")
	private String  email;
	@Exclude
	private boolean isAuthenticated;
	@Exclude
	private boolean isNew, isCreated;

	public User() {
	}

	public User(String uid, String name, String email) {
		this.uid   = uid;
		this.name  = name;
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

	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		isAuthenticated = authenticated;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean aNew) {
		isNew = aNew;
	}

	public boolean isCreated() {
		return isCreated;
	}

	public void setCreated(boolean created) {
		isCreated = created;
	}
}
