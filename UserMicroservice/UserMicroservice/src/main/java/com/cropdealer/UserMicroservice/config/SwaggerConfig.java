package com.cropdealer.UserMicroservice.config;



import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI cropOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("USER Service API")
                        .description("Handles USER-related operations for BOTH FARMER AS WELL AS DEALER")
                        .version("1.0"));
    }
}
