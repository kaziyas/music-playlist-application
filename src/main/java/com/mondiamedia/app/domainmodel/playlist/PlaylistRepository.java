package com.mondiamedia.app.domainmodel.playlist;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
@Repository
public interface PlaylistRepository extends CrudRepository<PlaylistEntity, Long> {
  PlaylistEntity findByTitle(String title);

  PlaylistEntity findByPlaylistId(String playlistId);

  List<PlaylistEntity> findAllByTitle(String title);
}
