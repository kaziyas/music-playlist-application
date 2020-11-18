package com.mondiamedia.app.service;

import com.mondiamedia.app.domainmodel.article.Article;
import com.mondiamedia.app.domainmodel.article.ArticleRepository;
import com.mondiamedia.app.domainmodel.playlist.PlaylistRepository;
import com.mondiamedia.app.service.article.ArticleDTO;
import com.mondiamedia.app.service.article.ArticleService;
import com.mondiamedia.app.service.article.ArticleServiceException;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService {
  private final ArticleRepository articleRepository;
  private final PlaylistRepository playlistRepository;

  public ArticleServiceImpl(
      PlaylistRepository playlistRepository, ArticleRepository articleRepository) {
    this.playlistRepository = playlistRepository;
    this.articleRepository = articleRepository;
  }

  @Override
  public ArticleDTO getArticleByArticleId(String articleId) {
    Article article = articleRepository.findByArticleId(articleId);
    if (article == null) {
      throw new ArticleServiceException("Article with ID: " + articleId + " not found");
    }

    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(article, ArticleDTO.class);
  }

  @Override
  public void saveSearchedArticles(List<ArticleDTO> articles) {
    ModelMapper modelMapper = new ModelMapper();
    articles.stream()
        .filter(articleDTO -> articleRepository.findByArticleId(articleDTO.getArticleId()) == null)
        .map(articleDTO -> modelMapper.map(articleDTO, Article.class))
        .map(articleRepository::save);
  }
}
