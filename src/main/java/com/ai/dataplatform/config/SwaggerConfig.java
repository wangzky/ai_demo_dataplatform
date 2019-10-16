package com.ai.dataplatform.config;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 配置
 *
 * */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.basePackage}")
    private String basePackage;
    @Value("${swagger.title}")
    private String title;
    @Value("${swagger.description}")
    private String description;
    @Value("${swagger.contact}")
    private String contact;
    @Value("${swagger.version}")
    private String version;

    /**
     * 设置监控路径
     *
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .produces(Sets.newHashSet("application/json"))
                .consumes(Sets.newHashSet("application/json"))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .contact(contact)
                .version(version)
                .build();
    }

}
