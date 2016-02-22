package com.test.controllers;


import com.test.repository.SimpleTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TagController {
    private static final String SEARCH_VIEW_NAME = "home/search";

    @Autowired
    SimpleTagRepository simpleTagRepository;

    @RequestMapping(value = "/tag/articles/{name}", method = RequestMethod.GET)
    public String getTagArticles(@PathVariable String name, Model model){
        model.addAttribute("tag", name);
        model.addAttribute("articles", simpleTagRepository.findOneByName(name).sendedArticles());
        return SEARCH_VIEW_NAME;
    }
}
