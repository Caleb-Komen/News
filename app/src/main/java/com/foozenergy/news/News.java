package com.foozenergy.news;

public class News {

    String title, category, webUrl, image;

    public News(String title, String category, String webUrl, String image) {
        this.title = title;
        this.category = category;
        this.webUrl = webUrl;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getImage() {
        return image;
    }
}
