package com.mondiamedia.app.io.repository;

import com.mondiamedia.app.io.entity.ArticleEntity;
import com.mondiamedia.app.io.entity.PlaylistEntity;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> {
  List<ArticleEntity> findAllByPlaylistDetails(PlaylistEntity playlistEntity);
}
