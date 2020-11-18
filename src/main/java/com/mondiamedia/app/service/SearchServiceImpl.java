package com.mondiamedia.app.service;

import com.mondiamedia.app.config.AppProperties;
import com.mondiamedia.app.security.SecurityConstants;
import com.mondiamedia.app.security.TokenInitializer;
import com.mondiamedia.app.service.article.ArticleDTO;
import com.mondiamedia.app.service.content.SearchService;
import com.mondiamedia.app.service.security.TokenDTO;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
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
  private final RestTemplate restTemplate;
  private final AppProperties appProperties;
  private final TokenInitializer tokenInitializer;

  public SearchServiceImpl(
      RestTemplate restTemplate, AppProperties appProperties, TokenInitializer tokenInitializer) {
    this.restTemplate = restTemplate;
    this.appProperties = appProperties;
    this.tokenInitializer = tokenInitializer;
  }

  public List<ArticleDTO> searchArticle(String query, String offset) {
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
    // Must check null pointer exception
    String returnedJson = response.getBody();
    return parseArticlesResponse(returnedJson);
  }

  private HttpEntity<HttpHeaders> createRequestEntity() {
    TokenDTO token = tokenInitializer.getTokenDTO();
    HttpHeaders headers = new HttpHeaders();
    headers.add(
        SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token.getAccessToken());
    headers.add(SecurityConstants.HEADER_PARAMETER, appProperties.getTokenSecret());
    return new HttpEntity<>(headers);
  }

  private List<ArticleDTO> parseArticlesResponse(String json) {
    JsonReader jsonReader = Json.createReader(new StringReader(json));
    JsonArray jsonArray = jsonReader.readArray();
    return jsonArray.stream()
        .map(jsonValue -> (JsonObject) jsonValue)
        .map(
            jsonObject ->
                new ArticleDTO(
                    String.valueOf(jsonObject.getInt("id")),
                    jsonObject.getString("name"),
                    jsonObject.getString("artistName")))
        .collect(Collectors.toList());
  }
}
