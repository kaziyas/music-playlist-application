package com.mondiamedia.app.config;

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
  private static final String PAGE_LIMIT = "mondiaplaylist.mondia.pagelimit";
  private static final String GATEWAY_KEY = "mondiaplaylist.mondia.gatewaykey";
  private static final String MONDIA_TOKEN_API_URL = "mondiaplaylist.mondia.tokenapi.url";
  private static final String MONDIA_SEARCH_API_URL = "mondiaplaylist.mondia.searchapi.url";

  @Autowired private Environment env;

  public String getTokenSecret() {
    return env.getProperty(GATEWAY_KEY);
  }

  public String getPageLimit() {
    return env.getProperty(PAGE_LIMIT);
  }

  public String getMondiaTokenApiUrl() {
    return env.getProperty(MONDIA_TOKEN_API_URL);
  }

  public String getMondiaSearchApiUrl() {
    return env.getProperty(MONDIA_SEARCH_API_URL);
  }
}
