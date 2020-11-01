package com.mondiamedia.app.security;

import com.mondiamedia.app.SpringApplicationContext;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.29
 * @since 1.0
 */
public class SecurityConstants {

  public static final String HEADER_STRING = "Authorization";
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_PARAMETER= "X-MM-GATEWAY-KEY";
  public static final String GATEWAY_KEY ="GatewayKey";

  public static String getTokenSecret() {
    AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean(AppProperties.APP_PROPERTIES_BEAN_NAME);
    return appProperties.getTokenSecret();
  }
}
