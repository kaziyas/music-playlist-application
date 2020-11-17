package com.mondiamedia.app.service;

import com.mondiamedia.app.exceptions.ArticleServiceException;
import com.mondiamedia.app.io.entity.ArticleEntity;
import com.mondiamedia.app.io.entity.PlaylistEntity;
import com.mondiamedia.app.io.repository.ArticleRepository;
import com.mondiamedia.app.io.repository.PlaylistRepository;
import com.mondiamedia.app.service.api.ArticleService;
import com.mondiamedia.app.service.article.ArticleDTO;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService {

  @Autowired private PlaylistRepository playlistRepository;

  @Autowired private ArticleRepository articleRepository;

  @Override
  public List<ArticleDTO> getArticles(String playlistId) {
    List<ArticleDTO> returnValue = new ArrayList<>();
    ModelMapper modelMapper = new ModelMapper();

    PlaylistEntity playlistEntity = playlistRepository.findByPlaylistId(playlistId);

    if (playlistEntity == null) return returnValue;

    final List<ArticleEntity> articles = playlistEntity.getArticles();
    for (ArticleEntity articleEntity : articles) {
      returnValue.add(modelMapper.map(articleEntity, ArticleDTO.class));
    }

    return returnValue;
  }

  @Override
  public ArticleDTO getArticleByArticleId(String articleId) {
    ArticleEntity articleEntity = articleRepository.findByArticleId(articleId);
    if (articleEntity == null)
      throw new ArticleServiceException("Article with ID: " + articleId + " not found");

    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(articleEntity, ArticleDTO.class);
  }

  @Override
  public List<ArticleDTO> saveSearchedArticles(List<ArticleDTO> articles) {
    List<ArticleDTO> returnValue = new ArrayList<>();
    ModelMapper modelMapper = new ModelMapper();

    for (ArticleDTO articleDTO : articles) {
      if (articleRepository.findByArticleId(articleDTO.getArticleId()) != null)
        continue; // duplicate article

      ArticleEntity entity = modelMapper.map(articleDTO, ArticleEntity.class);
      ArticleEntity storedArticle = articleRepository.save(entity);
      returnValue.add(modelMapper.map(storedArticle, ArticleDTO.class));
    }

    return returnValue;
  }
}
