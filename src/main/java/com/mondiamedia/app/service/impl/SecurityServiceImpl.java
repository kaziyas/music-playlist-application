package com.mondiamedia.app.service.impl;

import com.mondiamedia.app.io.entity.TokenEntity;
import com.mondiamedia.app.io.repository.TokenRepository;
import com.mondiamedia.app.service.SecurityService;
import com.mondiamedia.app.service.shared.TokenDTO;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.30
 * @since 1.0
 */
@Service
public class SecurityServiceImpl implements SecurityService {
  @Autowired TokenRepository tokenRepository;

  @Override
  public TokenDTO getToken() {
    List<TokenEntity> tokenList =
        tokenRepository.findByIdOrderByIdDesc(0L);
    if (tokenList.isEmpty())
      return new TokenDTO();

    ModelMapper mapper = new ModelMapper();
    return mapper.map(tokenList.get(0), TokenDTO.class);
  }

  @Override
  public TokenDTO saveToken(TokenDTO tokenDTO) {
    ModelMapper mapper = new ModelMapper();
    TokenEntity tokenEntity = mapper.map(tokenDTO, TokenEntity.class);
    TokenEntity savedTokenEntity = tokenRepository.save(tokenEntity);
    return mapper.map(savedTokenEntity, TokenDTO.class);
  }

}
