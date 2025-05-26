package com.auction.system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "Auction System", version = "0.5.1", description = "This is an OpenAPI description of an **Online Auction System**.", contact = @Contact(name = "Vanshaj Saxena", email = "vanshajsaxena2005@gmail.com"), license = @License(name = "MIT License", url = "https://github.com/VanshajSaxena/auction-system?tab=MIT-1-ov-file#")), servers = @Server(url = "http://127.0.0.1:8080", description = "Localhost"))
@Configuration
public class OpenApiConfig {
}
