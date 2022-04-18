package com.media.app.infrastructure;

import com.media.app.service.article.ArticleServiceExceptionApp;
import com.media.app.service.playlist.PlaylistServiceExceptionApp;
import com.media.app.ui.model.response.ErrorMessage;
import java.util.Date;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
@ControllerAdvice
public class AppExceptionsHandler {
  @ExceptionHandler(value = {PlaylistServiceExceptionApp.class})
  public ResponseEntity<Object> handleUserServiceException(
          PlaylistServiceExceptionApp ex, WebRequest request) {

    ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
    return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = {ArticleServiceExceptionApp.class})
  public ResponseEntity<Object> handleUserServiceException(
          ArticleServiceExceptionApp ex, WebRequest request) {

    ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
    return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
    return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
