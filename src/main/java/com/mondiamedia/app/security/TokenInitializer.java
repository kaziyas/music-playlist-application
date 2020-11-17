package com.mondiamedia.app.security;

import com.mondiamedia.app.service.SecurityService;
import com.mondiamedia.app.service.shared.TokenDTO;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.11.16
 * @since 1.0
 */
@Component
public class TokenInitializer {
  @Autowired private RestTemplate restTemplate;
  @Autowired private AppProperties appProperties;
  @Autowired private SecurityService securityService;

  private TokenDTO tokenDTO;

  private void init() {
    String token = securityService.getToken().getAccessToken();
    if (token == null) token = fetchToken();
    this.tokenDTO = saveToken(token);
  }

  private void tokenRefresh() {
    String token = fetchToken();
    this.tokenDTO = saveToken(token);
  }

  private String fetchToken() {
    HttpHeaders headers = new HttpHeaders();
    headers.add(SecurityConstants.HEADER_PARAMETER, SecurityConstants.getTokenSecret());
    HttpEntity request = new HttpEntity(headers);

    ResponseEntity<String> response =
        restTemplate.exchange(
            appProperties.getMondiaTokenApiUrl(), HttpMethod.POST, request, String.class);

    return response.getBody();
  }

  private TokenDTO saveToken(String jsonToken) {
    TokenDTO tokenDTO = TokenParser.parse(jsonToken);
    TokenDTO validTokenDTO = securityService.saveToken(tokenDTO);
    return validTokenDTO;
  }

  public TokenDTO getTokenDTO() {
    if (this.tokenDTO == null)
      init();

    return this.tokenDTO;
  }

  public void refresh() {
    this.tokenRefresh();
  }

  private static class TokenParser {
    private static TokenDTO parse(String jsonToken) {
      JsonReader jsonReader = Json.createReader(new StringReader(jsonToken));
      JsonObject jsonObject = jsonReader.readObject();
      TokenDTO tokenDTO =
          new TokenDTO(
              jsonObject.getString(SecurityConstants.ACCESS_TOKEN),
              jsonObject.getString(SecurityConstants.TOKEN_TYPE),
              jsonObject.getString(SecurityConstants.EXPIRES_IN));
      return tokenDTO;
    }
  }
}
