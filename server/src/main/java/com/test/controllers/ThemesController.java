package com.test.controllers;

import com.test.account.Account;
import com.test.repository.SimpleAccountRepository;
import com.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;


@Controller
public class ThemesController {
    @Autowired
    AccountService accountService;

    @Autowired
    SimpleAccountRepository simpleAccountRepository;

    @RequestMapping(value = "/{themes}")
    public String setThemes(@PathVariable String themes, Principal principal, HttpSession session, HttpServletRequest request) {
        session.setAttribute("userThemes", themes);
        if(principal != null) {
            Account account = simpleAccountRepository.findOneByEmail(principal.getName());
            account.getInfo().setThemes(themes);
            accountService.update(account);
        }
        return "redirect:" + request.getHeader("Referer");
    }
}
