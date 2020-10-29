package com.mondiamedia.app.exceptions;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
public class ArticleServiceException extends RuntimeException {

  public ArticleServiceException(String message) {
    super(message);
  }
}
