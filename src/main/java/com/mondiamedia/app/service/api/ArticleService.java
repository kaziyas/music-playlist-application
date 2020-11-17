package com.mondiamedia.app.service.api;

import com.mondiamedia.app.service.article.ArticleDTO;
import java.util.List;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
public interface ArticleService {
  List<ArticleDTO> getArticles(String playlistId);

  ArticleDTO getArticleByArticleId(String articleId);

  void saveSearchedArticles(List<ArticleDTO> articles);
}
