package com.mondiamedia.app.service.playlist;

import com.mondiamedia.app.service.article.ArticleDTO;
import java.util.List;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
public interface PlaylistService {
  PlaylistDTO getPlaylistByPlaylistId(String id);

  PlaylistDTO createPlaylist(PlaylistDTO playlistDTO);

  PlaylistDTO updatePlayList(String playlistId, PlaylistDTO playlistDTO);

  void deletePlaylist(String id);

  List<ArticleDTO> getArticles(String playlistId);
}
