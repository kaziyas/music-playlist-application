package com.media.app.service.playlist;

import com.media.app.service.article.ArticleDTO;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class PlaylistDTO {
  private long id;
  private String playListId;
  private String email;
  private String title;
  private String description;
  private List<ArticleDTO> articles;
}
