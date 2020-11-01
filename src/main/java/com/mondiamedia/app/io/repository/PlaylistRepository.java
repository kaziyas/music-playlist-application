package com.mondiamedia.app.io.repository;

import com.mondiamedia.app.io.entity.PlaylistEntity;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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

  @Query(value="select * from playlists p where p.title = ?1",nativeQuery=true)
  List<PlaylistEntity> findPlaylistByTitle(String title);

/*  @Query("select playlist from PlaylistEntity playlist where playlist.playlistId =:playlistId")
  PlaylistEntity findPlaylistEntityByPlaylistId(@Param("playlistId") String playlistId);*/
}
