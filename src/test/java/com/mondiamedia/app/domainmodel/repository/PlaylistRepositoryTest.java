package com.mondiamedia.app.domainmodel.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mondiamedia.app.domainmodel.article.ArticleEntity;
import com.mondiamedia.app.domainmodel.playlist.PlaylistEntity;
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
    List<PlaylistEntity> playlists = playlistRepository.findAllByTitle(title);
    assertNotNull(playlists);
    assertTrue(playlists.size() == 2);

    PlaylistEntity playlist = playlists.get(0);
    assertTrue(playlist.getTitle().equals(title));
  }

  private void createRecorods() {
    PlaylistEntity playlistEntity = new PlaylistEntity();
    playlistEntity.setPlaylistId(PLAYLIST_ID);
    playlistEntity.setTitle("My best pop songs");
    playlistEntity.setDescription("The best 90 decade pop songs");
    playlistEntity.setEmail("yaser.kazerooni@gmail.com");

    ArticleEntity articleEntity = new ArticleEntity();
    articleEntity.setArticleId("23422");
    articleEntity.setArtistName("Sandra");
    articleEntity.setTrackName("Hiroshima");

    List<ArticleEntity> articles = new ArrayList<>();
    articles.add(articleEntity);
    playlistEntity.setArticles(articles);

    playlistRepository.save(playlistEntity);

    PlaylistEntity playlistEntity2 = new PlaylistEntity();
    playlistEntity2.setPlaylistId("5656456");
    playlistEntity2.setTitle("My best pop songs");
    playlistEntity2.setDescription("The best 90 decade roc songs");
    playlistEntity2.setEmail("yaser.kazerooni@gmail.com");

    ArticleEntity articleEntity2 = new ArticleEntity();
    articleEntity2.setArticleId("455645");
    articleEntity2.setArtistName("Michael");
    articleEntity2.setTrackName("Give in to me");

    List<ArticleEntity> articles2 = new ArrayList<>();
    articles2.add(articleEntity2);
    playlistEntity.setArticles(articles2);

    playlistRepository.save(playlistEntity2);
  }
}
