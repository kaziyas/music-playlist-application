package com.mondiamedia.app.io.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
@Data
@Entity(name = "playlists")
public class PlaylistEntity implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, length = 30)
  private String playlistId;

  @Column(nullable = false, length = 50)
  private String title;

  @Column(length = 120)
  private String description;
}
