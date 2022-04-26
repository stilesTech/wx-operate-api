package com.wx.operate.repository;

import com.wx.operate.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author stiles
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    @Query(value = "select url from article where md5 = ?1",nativeQuery = true)
    String getUrl(String md5);
}
