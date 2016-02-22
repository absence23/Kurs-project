package com.test.repository;

import com.test.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleArticleRepository extends JpaRepository<Article, Long> {
    Article findOneById(Long id);
}
