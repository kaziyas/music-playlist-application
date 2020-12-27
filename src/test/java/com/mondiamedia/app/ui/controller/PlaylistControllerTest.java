package com.mondiamedia.app.ui.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.mondiamedia.app.service.article.ArticleDTO;
import com.mondiamedia.app.service.playlist.PlaylistDTO;
import com.mondiamedia.app.service.playlist.PlaylistService;
import com.mondiamedia.app.service.playlist.PlaylistServiceException;
import com.mondiamedia.app.ui.model.request.PlaylistRequestModel;
import com.mondiamedia.app.ui.model.response.PlaylistRest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.30
 * @since 1.0
 */
public class PlaylistControllerTest {
  final String PLAYLIST_ID = "5AK81zVunje384aLOyjEywqFR862xl";

  @InjectMocks PlaylistController playlistController;
  @Mock PlaylistService playlistService;

  PlaylistDTO playlistDTO;
  PlaylistRequestModel playlistDetails;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);

    playlistDTO = new PlaylistDTO();
    playlistDTO.setTitle("My Playlist");
    playlistDTO.setEmail("yaser.kazerooni@gmail.com");
    playlistDTO.setPlayListId(PLAYLIST_ID);
    playlistDTO.setDescription("The best pop musics");
    playlistDTO.setArticles(getArticlesDTO());

    playlistDetails = new PlaylistRequestModel();
    playlistDetails.setTitle("My Playlist");
    playlistDetails.setEmail("yaser.kazerooni@gmail.com");
    playlistDetails.setDescription("The best pop musics");
  }

  @Test
  final void testGetPlaylist() {
    when(playlistService.getPlaylistByPlaylistId(anyString())).thenReturn(playlistDTO);

    PlaylistRest playlistRest = playlistController.getPlaylist(PLAYLIST_ID);
    assertNotNull(playlistRest);
    assertEquals(PLAYLIST_ID, playlistDTO.getPlayListId());
    assertEquals(playlistDTO.getTitle(), playlistRest.getTitle());
    assertEquals(playlistDTO.getEmail(), playlistRest.getEmail());
    assertEquals(playlistDTO.getArticles().size(), playlistRest.getArticles().size());
  }

  @Test
  final void testCreatePlaylistServiceException() {
    when(playlistService.getPlaylistByPlaylistId(anyString())).thenReturn(playlistDTO);

    playlistDetails.setTitle("My Playlist");
    assertThrows(
        PlaylistServiceException.class, () -> playlistController.createPlaylist(playlistDetails));
  }

  @Test
  final void testCreatePlaylist() {
    when(playlistService.getPlaylistByPlaylistId(anyString())).thenReturn(playlistDTO);
    when(playlistService.createPlaylist(any(PlaylistDTO.class))).thenReturn(playlistDTO);

    PlaylistRest playlistRest = playlistController.createPlaylist(playlistDetails);
    assertNotNull(playlistRest);
    assertEquals(PLAYLIST_ID, playlistRest.getPlayListId());
    assertEquals(playlistDetails.getTitle(), playlistRest.getTitle());
    assertEquals(playlistDetails.getEmail(), playlistRest.getEmail());
  }

  private List<ArticleDTO> getArticlesDTO() {
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setArtistName("Sandra");
    articleDTO.setTrackName("Hiroshima");

    ArticleDTO articleDTO1 = new ArticleDTO();
    articleDTO1.setArtistName("Sandra");
    articleDTO1.setTrackName("Hiroshima");

    List<ArticleDTO> articles = new ArrayList<>();
    articles.add(articleDTO);
    articles.add(articleDTO1);

    return articles;
  }
}
