package com.mondiamedia.app.service.security;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.30
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class TokenDTO {
  private long id;
  private String accessToken;
  private String tokenType;
  private String expiresIn;

  public TokenDTO(String accessToken, String tokenType, String expiresIn) {
    this.accessToken = accessToken;
    this.tokenType = tokenType;
    this.expiresIn = expiresIn;
  }
}
