package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.CacheControl;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
@PropertySource("classpath:app.properties")
@EnableWebMvc
public class WebConfig {

    @Value("${security.cors.enabled}")
    private boolean corsEnabled;

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/usr/**")
                        .addResourceLocations("classpath:/image/users/")
                        .setCachePeriod(0);
            }

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                if (!corsEnabled)
                {
                    registry.addMapping("/**").allowedOrigins("*");
                    return;
                }

                //.allowedOrigins("http://localhost:4200", "http://localhost:8080")

                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200", "http://localhost:8080")
                        .allowedMethods("GET", "PUT", "POST", "DELETE");
            }
        };
    }
}
