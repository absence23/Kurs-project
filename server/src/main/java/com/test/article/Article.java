package com.test.article;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.JsonGeneratorDelegate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.test.account.Account;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import javax.persistence.*;
import java.util.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "article")
public class Article implements java.io.Serializable {

    public static enum Category{
        BIOLOGY, PROGRAMMING, MATHEMATICS, PHYSICS, LITERATURE, CHEMISTRY, ENGLISH, GERMAN, OTHERS
    }

    @Id
    @GeneratedValue
    private Long id;


    @Column(nullable = false)
    private String name;

    private String description;

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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name="article_tags",
            joinColumns=
            @JoinColumn(name="article_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="tag_id", referencedColumnName="id")
    )
    Set<Tag> tags = new HashSet<>();

    public void setTags(Set<Tag> tags) {
        this.tags = new HashSet<>(tags);
    }

    @JsonIgnore
    public Set<Tag> getTags() {
        return tags;
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
        Set<Tag> tagsCopy = new HashSet<>(this.tags);
        for(Tag tag: tagsCopy)
            if(!tags.contains(tag)) {
                tag.deleteArticle(this);
                this.tags.remove(tag);
            }
        this.tags.addAll(tags);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null)
            return id == ((Article)obj).id;
        return false;
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


    public Set<Tag> sendedTags() {
        return tags;
    }

    @Override
    public String toString() {
        return name;
    }

}