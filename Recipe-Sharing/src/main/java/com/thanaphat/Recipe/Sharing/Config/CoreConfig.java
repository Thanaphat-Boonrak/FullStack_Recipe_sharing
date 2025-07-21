package com.thanaphat.Recipe.Sharing.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CoreConfig
        implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(
            CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200","*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }

}
