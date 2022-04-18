package com.media.app.service.playlist;

import com.media.app.infrastructure.TechnicalException;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
public class PlaylistServiceExceptionApp extends TechnicalException {

  public PlaylistServiceExceptionApp(String message) {
    super(message);
  }
}
