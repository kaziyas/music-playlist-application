package com.mondiamedia.app.io.repository;

import com.mondiamedia.app.io.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

  ArticleEntity findByArticleId(String articleId);
}
