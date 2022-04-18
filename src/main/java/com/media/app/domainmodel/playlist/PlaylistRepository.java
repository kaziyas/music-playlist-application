package com.media.app.domainmodel.playlist;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
@Repository
public interface PlaylistRepository extends CrudRepository<Playlist, Long> {
  Playlist findByTitle(String title);

  Playlist findByPlaylistId(String playlistId);
}
