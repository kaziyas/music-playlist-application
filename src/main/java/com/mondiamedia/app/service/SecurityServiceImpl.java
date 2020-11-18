package com.mondiamedia.app.service;

import com.mondiamedia.app.domainmodel.security.Token;
import com.mondiamedia.app.domainmodel.security.TokenRepository;
import com.mondiamedia.app.service.security.SecurityService;
import com.mondiamedia.app.service.security.TokenDTO;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.30
 * @since 1.0
 */
@Service
public class SecurityServiceImpl implements SecurityService {
  private final TokenRepository tokenRepository;

  public SecurityServiceImpl(TokenRepository tokenRepository) {
    this.tokenRepository = tokenRepository;
  }

  @Override
  public TokenDTO getToken() {
    List<Token> tokenList = tokenRepository.findByIdOrderByIdDesc(0L);
    if (tokenList.isEmpty()) return new TokenDTO();

    ModelMapper mapper = new ModelMapper();
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
