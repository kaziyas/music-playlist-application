package com.mondiamedia.app.service.article;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class ArticleDTO {
  private long id;
  private String articleId;
  private String trackName;
  private String artistName;

  public ArticleDTO(String articleId, String trackName, String artistName) {
    this.articleId = articleId;
    this.trackName = trackName;
    this.artistName = artistName;
  }
}
