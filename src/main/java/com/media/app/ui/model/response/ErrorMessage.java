package com.media.app.ui.model.response;

import java.util.Date;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
@Data
@RequiredArgsConstructor
public final class ErrorMessage {
  private final Date timestamp;
  private final String message;
}
