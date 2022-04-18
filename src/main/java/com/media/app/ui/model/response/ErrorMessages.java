package com.media.app.ui.model.response;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
public enum ErrorMessages {
  MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields");

  private final String errorMessage;

  ErrorMessages(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}
