package com.test.restcontrollers;

import com.test.account.*;
import com.test.article.Article;
import com.test.article.ArticleService;
import com.test.article.SimpleArticleRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AppController {

    @Inject
    SimpleAccountRepository simpleAccountRepository;
    @Inject
    SimpleArticleRepository simpleArticleRepository;
    @Inject
    AccountService accountService;
    @Inject
    ArticleService articleService;

    @RequestMapping("/article/getlast")
    public Object[] getLast(){
        return articleService.getLastArticles(5).toArray();
    }

    @RequestMapping("/article/get/{id}")
    public Article getArticle(@PathVariable Long id){
        return simpleArticleRepository.findOneById(id);
    }


    @RequestMapping("**/email")
    public String getEmail(Principal principal){
        return principal.getName();
    }

    @RequestMapping("**/role")
    public String getRole(Principal principal){
        return simpleAccountRepository.findOneByEmail(principal.getName()).getRole();
    }

    @RequestMapping("**/current_user")
    public Object getCurrentUser(Principal principal){
        return simpleAccountRepository.findOneByEmail(principal.getName());
    }

    @RequestMapping("**/users")
    public Object[] getUsers(){
        List<Account> allAccounts =  simpleAccountRepository.findAll();
        List<Account> users = new ArrayList<Account>();
        for(Account a: allAccounts)
            if(a.getRole().equals("ROLE_USER"))
                users.add(a);
        return users.toArray();
    }

    @RequestMapping(value = "**/user/delete/{name}", method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public void deleteUser(@PathVariable String name){
        simpleAccountRepository.delete(simpleAccountRepository.findOneByEmail(name));
    }

    @RequestMapping(value = "**/user/password/{name}", method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public void changePassword(@RequestBody String password, @PathVariable String name){
        Account account = simpleAccountRepository.findOneByEmail(name);
        account.setPassword(password);
        accountService.update(account);
    }
}
