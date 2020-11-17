package com.mondiamedia.app.service;

import com.mondiamedia.app.domainmodel.security.Token;
import com.mondiamedia.app.domainmodel.security.TokenRepository;
import com.mondiamedia.app.service.api.SecurityService;
import com.mondiamedia.app.service.security.TokenDTO;
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
    ModelMapper mapper = new ModelMapper();

    List<Token> tokenList = tokenRepository.findByIdOrderByIdDesc(0L);
    if (tokenList.isEmpty()) return new TokenDTO();
    return mapper.map(tokenList.get(0), TokenDTO.class);
  }

  @Override
  public TokenDTO saveToken(TokenDTO tokenDTO) {
    ModelMapper mapper = new ModelMapper();

    Token token = mapper.map(tokenDTO, Token.class);
    Token savedToken = tokenRepository.save(token);
    return mapper.map(savedToken, TokenDTO.class);
  }
}
