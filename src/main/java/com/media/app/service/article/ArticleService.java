package com.media.app.service.article;

import java.util.List;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
public interface ArticleService {
  ArticleDTO getArticleByArticleId(String articleId);

  void saveSearchedArticles(List<ArticleDTO> articles);
}
