package com.mondiamedia.app.domainmodel.playlist;

import com.mondiamedia.app.domainmodel.article.Article;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
@Getter
@Setter
@Entity(name = "PLAYLIST")
public class Playlist implements Serializable {

  @Id @GeneratedValue private long id;

  @Column(nullable = false, length = 30)
  private String playlistId;

  @Column(nullable = false, length = 120)
  private String email;

  @Column(nullable = false, length = 50)
  private String title;

  @Column(length = 120)
  private String description;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "PLAYLIST_ARTICLE",
      joinColumns = @JoinColumn(name = "PLAYLIST_ID"),
      inverseJoinColumns = @JoinColumn(name = "ARTICLE_ID"))
  private List<Article> articles;
}
