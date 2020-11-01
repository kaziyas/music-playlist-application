package com.mondiamedia.app.io.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
@Getter
@Setter
@Entity(name = "playlists")
public class PlaylistEntity implements Serializable {

  @Id @GeneratedValue private long id;

  @Column(nullable = false, length = 30)
  private String playlistId;

  @Column(nullable = false, length = 120)
  private String email;

  @Column(nullable = false, length = 50)
  private String title;

  @Column(length = 120)
  private String description;

  @OneToMany(mappedBy = "playlistDetails", cascade = CascadeType.ALL)
  private List<ArticleEntity> articles;
}
