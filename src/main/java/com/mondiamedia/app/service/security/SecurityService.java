package com.mondiamedia.app.service.security;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
public interface SecurityService {
  TokenDTO getToken();

  TokenDTO saveToken(TokenDTO tokenDTO);
}
