package com.example.projekt2_gruppe4.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// Lille konfigurering der skulle hjælpe en smule css kode med at tvinges ind på siden, der ellers ikke rigtig ville komme ind ordentligt
@Configuration
public class webconfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}