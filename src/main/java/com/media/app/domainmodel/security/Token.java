package com.media.app.domainmodel.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.30
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity(name = "TOKEN")
public class Token {
  @Id private Long id;

  @Column(nullable = false, length = 50)
  private String accessToken;

  @Column(nullable = false, length = 10)
  private String tokenType;

  @Column(nullable = false)
  private String expiresIn;
}
