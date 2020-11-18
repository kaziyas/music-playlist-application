package com.mondiamedia.app.service;

import com.mondiamedia.app.domainmodel.article.Article;
import com.mondiamedia.app.domainmodel.playlist.Playlist;
import com.mondiamedia.app.domainmodel.playlist.PlaylistRepository;
import com.mondiamedia.app.service.article.ArticleDTO;
import com.mondiamedia.app.service.playlist.PlaylistDTO;
import com.mondiamedia.app.service.playlist.PlaylistService;
import com.mondiamedia.app.service.playlist.PlaylistServiceException;
import com.mondiamedia.app.service.shared.IdGeneratorUtil;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
@Service
public class PlaylistServiceImpl implements PlaylistService {
  private final PlaylistRepository playlistRepository;

  public PlaylistServiceImpl(PlaylistRepository playlistRepository) {
    this.playlistRepository = playlistRepository;
  }

  @Override
  public PlaylistDTO createPlaylist(PlaylistDTO playlistDTO) {
    if (playlistRepository.findByTitle(playlistDTO.getTitle()) != null) {
      throw new PlaylistServiceException("playlist already exist");
    }

    ModelMapper modelMapper = new ModelMapper();
    Playlist playlist = modelMapper.map(playlistDTO, Playlist.class);
    String publicPlaylistId = IdGeneratorUtil.generatePlaylistId(30);
    playlist.setPlaylistId(publicPlaylistId);
    Playlist storedPlaylistDetail = playlistRepository.save(playlist);
    return modelMapper.map(storedPlaylistDetail, PlaylistDTO.class);
  }

  @Override
  public PlaylistDTO getPlaylistByPlaylistId(String id) {
    Playlist playlist = playlistRepository.findByPlaylistId(id);
    if (playlist == null) {
      throw new PlaylistServiceException("Playlist with ID: " + id + " not found");
    }

    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(playlist, PlaylistDTO.class);
  }

  @Override
  public PlaylistDTO updatePlayList(String playlistId, PlaylistDTO playlistDTO) {
    Playlist playlist = playlistRepository.findByPlaylistId(playlistId);
    if (playlist == null) {
      throw new PlaylistServiceException("Playlist with ID: " + playlistId + " not found");
    }

    List<Article> articleEntities;
    ModelMapper modelMapper = new ModelMapper();
    final List<ArticleDTO> articles = playlistDTO.getArticles();
    if (articles != null) { // add or remove article
      IntStream.range(0, articles.size())
          .forEach(
              i -> {
                ArticleDTO article = articles.get(i);
                articles.set(i, article);
              });
      articleEntities =
          articles.stream()
              .map(articleDTO -> modelMapper.map(articleDTO, Article.class))
              .collect(Collectors.toList());
    } else { // update playlistDTO
      articleEntities = playlist.getArticles();
    }

    playlist.setTitle(playlistDTO.getTitle());
    playlist.setDescription(playlistDTO.getDescription());
    playlist.setArticles(articleEntities);
    Playlist updatePlayList = playlistRepository.save(playlist);
    return modelMapper.map(updatePlayList, PlaylistDTO.class);
  }

  @Override
  public void deletePlaylist(String id) {
    Playlist playlist = playlistRepository.findByPlaylistId(id);
    if (playlist == null) {
      throw new PlaylistServiceException("Playlist with ID: " + id + " not found");
    }

    playlistRepository.delete(playlist);
  }

  @Override
  public List<ArticleDTO> getArticles(String playlistId) {
    Playlist playlist = playlistRepository.findByPlaylistId(playlistId);
    if (playlist == null) {
      throw new PlaylistServiceException("Playlist with ID: " + playlistId + " not found");
    }

    ModelMapper modelMapper = new ModelMapper();
    final List<Article> articles = playlist.getArticles();
    return articles.stream()
        .map(article -> modelMapper.map(article, ArticleDTO.class))
        .collect(Collectors.toList());
  }
}
