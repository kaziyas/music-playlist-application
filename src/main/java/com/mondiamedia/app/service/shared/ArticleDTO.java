package com.mondiamedia.app.service.shared;

import lombok.Data;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
@Data
public class ArticleDTO {
  private long id;
  private String articleId;
  private String trackName;
  private String artistName;
  private String albumName;
  private PlaylistDTO playlistDetails;
}
