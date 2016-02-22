package com.test.article;

import com.test.account.Account;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class ArticleForm {

    @NotBlank
    @Valid
    private String name = "Undefined";

    @Length(max = 500)
    private String description = "";

    private String article = "";

    private Article.Category category = Article.Category.BIOLOGY;;

    public String getCategory() {
        return category.name();
    }

    public Article.Category[] getCategories(){
        return Article.Category.values();
    }

    public void setCategory(Article.Category category) {
        this.category = category;
    }

    public void setCategory(String category) {
        this.category = Article.Category.valueOf(category);
    }

    private boolean isEditable;

    public static boolean isNew = false;

    public static Long id;

    public static String email;

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public ArticleForm(Article article) {
        this.name = article.getName();
        this.description = article.getDescription();
        this.article = article.getArticle();
        this.category = article.getCategory();
        isNew = false;
        id = article.getId();
        email = article.getAuthorEmail();
    }

    public ArticleForm() {
    }

    public String getArticle() {
        return article;

    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Article createArticle(Account account){
        Article createdArticle = new Article(name, description, article, category, account);
        id = createdArticle.getId();
        return createdArticle;
    }

    public void saveArticle(Account account){
        setArticleParams(account.getArticleById(id));
    }   

    public void setArticleParams(Article article) {
        article.setName(this.name);
        article.setDescription(this.description);
        article.setArticle(this.article);
        article.setCategory(this.category);
    }
}
