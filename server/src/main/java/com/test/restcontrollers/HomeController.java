package com.test.restcontrollers;

import java.security.Principal;

import com.test.repository.SimpleAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	SimpleAccountRepository simpleAccountRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal, HttpSession session) {
		if(principal == null) {
			session.setAttribute("userThemes", "light");
			return "index";
		}
		session.setAttribute("userThemes", simpleAccountRepository.findOneByEmail(principal.getName()).getInfo().getThemes());
		return "home/main";
	}
}