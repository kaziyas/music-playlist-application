package com.mondiamedia.app.security;

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
  private static final String PAGE_LIMIT ="PageLimit";
  private static final String MONDIA_TOKEN_API_URL ="MondiaTokenApiUrl";
  private static final String MONDIA_SEARCH_API_URL="MondiaSearchApiUrl";

  public static final String APP_PROPERTIES_BEAN_NAME ="AppProperties";

  @Autowired private Environment env;

  public String getTokenSecret() {
    return env.getProperty(SecurityConstants.GATEWAY_KEY);
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
