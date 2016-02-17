package com.test.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ArticleService {

    @Autowired
    SimpleArticleRepository articleRepository;

    @PostConstruct
    protected void initialize() {}

    public List<Article> getLastArticles(int count){
        List<Article> articles = articleRepository.findAll();
        Collections.reverse(articles);
        int size = articles.size();
        if(size <= count)
            return articles;
        List<Article> lastArticles = new ArrayList<>(count);
        for (int i = 0; i < count; i++)
            lastArticles.add(articles.get(i));
        return lastArticles;
    }

}
