package com.example.Varsani.Applicants.Models;

public class ArticlesMdel {
    private String articleID;
    private String title;
    private String content;
    private String dateCreated;


    public ArticlesMdel(String articleID, String title, String content, String dateCreated) {
        this.articleID = articleID;
        this.title = title;
        this.content = content;
        this.dateCreated = dateCreated;
    }

    public String getArticleID() {
        return articleID;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDateCreated() {
        return dateCreated;
    }

}
