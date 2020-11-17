package com.mondiamedia.app.service;

import com.mondiamedia.app.domainmodel.article.Article;
import com.mondiamedia.app.domainmodel.article.ArticleRepository;
import com.mondiamedia.app.domainmodel.playlist.Playlist;
import com.mondiamedia.app.domainmodel.playlist.PlaylistRepository;
import com.mondiamedia.app.service.article.ArticleServiceException;
import com.mondiamedia.app.service.article.ArticleService;
import com.mondiamedia.app.service.article.ArticleDTO;
import java.util.ArrayList;
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
  public List<ArticleDTO> getArticles(String playlistId) {
    List<ArticleDTO> returnValue = new ArrayList<>();
    ModelMapper modelMapper = new ModelMapper();

    Playlist playlist = playlistRepository.findByPlaylistId(playlistId);

    if (playlist == null) return returnValue;

    final List<Article> articles = playlist.getArticles();
    for (Article article : articles) {
      returnValue.add(modelMapper.map(article, ArticleDTO.class));
    }

    return returnValue;
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
    List<ArticleDTO> returnValue = new ArrayList<>();
    ModelMapper modelMapper = new ModelMapper();

    for (ArticleDTO articleDTO : articles) {
      if (articleRepository.findByArticleId(articleDTO.getArticleId()) != null) continue;

      Article entity = modelMapper.map(articleDTO, Article.class);
      Article storedArticle = articleRepository.save(entity);
      returnValue.add(modelMapper.map(storedArticle, ArticleDTO.class));
    }
  }
}
