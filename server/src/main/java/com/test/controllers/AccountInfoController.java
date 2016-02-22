package com.test.controllers;

import com.test.account.Account;
import com.test.account.AccountInfoForm;
import com.test.service.AccountService;
import com.test.repository.SimpleAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;

@Controller
public class AccountInfoController {
    private static final String INFO_VIEW_NAME = "home/profile";

    @Autowired
    private AccountService accountService;

    @Inject
    SimpleAccountRepository accountRepository;

    protected boolean isEditable(String email, Principal principal){
        return principal != null && email.equals(principal.getName()) || accountRepository.findOneByEmail(principal.getName()).getRole().equals("ROLE_ADMIN");
    }

    protected void setAttributes(Model model, Principal principal, String email){
        model.addAttribute("owner", email);
        if(principal != null)
            model.addAttribute("user", principal.getName());
        model.addAttribute("isEditable", isEditable(email, principal));
    }

    @RequestMapping(value = {"/profile/show", "**/profile/show/{email}"}, method = RequestMethod.GET)
	public String profile(@PathVariable Map<String, String> pathVariablesMap, Principal principal, Model model) {
        String email  = pathVariablesMap.get("email");
        if(email == null)
            email = principal.getName();
        Account account = accountRepository.findOneByEmail(email);
        setAttributes(model, principal, email);
        model.addAttribute("userArticles", account.getArticles());
        model.addAttribute(new AccountInfoForm(account));
		return INFO_VIEW_NAME;
	}

    @RequestMapping(value = "/profile/edit/{email}", method = RequestMethod.POST)
    public String saveEdited(@Valid @ModelAttribute AccountInfoForm accountInfoForm, @PathVariable String email, Errors errors, Model model) {
        if(errors.hasErrors())
            return INFO_VIEW_NAME;
        Account account = accountRepository.findOneByEmail(email);
        accountInfoForm.setInfo(account);
        accountService.update(account);
        return "redirect:/profile/show/" + email;
    }
}
