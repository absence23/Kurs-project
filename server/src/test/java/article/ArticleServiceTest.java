package article;

import static org.junit.Assert.*;
import com.test.article.ArticleService;
import com.test.article.SimpleArticleRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
public class ArticleServiceTest {

 //   @Autowired
    private ArticleService articleService;

 //   @Autowired
    private SimpleArticleRepository articleRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldInjectService() {
        assertNull(articleService);
    }

    @Test
    public void shouldInjectRepository() {
        assertNull(articleRepository);
    }

//    @Test
//    public void shouldReturnArticles(){
//        assertTrue(articleService.getLastArticles(5).size() <= 5);
//        assertTrue(articleService.getLastArticles(5).size() > 0);
//    }

}

