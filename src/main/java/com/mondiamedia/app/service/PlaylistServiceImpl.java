package com.mondiamedia.app.service;

import com.mondiamedia.app.exceptions.PlaylistServiceException;
import com.mondiamedia.app.io.entity.ArticleEntity;
import com.mondiamedia.app.io.entity.PlaylistEntity;
import com.mondiamedia.app.io.repository.PlaylistRepository;
import com.mondiamedia.app.service.api.PlaylistService;
import com.mondiamedia.app.service.article.ArticleDTO;
import com.mondiamedia.app.service.playlist.PlaylistDTO;
import com.mondiamedia.app.service.shared.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
@Service
public class PlaylistServiceImpl implements PlaylistService {
  @Autowired Utils utils;

  @Autowired PlaylistRepository playlistRepository;

  @Override
  public PlaylistDTO createPlaylist(PlaylistDTO playlistDTO) {
    if (playlistRepository.findByTitle(playlistDTO.getTitle()) != null)
      throw new PlaylistServiceException("playlist already exist");

    ModelMapper modelMapper = new ModelMapper();
    PlaylistEntity playlistEntity = modelMapper.map(playlistDTO, PlaylistEntity.class);

    String publicPlaylistId = utils.generatePlaylistId(30);
    playlistEntity.setPlaylistId(publicPlaylistId);

    PlaylistEntity storedPlaylistDetail = playlistRepository.save(playlistEntity);
    return modelMapper.map(storedPlaylistDetail, PlaylistDTO.class);
  }

  @Override
  public PlaylistDTO getPlaylistByPlaylistId(String id) {
    PlaylistEntity playlistEntity = playlistRepository.findByPlaylistId(id);
    if (playlistEntity == null)
      throw new PlaylistServiceException("Playlist with ID: " + id + " not found");

    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(playlistEntity, PlaylistDTO.class);
  }

  @Override
  public PlaylistDTO updatePlayList(String playlistId, PlaylistDTO playlist) {
    ModelMapper modelMapper = new ModelMapper();
    List<ArticleEntity> articleEntities = new ArrayList<>();

    PlaylistEntity playlistEntity = playlistRepository.findByPlaylistId(playlistId);
    if (playlistEntity == null)
      throw new PlaylistServiceException("Playlist with ID: " + playlistId + " not found");

    final List<ArticleDTO> articles = playlist.getArticles();
    if (articles != null) { //add or remove article
      IntStream.range(0, articles.size()).forEach(i -> {
        ArticleDTO article = articles.get(i);
        articles.set(i, article);
      });

      articleEntities = articles.stream()
          .map(articleDTO -> modelMapper.map(articleDTO, ArticleEntity.class))
          .collect(Collectors.toList());
    } else {//update playlist
      articleEntities = playlistEntity.getArticles();
    }

    playlistEntity.setTitle(playlist.getTitle());
    playlistEntity.setDescription(playlist.getDescription());
    playlistEntity.setArticles(articleEntities);

    PlaylistEntity updatePlayListEntity = playlistRepository.save(playlistEntity);

    return modelMapper.map(updatePlayListEntity, PlaylistDTO.class);
  }

  @Override
  public void deletePlaylist(String id) {
    PlaylistEntity playlistEntity = playlistRepository.findByPlaylistId(id);

    if (playlistEntity == null)
      throw new PlaylistServiceException("Playlist with ID: " + id + " not found");

    playlistRepository.delete(playlistEntity);
  }
}
