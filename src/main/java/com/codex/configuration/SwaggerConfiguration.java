package com.codex.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	@Value("${title}")
	private String appTitle;

	@Value("${description}")
	private String appDescription;

	@Value("${version}")
	private String appVersion;

	@Value("${license}")
	private String appLicense;

	@Value("${name}")
	private String contactName;

	@Value("${url}")
	private String contactUrl;

	@Value("${email}")
	private String contactEmail;

	@Bean
	public Docket userApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.codex.controller")).build().apiInfo(apiDetails());
	}

	private ApiInfo apiDetails() {
		return new ApiInfoBuilder().title(appTitle).description(appDescription).version(appVersion).license(appLicense)
				.contact(new Contact(contactName, contactUrl, contactEmail)).build();
	}
}
