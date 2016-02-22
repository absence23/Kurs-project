package com.test.service;

import com.test.article.Article;
import com.test.repository.SimpleArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;


@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ArticleService {

    @Autowired
    SimpleArticleRepository articleRepository;

    @PostConstruct
    protected void initialize() {}

    public List<Article> getLastArticles(int start, int end){
        List<Article> articles = articleRepository.findAll();
        Collections.reverse(articles);
        if(articles.size() < end)
            return articles.subList(start, articles.size());
        return articles.subList(start, end);
    }

}
