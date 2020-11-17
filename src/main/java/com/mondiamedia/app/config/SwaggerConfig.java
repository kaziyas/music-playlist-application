package com.mondiamedia.app.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Yaser Kazerooni (yaser.kazerooni@gmail.com)
 * @version 1.0 2020.11.01
 * @since 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket apiDocket() {

    Contact contact =
        new Contact(
            "Yaser Kazerooni",
            "",
            "yaser.kazerooni@gmail.com");

    List<VendorExtension> vendorExtensions = new ArrayList<>();

    ApiInfo apiInfo =
        new ApiInfo(
            "Mondia Media app RESTful Web Service documentation",
            "This pages documents mondiamedia app RESTful Web Service endpoints",
            "1.0",
            "http://www.mondiamedia.com/service.html",
            contact,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            vendorExtensions);

    Docket docket =
        new Docket(DocumentationType.SWAGGER_2)
            .protocols(new HashSet<>(Arrays.asList("HTTP")))
            .apiInfo(apiInfo)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.mondiamedia.app"))
            .paths(PathSelectors.any())
            .build();

    return docket;
  }
}
