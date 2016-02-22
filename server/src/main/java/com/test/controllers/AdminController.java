package com.test.controllers;

import com.test.account.Account;
import com.test.repository.SimpleAccountRepository;
import com.test.repository.SimpleArticleRepository;
import com.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;


@Controller
public class AdminController {
    private static final String USERS_VIEW_NAME = "home/users";

    @Inject
    private AccountService accountService;

    @Inject
    SimpleAccountRepository accountRepository;

    @RequestMapping(value = "admin/users", method = RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public String allUsers(Model model){
        List<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts", accounts);
        return USERS_VIEW_NAME;
    }


}
