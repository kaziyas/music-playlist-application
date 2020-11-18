package com.mondiamedia.app.service.article;

import com.mondiamedia.app.infrastructure.MondiaGeneralException;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
public class ArticleServiceException extends MondiaGeneralException {

  public ArticleServiceException(String message) {
    super(message);
  }
}
