package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer { // web 설정파일로 만들어줌.

    @Value("${file.path}")
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        // C:/workspace/springbootwork/upload/ 경로 설정해주기
        registry
                .addResourceHandler("/upload/**")// jsp 페이지에서 /upload/** 경로패턴이 나오면
                .addResourceLocations("file:///" + uploadFolder)
                .setCachePeriod(60 * 10 * 6) // 1시간동안 캐싱하고
                .resourceChain(true) // ture면 발동한다.
                .addResolver(new PathResourceResolver());
    }
}