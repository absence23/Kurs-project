package com.test.article;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.account.Account;

import javax.persistence.*;
import java.util.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "article")
public class Article implements java.io.Serializable {

    public static enum Category{
        BIOLOGY, PROGRAMMING, MATHEMATICS
    }

    @Id
    @GeneratedValue
    private Long id;


    @Column(nullable = false)
    private String name;

    private String description;

    @Column(length = 20000)
    private String article;

    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "account_id")
    private Account account;

    private String authorEmail;


    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name="article_tags",
            joinColumns=
            @JoinColumn(name="article_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="tag_id", referencedColumnName="id")
    )
    private Set<Tag> tags = new HashSet<>();

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = new HashSet<>(tags);
    }
    public Article() {}

    public static Article createEmpty(Account account){
        return new Article("", "", "", Category.BIOLOGY, account);
    }

    public Article(String name, String description, String article, Category category, Account account) {
        this.name = name;
        this.description = description;
        this.article = article;
        this.category = category;
        this.account = account;
        this.authorEmail = account.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void updateTags(Set<Tag> tags){
        for(Tag tag: this.tags)
            if(tags.contains(tag))
                this.tags.remove(tag);
        this.tags.addAll(tags);
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void setName(String fileName) {
        this.name = fileName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    @Override
    public String toString() {
        return name;
    }
}