package com.mondiamedia.app.service.playlist;

import com.mondiamedia.app.infrastructure.MondiamediaException;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
public class PlaylistServiceException extends MondiamediaException {

  public PlaylistServiceException(String message) {
    super(message);
  }
}
