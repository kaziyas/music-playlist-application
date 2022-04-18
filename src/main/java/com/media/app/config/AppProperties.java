package com.media.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
@Component
public class AppProperties {
  private static final String PAGE_LIMIT = "playlist.media.pagelimit";
  private static final String GATEWAY_KEY = "playlist.media.gatewaykey";
  private static final String TOKEN_API_URL = "playlist.media.tokenapi.url";
  private static final String SEARCH_API_URL = "playlist.media.searchapi.url";

  @Autowired private Environment env;

  public String getTokenSecret() {
    return env.getProperty(GATEWAY_KEY);
  }

  public String getPageLimit() {
    return env.getProperty(PAGE_LIMIT);
  }

  public String getTokenApiUrl() {
    return env.getProperty(TOKEN_API_URL);
  }

  public String getSearchApiUrl() {
    return env.getProperty(SEARCH_API_URL);
  }
}
