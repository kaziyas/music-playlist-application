package com.mondiamedia.app.service.playlist;

import com.mondiamedia.app.infrastructure.MondiaGeneralException;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
public class PlaylistServiceException extends MondiaGeneralException {

  public PlaylistServiceException(String message) {
    super(message);
  }
}
