package com.test.article;

import com.test.account.Account;
import com.test.account.AccountService;
import com.test.account.SimpleAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by User on 12.02.2016.
 */
@Controller
public class ArticleController {
    private static final String INFO_VIEW_NAME = "home/profile";

    @Autowired
    private AccountService accountService;

    @Inject
    SimpleAccountRepository accountRepository;

    @Inject
    SimpleArticleRepository articleRepository;

    @RequestMapping(value = "**/article/add/{email}")
    public String addArticle(@PathVariable String email, Model model){
        ArticleForm.isNew = true;
        ArticleForm.email = email;
        model.addAttribute(new ArticleForm());
        model.addAttribute("owner", email);
        return "home/article";
    }


    @RequestMapping(value = "**/article/show/{id}", method = RequestMethod.GET)
    public String articleShow(@PathVariable Long id, Principal principal, Model model) {
        Article article = articleRepository.findOneById(id);
        model.addAttribute("owner", article.getAuthorEmail());
        ArticleForm form = new ArticleForm(article);
        form.setEditable(article.getAuthorEmail().equals(principal.getName()) || accountRepository.findOneByEmail(principal.getName()).getRole().equals("ROLE_ADMIN"));
        model.addAttribute(form);
        return "home/article";
    }

    @RequestMapping(value = "**/article/edit", method = RequestMethod.POST, params = "action=save")
    public String saveArticle(@Valid @ModelAttribute ArticleForm articleForm, Errors errors, RedirectAttributes ra) {
        if(errors.hasErrors())
            return "home/article";
        Account account = accountRepository.findOneByEmail(ArticleForm.email);
        articleForm.saveArticle(account);
        accountService.update(account);
        if(!ArticleForm.isNew)
            return "redirect:/article/show/" + ArticleForm.id;
        ArticleForm.isNew = false;
        return "redirect:/profile/show/" + ArticleForm.email;
    }

    @RequestMapping(value = "**/article/edit", method = RequestMethod.POST, params = "action=delete")
    public String deleteArticle(@Valid @ModelAttribute ArticleForm articleForm, Errors errors, RedirectAttributes ra) {
        Account account = accountRepository.findOneByEmail(ArticleForm.email);
        account.deleteArticle(articleForm.getName());
        accountService.update(account);
        return "redirect:/profile/show/" + ArticleForm.email;
    }

    @RequestMapping(value = "**/article/edit", method = RequestMethod.POST, params = "action=cancel")
    public String cancelEdit(@Valid @ModelAttribute ArticleForm articleForm, Errors errors, RedirectAttributes ra) {
        if(ArticleForm.isNew)
            return "redirect:/profile/show/" + ArticleForm.email;
        return "redirect:/article/show/" + articleForm.id;
    }

}
