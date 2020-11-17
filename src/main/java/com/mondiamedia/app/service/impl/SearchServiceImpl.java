package com.mondiamedia.app.service.impl;

import com.mondiamedia.app.security.AppProperties;
import com.mondiamedia.app.security.SecurityConstants;
import com.mondiamedia.app.security.TokenInitializer;
import com.mondiamedia.app.service.SearchService;
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
  @Autowired private AppProperties appProperties;
  @Autowired private TokenInitializer tokenInitializer;

  public List<ArticleDTO> searchArticle(String query, String offset) {
    String returnedJson = "";

    String url =
        appProperties.getMondiaSearchApiUrl()
            + "?q="
            + query
            + "&offset="
            + offset
            + "&limit="
            + appProperties.getPageLimit();

    ResponseEntity<String> response = null;
    try {
      response = restTemplate.exchange(url, HttpMethod.GET, createRequestEntity(), String.class);
    } catch (HttpClientErrorException e) {
      if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
        tokenInitializer.refresh();
        response = restTemplate.exchange(url, HttpMethod.GET, createRequestEntity(), String.class);
      } else e.printStackTrace();
    }

    returnedJson = response.getBody();

    return parseArticlesResponse(returnedJson);
  }

  private HttpEntity createRequestEntity() {
    TokenDTO token = tokenInitializer.getTokenDTO();

    HttpHeaders headers = new HttpHeaders();
    headers.add(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token.getAccessToken());
    headers.add(SecurityConstants.HEADER_PARAMETER, SecurityConstants.getTokenSecret());
    return new HttpEntity(headers);
  }

  private List<ArticleDTO> parseArticlesResponse(String json) {
    List<ArticleDTO> articles = new ArrayList<>();

    JsonReader jsonReader = Json.createReader(new StringReader(json));
    JsonArray jsonArray = jsonReader.readArray();
    ListIterator l = jsonArray.listIterator();
    while (l.hasNext()) {
      JsonObject jsonObject = (JsonObject) l.next();
      ArticleDTO articleDTO =
          new ArticleDTO(
              String.valueOf(jsonObject.getInt("id")),
              jsonObject.getString("name"),
              jsonObject.getString("artistName"));
      articles.add(articleDTO);
    }
    return articles;
  }
}
