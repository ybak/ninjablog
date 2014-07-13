package models;

import java.util.List;

import models.Article;

public class ArticlesDto {

    public List<Article> articles;

    public ArticlesDto(List<Article> articles) {
        this.articles = articles;
    }
}
