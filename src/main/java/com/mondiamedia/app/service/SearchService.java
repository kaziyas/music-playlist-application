package com.mondiamedia.app.service;

import com.mondiamedia.app.service.shared.ArticleDTO;
import java.util.List;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
public interface SearchService {
  List<ArticleDTO> searchArticle(String query, String offset);
}
