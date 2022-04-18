package com.media.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.media.app.service.article.ArticleDTO;
import com.media.app.service.playlist.PlaylistServiceExceptionApp;
import com.media.app.domainmodel.article.Article;
import com.media.app.domainmodel.playlist.Playlist;
import com.media.app.domainmodel.playlist.PlaylistRepository;
import com.media.app.service.playlist.PlaylistDTO;
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
  Playlist playlist;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);

    playlist = new Playlist();
    playlist.setId(1L);
    playlist.setPlaylistId(PLAYLIST_ID);
    playlist.setTitle("My best pop songs");
    playlist.setDescription("The best 90 decade pop songs");
    playlist.setEmail("yaser.kazerooni@gmail.com");
    playlist.setArticles(getArticlesEntity());
  }

  @Test
  final void testGetPlaylist() {
    when(playlistRepository.findByPlaylistId(anyString())).thenReturn(playlist);

    PlaylistDTO playlistDTO = playlistService.getPlaylistByPlaylistId(PLAYLIST_ID);
    assertNotNull(playlistDTO);
    assertEquals("My best pop songs", playlistDTO.getTitle());
  }

  @Test
  final void testGetPlaylist_PlaylistServiceException() {
    when(playlistRepository.findByPlaylistId(anyString())).thenReturn(null);

    assertThrows(
        PlaylistServiceExceptionApp.class,
        () -> playlistService.getPlaylistByPlaylistId(PLAYLIST_ID));
  }

  @Test
  final void testCreatePlaylist_CreatePlaylistServiceException() {
    when(playlistRepository.findByTitle(anyString())).thenReturn(playlist);

    PlaylistDTO playlistDTO = getPlaylistDTO();
    assertThrows(
        PlaylistServiceExceptionApp.class,
        () -> playlistService.createPlaylist(playlistDTO));
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
    when(playlistRepository.save(any(Playlist.class))).thenReturn(playlist);

    PlaylistDTO playlistDTO = getPlaylistDTO();
    PlaylistDTO storedPlaylistDetails = playlistService.createPlaylist(playlistDTO);
    assertNotNull(storedPlaylistDetails);
    assertEquals(playlist.getTitle(), storedPlaylistDetails.getTitle());
    assertEquals(playlist.getDescription(), storedPlaylistDetails.getDescription());
    assertNotNull(storedPlaylistDetails.getPlayListId());
    assertEquals(storedPlaylistDetails.getArticles().size(), playlist.getArticles().size());
    verify(playlistRepository, times(1)).save(any(Playlist.class));
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

  private List<Article> getArticlesEntity() {
    Type listType = new TypeToken<List<Article>>() {}.getType();
    List<ArticleDTO> articles = getArticlesDTO();
    return new ModelMapper().map(articles, listType);
  }
}
