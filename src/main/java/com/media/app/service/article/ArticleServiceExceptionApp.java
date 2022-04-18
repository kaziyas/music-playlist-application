package com.media.app.service.article;

import com.media.app.infrastructure.TechnicalException;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
public class ArticleServiceExceptionApp extends TechnicalException {

  public ArticleServiceExceptionApp(String message) {
    super(message);
  }
}
