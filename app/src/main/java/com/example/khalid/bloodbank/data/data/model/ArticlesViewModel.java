package com.example.khalid.bloodbank.data.data.model;

public class ArticlesViewModel {

    String article;
    int    fav;
    int    photo;

    public ArticlesViewModel() {
    }

    public ArticlesViewModel(String article, int fav, int photo) {
        this.article = article;
        this.fav = fav;
        this.photo = photo;
    }

    public String getArticle() {
        return article;
    }

    public int getFav() {
        return fav;
    }

    public int getPhoto() {
        return photo;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
