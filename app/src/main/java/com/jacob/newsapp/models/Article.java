package com.jacob.newsapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Article implements Parcelable {

    @Expose
    @SerializedName("author")
    private String author;

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("source")
    private String source;

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("image")
    private String image;

    @Expose
    @SerializedName("category")
    private String category;

    @Expose
    @SerializedName("language")
    private String language;

    @Expose
    @SerializedName("country")
    private String country;

    @Expose
    @SerializedName("published_at")
    private String publishedAt;

    public static final Creator<Article> CREATOR =
            new Creator<Article>() {
                @Override
                public Article createFromParcel(Parcel in) {
                    return new Article(in);
                }

                @Override
                public Article[] newArray(int size) {
                    return new Article[size];
                }
            };

    protected Article(Parcel in) {
        author = in.readString();
        title = in.readString();
        description = in.readString();
        source = in.readString();
        url = in.readString();
        image = in.readString();
        category = in.readString();
        language = in.readString();
        country = in.readString();
        publishedAt = in.readString();
    }

    public Article() {}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean notContainsNull() {
        return !containsNull();
    }

    public boolean containsNull() {
        return title == null
                || description == null
                || source == null
                || url == null
                || image == null
                || category == null;
    }

    public boolean notEquals(Object o) {
        return !equals(o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(author, article.author)
                && Objects.equals(title, article.title)
                && Objects.equals(source, article.source)
                && Objects.equals(url, article.url)
                && Objects.equals(publishedAt, article.publishedAt);
    }

    @NotNull
    @Override
    public String toString() {
        return String.format(
                "Article{author='%s', title='%s', description='%s', source='%s', url='%s', image='%s', category='%s',"
                        + " language='%s', country='%s', publishedAt='%s'}",
                author,
                title,
                description,
                source,
                url,
                image,
                category,
                language,
                country,
                publishedAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(source);
        dest.writeString(url);
        dest.writeString(image);
        dest.writeString(category);
        dest.writeString(language);
        dest.writeString(country);
        dest.writeString(publishedAt);
    }
}
