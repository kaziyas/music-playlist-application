package com.mondiamedia.app.io.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity(name = "article")
public class ArticleEntity implements Serializable {

  @Id @GeneratedValue private long id;

  @Column(nullable = false, length = 50)
  private String articleId;

  @Column(nullable = false, length = 50)
  private String trackName;

  @Column(nullable = false, length = 50)
  private String artistName;

  @ManyToOne
  @JoinColumn(name = "playlists_id")
  private PlaylistEntity playlistDetails;
}
