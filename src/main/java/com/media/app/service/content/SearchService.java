package com.media.app.service.content;

import com.media.app.service.article.ArticleDTO;

import java.util.List;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
public interface SearchService {
  List<ArticleDTO> searchArticle(String query, String offset);
}
