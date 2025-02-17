package com.minseg.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.cors.CorsConfiguration;

@SpringBootApplication
public class Application {

    //Método para iniciar el programa
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    //Método para configurar el Cross Origin Resource Sharing
    @Configuration
    @EnableWebMvc
    public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOriginPatterns("https://192.168.1.3:3000")
                    .allowedMethods(CorsConfiguration.ALL)
                    .allowedHeaders(CorsConfiguration.ALL)
                    .maxAge(1728000)
                    .allowCredentials(true);
        }
    }
}
