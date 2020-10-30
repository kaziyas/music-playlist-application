package com.mondiamedia.app.ui.controller;

import com.mondiamedia.app.exceptions.ArticleServiceException;
import com.mondiamedia.app.exceptions.PlaylistServiceException;
import com.mondiamedia.app.service.ArticleService;
import com.mondiamedia.app.service.PlaylistService;
import com.mondiamedia.app.service.shared.ArticleDTO;
import com.mondiamedia.app.service.shared.PlaylistDTO;
import com.mondiamedia.app.ui.model.request.ArticleRequestModel;
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
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired private PlaylistService playlistService;

  @Autowired private ArticleService articleService;

  @GetMapping(
      path = "/{id}",
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public PlaylistRest getPlaylist(@PathVariable String id) {
    final PlaylistDTO playlistDTO = playlistService.getPlaylistByPlaylistId(id);

    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(playlistDTO, PlaylistRest.class);
  }

  @PostMapping(
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public PlaylistRest createPlaylist(@RequestBody PlaylistRequestModel playlistDetails)
      throws PlaylistServiceException {

    if (playlistDetails.getTitle().isEmpty())
      throw new PlaylistServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

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
  public PlaylistRest addArticle(
      @PathVariable String id, @RequestParam String articleId) {
/*
    if (articleRequestModel.getArtistName().isEmpty()
        || articleRequestModel.getTrackName().isEmpty())
      throw new ArticleServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
    ArticleDTO articleDTO = modelMapper.map(articleRequestModel, ArticleDTO.class);
*/
    ModelMapper modelMapper = new ModelMapper();

    PlaylistDTO playlistDTO = playlistService.getPlaylistByPlaylistId(id);
    ArticleDTO articleDTO = articleService.getArticleByArticleId(articleId);

    playlistDTO.getArticles().add(articleDTO);

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
