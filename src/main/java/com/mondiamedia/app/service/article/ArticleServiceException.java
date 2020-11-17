package com.mondiamedia.app.service.article;

import com.mondiamedia.app.infrastructure.MondiamediaException;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
public class ArticleServiceException extends MondiamediaException {

  public ArticleServiceException(String message) {
    super(message);
  }
}
