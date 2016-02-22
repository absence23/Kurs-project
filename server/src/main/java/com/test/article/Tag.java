package com.test.article;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@Table(name = "tag")
public class Tag  {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name="article_tags",
            joinColumns=
            @JoinColumn(name="tag_id", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="article_id", referencedColumnName="id")
    )
    private Set<Article> articles = new HashSet<>();
    public Set<Article> getArticles(){
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    public void addArticle(Article article){
        this.articles.add(article);
    }

    public Tag(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Tag(){}

    public void deleteArticle(Article article){
        articles.remove(article);
    }

    public void copyFrom(Tag tag){
        name = tag.name;
        id = tag.id;
        articles = tag.articles;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null)
            return id == ((Tag)obj).id;
        return false;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isEmpty(){
        return articles.isEmpty();
    }

    public Set<Article> sendedArticles(){
        return articles;
    }
}
