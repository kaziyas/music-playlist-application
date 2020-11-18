package com.mondiamedia.app.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.11.18
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"example.firstProperty=annotation"})
public class AppPropertiesTest {
  final String PAGE_LIMIT = "10";
  final String GATEWAY_KEY = "G058efa6a-f20b-f0af-bac2-9f515930928a";
  final String MONDIA_SEARCH_API_URL = "https://staging-gateway.mondiamedia.com/v1/api/content/search";
  final String MONDIA_TOKEN_API_URL = "https://staging-gateway.mondiamedia.com/v0/api/gateway/token/client";

  @Autowired private AppProperties appProperties;

  @Test
  public void testAppProperties() {
    String pageLimitProperty = appProperties.getPageLimit();
    String getWayKey = appProperties.getTokenSecret();
    String mondiaTokenApiUrl = appProperties.getMondiaTokenApiUrl();
    String mondiaSearchApiUrl = appProperties.getMondiaSearchApiUrl();

    assertEquals(PAGE_LIMIT, pageLimitProperty);
    assertEquals(GATEWAY_KEY, getWayKey);
    assertEquals(MONDIA_TOKEN_API_URL, mondiaTokenApiUrl);
    assertEquals(MONDIA_SEARCH_API_URL, mondiaSearchApiUrl);
  }
}
