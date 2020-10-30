package com.mondiamedia.app;

import com.mondiamedia.app.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.10.28
 * @since 1.0
 */
@SpringBootApplication
public class AppApplication {

  public static void main(String[] args) {
    SpringApplication.run(AppApplication.class, args);
  }

  @Bean(name="AppProperties")
  public AppProperties appProperties() {
    return new AppProperties();
  }

  @Bean
  public SpringApplicationContext springApplicationContext() {
    return new SpringApplicationContext();
  }
}
