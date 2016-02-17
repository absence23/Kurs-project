package com.test.account;

import com.test.article.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Controller
public class AccountInfoController {
    private static final String INFO_VIEW_NAME = "home/profile";

    @Autowired
    private AccountService accountService;

    @Inject
    SimpleAccountRepository accountRepository;

    @RequestMapping(value = {"**/profile/show", "**/profile/show/{email}"}, method = RequestMethod.GET)
	public String profile(@PathVariable Map<String, String> pathVariablesMap, Principal principal, Model model) {
        String email  = pathVariablesMap.get("email");
        if(email == null)
            email = principal.getName();
        Account account = accountRepository.findOneByEmail(email);
        model.addAttribute("user", principal.getName());
        model.addAttribute("owner", email);
        model.addAttribute("userArticles", account.getArticles());
        AccountInfoForm form = new AccountInfoForm(account);
        form.setEditable(email.equals(principal.getName()) || accountRepository.findOneByEmail(principal.getName()).getRole().equals("ROLE_ADMIN"));
        model.addAttribute(form);
		return "home/profile";
	}

    @RequestMapping(value = "**/profile/edit/{email}", method = RequestMethod.POST)
    public String saveEdited(@Valid @ModelAttribute AccountInfoForm accountInfoForm, @PathVariable String email, Errors errors, Model model) {
        if(errors.hasErrors())
            return INFO_VIEW_NAME;
        Account account = accountRepository.findOneByEmail(email);
        accountInfoForm.setInfo(account);
        accountService.update(account);
        return "redirect:/profile/show/" + email;
    }
}
