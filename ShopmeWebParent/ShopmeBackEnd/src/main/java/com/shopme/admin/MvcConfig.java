package com.shopme.admin;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String dirName = "user-photos";
        Path userPhotosDir = Paths.get(dirName);

        String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:" + userPhotosPath + "/");

        String categoryImageName = "../categories-images";
        Path categoryImagesDir = Paths.get(categoryImageName);

        String categoryImagesPath = categoryImagesDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/categories-images/**")
                .addResourceLocations("file:" + categoryImagesPath + "/");

        String brandImageName = "../brands-logos";
        Path brandImagesDir = Paths.get(brandImageName);

        String brandImagesPath = brandImagesDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/brands-logos/**")
                .addResourceLocations("file:" + brandImagesPath + "/");
    }

}
