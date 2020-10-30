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

  @Autowired private Environment env;

  public String getTokenSecret() {
    return env.getProperty("GatewayKey");
  }
}
