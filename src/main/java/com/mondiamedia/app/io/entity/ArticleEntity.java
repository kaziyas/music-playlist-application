package com.mondiamedia.app.io.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
@Getter
@Setter
@Entity(name = "ARTICLE")
public class ArticleEntity implements Serializable {

  @Id
  @GeneratedValue
  private long id;

  @Column(nullable = false, length = 50)
  private String articleId;

  @Column(nullable = false, length = 50)
  private String trackName;

  @Column(nullable = false, length = 50)
  private String artistName;

}
