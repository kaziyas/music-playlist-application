package com.mondiamedia.app.service.impl;

import com.mondiamedia.app.exceptions.PlaylistServiceException;
import com.mondiamedia.app.io.entity.PlaylistEntity;
import com.mondiamedia.app.io.repository.PlaylistRepository;
import com.mondiamedia.app.service.PlaylistService;
import com.mondiamedia.app.service.shared.PlaylistDTO;
import com.mondiamedia.app.service.shared.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
@Service
public class PlaylistServiceImpl implements PlaylistService {
  @Autowired Utils utils;

  @Autowired PlaylistRepository playlistRepository;

  @Override
  public PlaylistDTO createPlaylist(PlaylistDTO playlistDTO) {
    if (playlistRepository.findByTitle(playlistDTO.getTitle()) != null)
      throw new PlaylistServiceException("playlist already exist");

    ModelMapper modelMapper = new ModelMapper();
    PlaylistEntity playlistEntity = modelMapper.map(playlistDTO, PlaylistEntity.class);

    String publicPlaylistId = utils.generatePlaylistId(30);
    playlistEntity.setPlaylistId(publicPlaylistId);

    PlaylistEntity storedPlaylistDetail = playlistRepository.save(playlistEntity);
    PlaylistDTO returnValue = modelMapper.map(storedPlaylistDetail, PlaylistDTO.class);
    return returnValue;
  }

  @Override
  public PlaylistDTO getPlaylistByPlaylistId(String id) {
    PlaylistEntity playlistEntity = playlistRepository.findByPlaylistId(id);
    if (playlistEntity == null)
      throw new PlaylistServiceException("Playlist with ID: " + id + " not found");

    ModelMapper modelMapper = new ModelMapper();
    PlaylistDTO playlistDTO = modelMapper.map(playlistEntity, PlaylistDTO.class);
    return playlistDTO;
  }

  @Override
  public PlaylistDTO updatePlayList(String playlistId, PlaylistDTO playlistDTO) {
    PlaylistEntity playlistEntity = playlistRepository.findByPlaylistId(playlistId);

    if (playlistEntity == null)
      throw new PlaylistServiceException("Playlist with ID: " + playlistId + " not found");

    playlistEntity.setTitle(playlistDTO.getTitle());
    playlistEntity.setDescription(playlistDTO.getDescription());
    PlaylistEntity updatePlayListEntity = playlistRepository.save(playlistEntity);

    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(updatePlayListEntity, PlaylistDTO.class);
  }

  @Override
  public void deletePlaylist(String id) {
    PlaylistEntity playlistEntity = playlistRepository.findByPlaylistId(id);

    if (playlistEntity == null)
      throw new PlaylistServiceException("Playlist with ID: " + id + " not found");

    playlistRepository.delete(playlistEntity);
  }
}
