package com.mondiamedia.app.service;

import com.mondiamedia.app.io.entity.TokenEntity;
import com.mondiamedia.app.service.shared.TokenDTO;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
public interface SecurityService {
  TokenDTO getToken();
  TokenDTO saveToken(TokenDTO tokenDTO);
}
