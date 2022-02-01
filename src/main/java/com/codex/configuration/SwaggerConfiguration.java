package com.codex.configuration;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
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
	public Docket studentApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.codex.controller")).build().apiInfo(apiDetails());
	}

	private ApiInfo apiDetails() {
		return new ApiInfoBuilder().title(appTitle).description(appDescription).version(appVersion).license(appLicense)
				.contact(new Contact(contactName, contactUrl, contactEmail)).build();
	}

	/**
	 * Springfox workaround required by Spring Boot 2.6 See
	 * https://github.com/springfox/springfox/issues/3462
	 */
	@Bean
	public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
		return new BeanPostProcessor() {

			@Override
			public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
				if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
					customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
				}
				return bean;
			}

			private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(
					List<T> mappings) {
				mappings.removeIf(mapping -> mapping.getPatternParser() != null);
			}

			@SuppressWarnings("unchecked")
			private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
				try {
					Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
					field.setAccessible(true);
					return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new IllegalStateException(e);
				}
			}
		};
	}
}
