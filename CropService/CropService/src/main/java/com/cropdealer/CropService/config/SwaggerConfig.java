package com.cropdealer.CropService.config;

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
                        .title("Crop Service API")
                        .description("Handles crop-related operations for farmers")
                        .version("1.0"));
    }
}
