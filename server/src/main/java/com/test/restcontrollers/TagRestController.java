package com.test.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.service.AccountService;
import com.test.article.Article;
import com.test.repository.SimpleArticleRepository;
import com.test.repository.SimpleTagRepository;
import com.test.article.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
public class TagRestController {

    @Autowired
    SimpleArticleRepository articleRepository;
    @Autowired
    SimpleTagRepository simpleTagRepository;
    @Autowired
    AccountService accountService;


    @RequestMapping(value = "/article/settags/{id}", method = RequestMethod.POST)
    public int setTags(@RequestBody String tags, @PathVariable Long id) throws IOException {
        Article article = articleRepository.findOneById(id);
        Set<Tag> tagSet = new HashSet<>(Arrays.asList((new ObjectMapper()).readValue(tags, Tag[].class)));
        for (Tag tag : tagSet) {
            Tag tempTag;
            if ((tempTag = simpleTagRepository.findOneByName(tag.getName())) != null)
                tag.copyFrom(tempTag);
            else
                simpleTagRepository.save(tag);
            tag.addArticle(article);
        }
        article.updateTags(tagSet);
        accountService.update(article);
        checkForEmptyTags();
        return 0;
    }

    @RequestMapping(value = "/article/gettags/{id}", method = RequestMethod.GET)
    public Object[] getTags(@PathVariable Long id) {
        return articleRepository.findOneById(id).sendedTags().toArray();
    }

    @RequestMapping(value = "/article/gettags/all", method = RequestMethod.GET)
    public Object[] getAllTags() {
        return simpleTagRepository.findAll().toArray();
    }

    @RequestMapping(value = "/tag/check")
    public int checkForEmptyTags(){
        for(Tag tag: simpleTagRepository.findAll())
            if(tag .isEmpty())
                simpleTagRepository.delete(tag);
        return 0;
    }
}
