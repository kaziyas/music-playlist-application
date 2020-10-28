package com.mondiamedia.app.ui.model.response;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
public enum ErrorMessages {
  MISSING_REQUIRED_FIELD("Missing required field. Please check documentation for required fields"),
  NO_RECORD_FOUND("Record with provided id is not found");

  private String errorMessage;

  ErrorMessages(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
