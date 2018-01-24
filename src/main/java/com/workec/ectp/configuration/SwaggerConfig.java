package com.workec.ectp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by caixiaoling on 2018/1/23.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.workec.ectp.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {

        Contact contact = new Contact("蔡晓凌", "", "");

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("REST API")
                .description("REST API for Service Pack")
                .contact(contact)
                .version("1.1.0")
                .termsOfServiceUrl("Terms of service")
                .license("")
                .licenseUrl("")
                .build();
        return apiInfo;
    }
}