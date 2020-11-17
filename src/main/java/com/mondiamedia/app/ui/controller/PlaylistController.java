package com.mondiamedia.app.ui.controller;

import com.mondiamedia.app.service.playlist.PlaylistServiceException;
import com.mondiamedia.app.service.article.ArticleService;
import com.mondiamedia.app.service.playlist.PlaylistService;
import com.mondiamedia.app.service.article.ArticleDTO;
import com.mondiamedia.app.service.playlist.PlaylistDTO;
import com.mondiamedia.app.ui.model.request.PlaylistRequestModel;
import com.mondiamedia.app.ui.model.response.ArticleRest;
import com.mondiamedia.app.ui.model.response.ErrorMessages;
import com.mondiamedia.app.ui.model.response.OperationStatusModel;
import com.mondiamedia.app.ui.model.response.PlaylistRest;
import com.mondiamedia.app.ui.model.response.RequestOperationStatus;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
@RestController
@RequestMapping("/playlists") // http://localhost:9100/mondia-app/playlists
public class PlaylistController {
  private final PlaylistService playlistService;
  private final ArticleService articleService;

  public PlaylistController(PlaylistService playlistService, ArticleService articleService) {
    this.playlistService = playlistService;
    this.articleService = articleService;
  }

  @GetMapping(
      path = "/{id}",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public PlaylistRest getPlaylist(@PathVariable String id) {
    ModelMapper modelMapper = new ModelMapper();

    final PlaylistDTO playlistDTO = playlistService.getPlaylistByPlaylistId(id);
    return modelMapper.map(playlistDTO, PlaylistRest.class);
  }

  @PostMapping(
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public PlaylistRest createPlaylist(@RequestBody PlaylistRequestModel playlistDetails)
      throws PlaylistServiceException {

    if (playlistDetails.getTitle().isEmpty()) {
      throw new PlaylistServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
    }

    ModelMapper modelMapper = new ModelMapper();
    PlaylistDTO playlistDTO = modelMapper.map(playlistDetails, PlaylistDTO.class);
    PlaylistDTO createdPlaylist = playlistService.createPlaylist(playlistDTO);
    return modelMapper.map(createdPlaylist, PlaylistRest.class);
  }

  @PutMapping(
      path = "/{id}",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public PlaylistRest updatePlaylist(
      @PathVariable String id, @RequestBody PlaylistRequestModel playlistDetails)
      throws PlaylistServiceException {
    ModelMapper modelMapper = new ModelMapper();

    PlaylistDTO playlistDTO = modelMapper.map(playlistDetails, PlaylistDTO.class);
    playlistDTO = playlistService.updatePlayList(id, playlistDTO);
    return modelMapper.map(playlistDTO, PlaylistRest.class);
  }

  @DeleteMapping(
      path = "/{id}",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public OperationStatusModel deletePlaylist(@PathVariable String id) {
    playlistService.deletePlaylist(id);

    OperationStatusModel returnValue = new OperationStatusModel();
    returnValue.setOperationName(RequestOperationName.DELETE.name());
    returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
    return returnValue;
  }

  @PutMapping(
      path = "/{id}/articles",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public PlaylistRest addArticle(@PathVariable String id, @RequestParam String articleId) {
    ModelMapper modelMapper = new ModelMapper();

    PlaylistDTO playlistDTO = playlistService.getPlaylistByPlaylistId(id);
    playlistDTO.getArticles().add(articleService.getArticleByArticleId(articleId));
    playlistDTO = playlistService.updatePlayList(id, playlistDTO);
    return modelMapper.map(playlistDTO, PlaylistRest.class);
  }

  @PutMapping(
      path = "/{id}/articles/{articleId}",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public PlaylistRest removeArticle(@PathVariable String id, @PathVariable String articleId) {
    ModelMapper modelMapper = new ModelMapper();

    PlaylistDTO playlistDTO = playlistService.getPlaylistByPlaylistId(id);
    List<ArticleDTO> articleDTO =
        playlistDTO.getArticles().stream()
            .filter(article -> article.getArticleId().equals(articleId))
            .collect(Collectors.toList());
    playlistDTO.getArticles().removeAll(articleDTO);
    playlistDTO = playlistService.updatePlayList(id, playlistDTO);
    return modelMapper.map(playlistDTO, PlaylistRest.class);
  }

  @GetMapping(
      path = "/{id}/articles",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public List<ArticleRest> getArticles(@PathVariable String id) {
    List<ArticleRest> returnValue = new ArrayList<>();

    List<ArticleDTO> articles = articleService.getArticles(id);
    if (articles != null && !articles.isEmpty()) {
      Type listType = new TypeToken<List<ArticleRest>>() {}.getType();
      returnValue = new ModelMapper().map(articles, listType);
    }

    return returnValue;
  }
}
