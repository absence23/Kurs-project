package com.test.restcontrollers;

import com.test.account.Account;
import com.test.article.ArticleForm;
import com.test.service.AccountService;
import com.test.repository.SimpleAccountRepository;
import com.test.article.Article;
import com.test.service.ArticleService;
import com.test.repository.SimpleArticleRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.security.Principal;

@RestController
public class ArticleRestController {

    @Inject
    SimpleAccountRepository simpleAccountRepository;
    @Inject
    SimpleArticleRepository simpleArticleRepository;
    @Inject
    AccountService accountService;
    @Inject
    ArticleService articleService;

    @RequestMapping("/article/getlast/{startPosition}/{endPosition}")
    public Object[] getLast(@PathVariable int startPosition, @PathVariable int endPosition){
        return articleService.getLastArticles(startPosition, endPosition).toArray();
    }

    @RequestMapping("/article/count")
    public long getArticleCount(){
        return simpleArticleRepository.count();
    }

    @RequestMapping("/article/get/{id}")
    public Article getArticle(@PathVariable Long id){
        return simpleArticleRepository.findOneById(id);
    }


}
