package com.mondiamedia.app.service.impl;

import com.mondiamedia.app.security.AppProperties;
import com.mondiamedia.app.security.SecurityConstants;
import com.mondiamedia.app.service.SearchService;
import com.mondiamedia.app.service.SecurityService;
import com.mondiamedia.app.service.shared.ArticleDTO;
import com.mondiamedia.app.service.shared.TokenDTO;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {
  @Autowired private RestTemplate restTemplate;

  @Autowired private SecurityService securityService;

  @Autowired private AppProperties appProperties;

  public List<ArticleDTO> searchArticle(String query, String offset) {
    String returnedJson = "";

    try {
      String url =
          appProperties.getMondiaSearchApiUrl()
              + "?q="
              + query
              + "&offset="
              + offset
              + "&limit="
              + appProperties.getPageLimit();

      String accessToken = securityService.getToken().getAccessToken();

      if (accessToken == null) accessToken = fetchValidToken().getAccessToken();

      ResponseEntity<String> response = null;
      HttpEntity request = getHttpEntity(accessToken);

      try {
        response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
      } catch (HttpClientErrorException e) {
        if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
          accessToken = fetchValidToken().getAccessToken();
          request = getHttpEntity(accessToken);
          response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        } else e.printStackTrace();
      }

      returnedJson = response.getBody();

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return parseArticlesResponse(returnedJson);
  }

  private HttpEntity getHttpEntity(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + accessToken);
    headers.add(SecurityConstants.HEADER_PARAMETER, SecurityConstants.getTokenSecret());
    return new HttpEntity(headers);
  }

  private TokenDTO fetchValidToken() {
    TokenDTO savedTokenDTO = new TokenDTO();
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.add(SecurityConstants.HEADER_PARAMETER, SecurityConstants.getTokenSecret());

      HttpEntity request = new HttpEntity(headers);
      ResponseEntity<String> response =
          restTemplate.exchange(
              appProperties.getMondiaTokenApiUrl(), HttpMethod.POST, request, String.class);

      TokenDTO tokenDTO = parseTokenResponse(response.getBody());
      savedTokenDTO = securityService.saveToken(tokenDTO);

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return savedTokenDTO;
  }

  private TokenDTO parseTokenResponse(String json) {
    JsonReader jsonReader = Json.createReader(new StringReader(json));
    JsonObject j = jsonReader.readObject();
    TokenDTO tokenDTO =
        new TokenDTO(
            j.getString("accessToken"), j.getString("tokenType"), j.getString("expiresIn"));
    return tokenDTO;
  }

  private List<ArticleDTO> parseArticlesResponse(String json) {
    List<ArticleDTO> articles = new ArrayList<>();

    JsonReader jsonReader = Json.createReader(new StringReader(json));
    JsonArray jsonArray = jsonReader.readArray();
    ListIterator l = jsonArray.listIterator();
    while (l.hasNext()) {
      JsonObject j = (JsonObject) l.next();
      ArticleDTO articleDTO =
          new ArticleDTO(
              String.valueOf(j.getInt("id")), j.getString("name"), j.getString("artistName"));
      articles.add(articleDTO);
    }
    return articles;
  }
}
