package com.dpndcs4demodsbdbp.dpndcs4demodsbdbp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Dpndcs4DemoDsbdbpApplication {

    public static void main(String[] args) {
        SpringApplication.run(Dpndcs4DemoDsbdbpApplication.class, args);
    }


    //may have interferences with docker
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public WebMvcConfigurer corsMappingConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "PATCH")
                        .maxAge(3600)
                        .allowedHeaders("*")
                        .allowCredentials(false);
            }
        };
    }

}
