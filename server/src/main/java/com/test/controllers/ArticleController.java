package com.test.controllers;

import com.test.account.Account;
import com.test.service.AccountService;
import com.test.repository.SimpleAccountRepository;
import com.test.article.Article;
import com.test.article.ArticleForm;
import com.test.repository.SimpleArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;


@Controller
public class ArticleController {
    private static final String ARTICLE_VIEW_NAME = "home/article";

    @Autowired
    private AccountService accountService;

    @Inject
    SimpleAccountRepository accountRepository;

    @Inject
    SimpleArticleRepository articleRepository;

    protected boolean isEditable(String email, Principal principal){
        return email.equals(principal.getName()) || accountRepository.findOneByEmail(principal.getName()).getRole().equals("ROLE_ADMIN");
    }

    protected void setAttributes(Model model, Principal principal, String email){
        model.addAttribute("owner", email);
        model.addAttribute("isEditable", isEditable(email, principal));
    }

    @RequestMapping(value = "/article/add/{email}")
    public String addArticle(@PathVariable String email, Principal principal, Model model){
        ArticleForm.isNew = true;
        ArticleForm.email = email;
        ArticleForm form = new ArticleForm();
        model.addAttribute(form);
        ArticleForm.id = articleRepository.save(form.createArticle(accountRepository.findOneByEmail(email))).getId();
        setAttributes(model, principal, email);
        return ARTICLE_VIEW_NAME;
    }


    @RequestMapping(value = "/article/show/{id}", method = RequestMethod.GET)
    public String articleShow(@PathVariable Long id, Principal principal, Model model) {
        Article article = articleRepository.findOneById(id);
        ArticleForm form = new ArticleForm(article);
        setAttributes(model, principal, article.getAuthorEmail());
        model.addAttribute(form);
        return ARTICLE_VIEW_NAME;
    }

    @RequestMapping(value = "/article/edit", method = RequestMethod.POST, params = "action=save")
    public String saveArticle(@Valid @ModelAttribute ArticleForm articleForm, Errors errors, RedirectAttributes ra) {
        if(errors.hasErrors())
            return ARTICLE_VIEW_NAME;
        Account account = accountRepository.findOneByEmail(ArticleForm.email);
        articleForm.saveArticle(account);
        accountService.update(account);
        return "redirect:/article/show/" + ArticleForm.id;
    }


    @RequestMapping(value = "/article/edit", method = RequestMethod.POST, params = "action=cancel")
    public String cancelEdit(@Valid @ModelAttribute ArticleForm articleForm, Errors errors, RedirectAttributes ra) {
        if(ArticleForm.isNew) {
            articleRepository.delete(articleRepository.findOneById(ArticleForm.id));
            return "redirect:/profile/show/" + ArticleForm.email;
        }
        return "redirect:/article/show/" + articleForm.id;
    }

    @RequestMapping(value = "/article/delete/{id}", method = RequestMethod.GET)
    public String deleteArticle(@PathVariable Long id, Principal principal) {
        if(isEditable(ArticleForm.email, principal)) {
            Article article = articleRepository.findOneById(id);
            article.setTags(new HashSet<>(0));
            article = (Article)accountService.update(article);
            articleRepository.delete(article);
        }
        return "redirect:/profile/show/" + ArticleForm.email;
    }

}
