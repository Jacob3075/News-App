package com.jacob.newsapp.adapters;

public class RecyclerViewModel {

private int  imgArticleImage;
private String tvSource;
private String tvTitle;
private int  btnSaveArticle;

    public RecyclerViewModel(int imgArticleImage, String tvSource, String tvTitle, int btnSaveArticle)
    {
        this.imgArticleImage=imgArticleImage;
        this.tvSource=tvSource;
        this.tvTitle=tvTitle;
        this.btnSaveArticle=btnSaveArticle;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public int getBtnSaveArticle() {
        return btnSaveArticle;
    }

    public String getTvSource() {

        return tvSource;
    }

    public int getImgArticleImage() {
        return imgArticleImage;
    }
}
