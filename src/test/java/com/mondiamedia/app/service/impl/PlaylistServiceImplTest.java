package com.mondiamedia.app.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mondiamedia.app.exceptions.PlaylistServiceException;
import com.mondiamedia.app.domainmodel.article.ArticleEntity;
import com.mondiamedia.app.domainmodel.playlist.PlaylistEntity;
import com.mondiamedia.app.domainmodel.playlist.PlaylistRepository;
import com.mondiamedia.app.service.PlaylistServiceImpl;
import com.mondiamedia.app.service.article.ArticleDTO;
import com.mondiamedia.app.service.playlist.PlaylistDTO;
import com.mondiamedia.app.service.shared.Utils;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.30
 * @since 1.0
 */
public class PlaylistServiceImplTest {

  final String PLAYLIST_ID = "5AK81zVunje384aLOyjEywqFR862xl";
  @InjectMocks PlaylistServiceImpl playlistService;
  @Mock PlaylistRepository playlistRepository;
  @Mock Utils utils;
  PlaylistEntity playlistEntity;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    playlistEntity = new PlaylistEntity();
    playlistEntity.setId(1l);
    playlistEntity.setPlaylistId(PLAYLIST_ID);
    playlistEntity.setTitle("My best pop songs");
    playlistEntity.setDescription("The best 90 decade pop songs");
    playlistEntity.setEmail("yaser.kazerooni@gmail.com");
    playlistEntity.setArticles(getArticlesEntity());
  }

  @Test
  final void testGetPlaylist() {
    when(playlistRepository.findByPlaylistId(anyString())).thenReturn(playlistEntity);

    PlaylistDTO playlistDTO = playlistService.getPlaylistByPlaylistId(PLAYLIST_ID);

    assertNotNull(playlistDTO);
    assertEquals("My best pop songs", playlistDTO.getTitle());
  }

  @Test
  final void testGetPlaylist_PlaylistServiceException() {
    when(playlistRepository.findByPlaylistId(anyString())).thenReturn(null);

    assertThrows(
        PlaylistServiceException.class,
        () -> {
          playlistService.getPlaylistByPlaylistId(PLAYLIST_ID);
        });
  }

  @Test
  final void testCreatePlaylist_CreatePlaylistServiceException() {
    when(playlistRepository.findByTitle(anyString())).thenReturn(playlistEntity);
    PlaylistDTO playlistDTO = getPlaylistDTO();

    assertThrows(
        PlaylistServiceException.class,
        () -> {
          playlistService.createPlaylist(playlistDTO);
        });
  }

  private PlaylistDTO getPlaylistDTO() {
    PlaylistDTO playlistDTO = new PlaylistDTO();
    playlistDTO.setTitle("My best pop songs");
    playlistDTO.setEmail("yaser.kazerooni@gmail.com");
    playlistDTO.setPlayListId(PLAYLIST_ID);
    playlistDTO.setDescription("The best pop musics");
    playlistDTO.setArticles(getArticlesDTO());
    return playlistDTO;
  }

  @Test
  final void testCreatePlaylist() {
    when(playlistRepository.findByTitle(anyString())).thenReturn(null);
    when(utils.generatePlaylistId(anyInt())).thenReturn(PLAYLIST_ID);
    when(playlistRepository.save(any(PlaylistEntity.class))).thenReturn(playlistEntity);

    PlaylistDTO playlistDTO = getPlaylistDTO();

    PlaylistDTO storedPlaylistDetails = playlistService.createPlaylist(playlistDTO);
    assertNotNull(storedPlaylistDetails);
    assertEquals(playlistEntity.getTitle(), storedPlaylistDetails.getTitle());
    assertEquals(playlistEntity.getDescription(), storedPlaylistDetails.getDescription());
    assertNotNull(storedPlaylistDetails.getPlayListId());
    assertEquals(storedPlaylistDetails.getArticles().size(), playlistEntity.getArticles().size());
    verify(playlistRepository, times(1)).save(any(PlaylistEntity.class));
  }

  private List<ArticleDTO> getArticlesDTO() {
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setArticleId("9434679");
    articleDTO.setArtistName("Sandra");
    articleDTO.setTrackName("Hiroshima");

    ArticleDTO articleDTO1 = new ArticleDTO();
    articleDTO1.setArtistName("11744375");
    articleDTO1.setArtistName("Sandra");
    articleDTO1.setTrackName("Hiroshima");

    List<ArticleDTO> articles = new ArrayList<>();
    articles.add(articleDTO);
    articles.add(articleDTO1);

    return articles;
  }

  private List<ArticleEntity> getArticlesEntity() {
    List<ArticleDTO> articles = getArticlesDTO();

    Type listType = new TypeToken<List<ArticleEntity>>() {}.getType();

    return new ModelMapper().map(articles, listType);
  }
}
