package com.mondiamedia.app.service.impl;

import com.mondiamedia.app.security.SecurityConstants;
import com.mondiamedia.app.service.SearchService;
import com.mondiamedia.app.service.shared.ArticleDTO;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {
  @Autowired private RestTemplate restTemplate;

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  public List<ArticleDTO> searchArticle(String query) {
    String json = "";
    try {
      // request url
      String url =
          "https://staging-gateway.mondiamedia.com/v1/api/content/search?q=" + query
              + "&offset=0&limit=10";

      String token = "C7596e01e-799c-4ac5-9cd3-955c35f7246c";

      // create headers
      HttpHeaders headers = new HttpHeaders();
      headers.add(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
      headers.add(SecurityConstants.HEADER_PARAMETER, SecurityConstants.getTokenSecret());

      // create request
      HttpEntity request = new HttpEntity(headers);

      // make a request
      ResponseEntity<String> response =
          restTemplate.exchange(url, HttpMethod.GET, request, String.class);

      // get JSON response
      json = response.getBody();

    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return parseJsonString(json);
  }

  private List<ArticleDTO> parseJsonString(String json) {
    List<ArticleDTO> articles = new ArrayList<>();
    JsonReader jsonReader = Json.createReader(new StringReader(json));
    JsonArray jsonArray = jsonReader.readArray();
    ListIterator l = jsonArray.listIterator();
    while (l.hasNext()) {
      JsonObject j = (JsonObject) l.next();
      ArticleDTO articleDTO =
          new ArticleDTO(
              String.valueOf(j.getInt("id")),
              j.getString("name"),
              j.getString("artistName"));
      articles.add(articleDTO);
    }
    return articles;
  }
}
