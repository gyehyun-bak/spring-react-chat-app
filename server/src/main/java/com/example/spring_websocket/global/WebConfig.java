package com.example.spring_websocket.global;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 경로 허용
                .allowedOrigins("http://localhost:5173")  // 허용할 프론트엔드 주소
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}
