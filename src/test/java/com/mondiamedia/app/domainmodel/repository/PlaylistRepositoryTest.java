package com.mondiamedia.app.domainmodel.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mondiamedia.app.domainmodel.article.Article;
import com.mondiamedia.app.domainmodel.playlist.Playlist;
import com.mondiamedia.app.domainmodel.playlist.PlaylistRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.30
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PlaylistRepositoryTest {
  static boolean recordsCreated = false;
  final String PLAYLIST_ID = "5AK81zVunje384aLOyjEywqFR862xl";
  @Autowired PlaylistRepository playlistRepository;

  @BeforeEach
  void setUp() throws Exception {

    if (!recordsCreated) createRecorods();
  }

  @Test
  final void testFindPlaylistByTitle() {
    String title = "My best pop songs";
    List<Playlist> playlists = playlistRepository.findAllByTitle(title);
    assertNotNull(playlists);
    assertTrue(playlists.size() == 2);

    Playlist playlist = playlists.get(0);
    assertTrue(playlist.getTitle().equals(title));
  }

  private void createRecorods() {
    Playlist playlist = new Playlist();
    playlist.setPlaylistId(PLAYLIST_ID);
    playlist.setTitle("My best pop songs");
    playlist.setDescription("The best 90 decade pop songs");
    playlist.setEmail("yaser.kazerooni@gmail.com");

    Article article = new Article();
    article.setArticleId("23422");
    article.setArtistName("Sandra");
    article.setTrackName("Hiroshima");

    List<Article> articles = new ArrayList<>();
    articles.add(article);
    playlist.setArticles(articles);

    playlistRepository.save(playlist);

    Playlist playlist2 = new Playlist();
    playlist2.setPlaylistId("5656456");
    playlist2.setTitle("My best pop songs");
    playlist2.setDescription("The best 90 decade roc songs");
    playlist2.setEmail("yaser.kazerooni@gmail.com");

    Article article2 = new Article();
    article2.setArticleId("455645");
    article2.setArtistName("Michael");
    article2.setTrackName("Give in to me");

    List<Article> articles2 = new ArrayList<>();
    articles2.add(article2);
    playlist.setArticles(articles2);

    playlistRepository.save(playlist2);
  }
}
