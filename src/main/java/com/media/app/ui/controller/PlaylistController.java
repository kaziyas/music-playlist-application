package com.media.app.ui.controller;

import com.media.app.service.article.ArticleDTO;
import com.media.app.service.article.ArticleService;
import com.media.app.service.playlist.PlaylistDTO;
import com.media.app.service.playlist.PlaylistService;
import com.media.app.service.playlist.PlaylistServiceExceptionApp;
import com.media.app.ui.model.request.PlaylistRequestModel;
import com.media.app.ui.model.response.ArticleRest;
import com.media.app.ui.model.response.ErrorMessages;
import com.media.app.ui.model.response.OperationStatusModel;
import com.media.app.ui.model.response.PlaylistRest;
import com.media.app.ui.model.response.RequestOperationStatus;
import io.swagger.annotations.ApiOperation;
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
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
@RestController
@RequestMapping("/playlists") // http://localhost:9100/media-app/playlists
public class PlaylistController {
  private final PlaylistService playlistService;
  private final ArticleService articleService;

  public PlaylistController(PlaylistService playlistService, ArticleService articleService) {
    this.playlistService = playlistService;
    this.articleService = articleService;
  }

  @ApiOperation(
      value = "The Get Playlist Web Service Endpoint",
      notes = "${playlistController.getPlaylist.ApiOperation.Notes}")
  @GetMapping(
      path = "/{id}",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public PlaylistRest getPlaylist(@PathVariable String id) {
    final PlaylistDTO playlistDTO = playlistService.getPlaylistByPlaylistId(id);

    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(playlistDTO, PlaylistRest.class);
  }

  @ApiOperation(
      value = "The Create Playlist Web Service Endpoint",
      notes = "${playlistController.createPlaylist.ApiOperation.Notes}")
  @PostMapping(
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public PlaylistRest createPlaylist(@RequestBody PlaylistRequestModel playlistDetails)
      throws PlaylistServiceExceptionApp {

    if (playlistDetails.getTitle().isEmpty()) {
      throw new PlaylistServiceExceptionApp(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
    }

    ModelMapper modelMapper = new ModelMapper();
    PlaylistDTO playlistDTO = modelMapper.map(playlistDetails, PlaylistDTO.class);
    PlaylistDTO createdPlaylist = playlistService.createPlaylist(playlistDTO);
    return modelMapper.map(createdPlaylist, PlaylistRest.class);
  }

  @ApiOperation(
      value = "The Update Playlist Web Service Endpoint",
      notes = "${playlistController.updatePlaylist.ApiOperation.Notes}")
  @PutMapping(
      path = "/{id}",
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public PlaylistRest updatePlaylist(
      @PathVariable String id, @RequestBody PlaylistRequestModel playlistDetails)
      throws PlaylistServiceExceptionApp {

    ModelMapper modelMapper = new ModelMapper();
    PlaylistDTO playlistDTO = modelMapper.map(playlistDetails, PlaylistDTO.class);
    playlistDTO = playlistService.updatePlayList(id, playlistDTO);
    return modelMapper.map(playlistDTO, PlaylistRest.class);
  }

  @ApiOperation(
      value = "The Delete Playlist Web Service Endpoint",
      notes = "${playlistController.deletePlaylist.ApiOperation.Notes}")
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

  @ApiOperation(
      value = "The Add An Article In Playlist Web Service Endpoint",
      notes = "${playlistController.addArticle.ApiOperation.Notes}")
  @PutMapping(
      path = "/{id}/articles/{articleId}",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public PlaylistRest addArticle(@PathVariable String id, @PathVariable String articleId) {
    PlaylistDTO playlistDTO = playlistService.getPlaylistByPlaylistId(id);
    playlistDTO.getArticles().add(articleService.getArticleByArticleId(articleId));
    playlistDTO = playlistService.updatePlayList(id, playlistDTO);

    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(playlistDTO, PlaylistRest.class);
  }

  @ApiOperation(
      value = "The Remove An Article From Playlist Web Service Endpoint",
      notes = "${playlistController.removeArticle.ApiOperation.Notes}")
  @DeleteMapping(
      path = "/{id}/articles/{articleId}",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public PlaylistRest removeArticle(@PathVariable String id, @PathVariable String articleId) {
    PlaylistDTO playlistDTO = playlistService.getPlaylistByPlaylistId(id);
    List<ArticleDTO> articleDTO =
        playlistDTO.getArticles().stream()
            .filter(article -> article.getArticleId().equals(articleId))
            .collect(Collectors.toList());
    playlistDTO.getArticles().removeAll(articleDTO);
    playlistDTO = playlistService.updatePlayList(id, playlistDTO);

    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(playlistDTO, PlaylistRest.class);
  }

  @ApiOperation(
      value = "The Get Articles From A Playlist Web Service Endpoint",
      notes = "${playlistController.getArticles.ApiOperation.Notes}")
  @GetMapping(
      path = "/{id}/articles",
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public List<ArticleRest> getArticles(@PathVariable String id) {
    List<ArticleDTO> articles = playlistService.getArticles(id);

    return articleService.getArticles(articles);
  }
}
