package com.mondiamedia.app.service.impl;

import com.mondiamedia.app.io.entity.ArticleEntity;
import com.mondiamedia.app.io.entity.PlaylistEntity;
import com.mondiamedia.app.io.repository.ArticleRepository;
import com.mondiamedia.app.io.repository.PlaylistRepository;
import com.mondiamedia.app.service.ArticleService;
import com.mondiamedia.app.service.shared.ArticleDTO;
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

  @Autowired
  private PlaylistRepository playlistRepository;

  @Autowired
  private ArticleRepository articleRepository;

  @Override
  public List<ArticleDTO> getArticles(String playlistId) {
    List<ArticleDTO> returnValue = new ArrayList<>();
    ModelMapper modelMapper = new ModelMapper();

    PlaylistEntity playlistEntity = playlistRepository.findByPlaylistId(playlistId);

    if (playlistEntity == null) return returnValue;

    final List<ArticleEntity> articles = articleRepository
        .findAllByPlaylistDetails(playlistEntity);
    for (ArticleEntity articleEntity : articles) {
      returnValue.add(modelMapper.map(articleEntity, ArticleDTO.class));
    }

    return returnValue;
  }
}
