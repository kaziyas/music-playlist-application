package com.mondiamedia.app.ui.controller;

import com.mondiamedia.app.exceptions.PlaylistServiceException;
import com.mondiamedia.app.service.PlaylistService;
import com.mondiamedia.app.service.shared.PlaylistDTO;
import com.mondiamedia.app.ui.model.request.PlaylistRequestModel;
import com.mondiamedia.app.ui.model.response.ErrorMessages;
import com.mondiamedia.app.ui.model.response.OperationStatusModel;
import com.mondiamedia.app.ui.model.response.PlaylistRest;
import com.mondiamedia.app.ui.model.response.RequestOperationStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/playlists") // http://localhost:9100/mondia-app/playlists
public class PlaylistController {

  @Autowired private PlaylistService playlistService;

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
}
