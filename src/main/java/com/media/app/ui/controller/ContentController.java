package com.media.app.ui.controller;

import com.media.app.service.article.ArticleDTO;
import com.media.app.service.article.ArticleService;
import com.media.app.service.content.SearchService;
import com.media.app.ui.model.request.ArticleRequestModel;
import com.media.app.ui.model.response.ArticleRest;
import io.swagger.annotations.ApiOperation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
@RestController
@RequestMapping("/V1/api/content")
public class ContentController {
  private final SearchService searchService;
  private final ArticleService articleService;

  public ContentController(SearchService searchService, ArticleService articleService) {
    this.searchService = searchService;
    this.articleService = articleService;
  }

  @ApiOperation(
      value = "The Search Media Contents Web Service Endpoint",
      notes = "${contentController.searchArticle.ApiOperation.Notes}")
  @PostMapping(
      path = "/search",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public List<ArticleRest> searchArticle(
      @RequestBody ArticleRequestModel articleRequestModel,
      @RequestParam(value = "offset", defaultValue = "0") String offset) {

    List<ArticleDTO> articles =
        searchService.searchArticle(createQueryString(articleRequestModel), offset);
    articleService.saveSearchedArticles(articles);

    return articleService.getArticles(articles);
  }

  private String createQueryString(ArticleRequestModel articleRequestModel) {
    StringJoiner joiner = new StringJoiner(" , ");
    joiner.add(articleRequestModel.getArtistName());
    joiner.add(articleRequestModel.getTrackName());
    return joiner.toString();
  }
}
