package com.mondiamedia.app.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.30
 * @since 1.0
 */
@Configuration
public class AppConfig {
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  @Bean(name="AppProperties")
  public AppProperties appProperties() {
    return new AppProperties();
  }
}
