package com.auction.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

  @Bean(name = "org.openapitools.configuration.SpringDocConfiguration.apiInfo")
  OpenAPI apiInfo() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Online Auction System")
                .description("This is an OpenAPI description of an **Online Auction System**. ")
                .version("0.5.1"))
        .components(
            new Components()
                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT"))
                .addSecuritySchemes("oauth2google", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("ID Token (Google)"))
                .addSecuritySchemes("oauth2apple", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("ID Token (Apple)")));
  }
}
