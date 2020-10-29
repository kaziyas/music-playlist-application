package com.mondiamedia.app.ui.model.response;

import java.util.List;
import lombok.Data;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
@Data
public class PlaylistRest {
  private String playListId;
  private String email;
  private String title;
  private String description;
  private List<ArticleRest> articles;
}
