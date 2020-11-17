package com.mondiamedia.app.service.playlist;

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
}
