package com.adolph.javatools.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Import(WebMvcAutoConfiguration.EnableWebMvcConfiguration.class)
@EnableConfigurationProperties({ WebMvcProperties.class, ResourceProperties.class })
public class WebAppConfig implements WebMvcConfigurer {
    /**
     * 在配置文件中配置的文件保存路径
     */
    @Value("${file.export.dsmpath:/file/}")
    private String fileExportDsmPath;
    @Value("${file.export.mapping.path:D:\\file\\}")
    private String fileExportMappingPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将页面访问路径/file/映射到D:\file\**目录下的文件，可直接下载
        registry.addResourceHandler(fileExportMappingPath+"**").addResourceLocations("file:"+fileExportDsmPath);
    }
}
