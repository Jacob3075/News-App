package com.jacob.newsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MediaStackResponse {
	@Expose
	@SerializedName("pagination")
	private Pagination pagination;

	@Expose
	@SerializedName("data")
	private List<Article> articles = new ArrayList<>();

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	@NotNull
	@Override
	public String toString() {
		return "MediaStackResponse{" +
		       "pagination=" + pagination +
		       ", articles=" + articles +
		       '}';
	}
}
