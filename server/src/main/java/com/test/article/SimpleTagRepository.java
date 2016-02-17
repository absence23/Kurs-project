package com.test.article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleTagRepository extends JpaRepository<Tag, Long> {
    Tag findOneByName(String name);
    Tag findOneById(Long id);
}
