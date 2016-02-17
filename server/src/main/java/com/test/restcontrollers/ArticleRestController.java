package com.test.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.account.AccountService;
import com.test.article.Article;
import com.test.article.SimpleArticleRepository;
import com.test.article.SimpleTagRepository;
import com.test.article.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
public class ArticleRestController {

    @Autowired
    SimpleArticleRepository articleRepository;
    @Autowired
    SimpleTagRepository simpleTagRepository;
    @Autowired
    AccountService accountService;


    @RequestMapping(value = "**/article/settags/{id}", method = RequestMethod.POST)
    public int setTags(@RequestBody String tags, @PathVariable Long id) throws IOException {
        Article article = articleRepository.findOneById(id);
        Set<Tag> tagSet = new HashSet<>(Arrays.asList((new ObjectMapper()).readValue(tags, Tag[].class)));
        for (Tag tag : tagSet) {
            Tag temp;
            if((temp = simpleTagRepository.findOneByName(tag.getName())) != null)
                tag.copyFrom(temp);
            tag.addArticle(article);
            accountService.update(tag);
        }
        article = articleRepository.findOneById(id);
//        article.updateTags(tagSet);
//        accountService.update(article);
        return 0;
    }

    @RequestMapping(value = "**/article/gettags/{id}", method = RequestMethod.GET)
    public Object[] getTags(@PathVariable Long id) {
        return articleRepository.findOneById(id).getTags().toArray();
    }
}
