package com.tilindra.demo.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * @return Docket
	 */
	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("com.tilindra.demo.api.rest").apiInfo(apiInfo()).select()
				.paths(Predicates.not(regex("/error.*"))).build();
	}

	/**
	 * @return ApiInfoBuilder
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Employee Endpoint Services")
				.description(
						"This project Employee Endpoint Services will be exposed for fetch/update/delete employee information.")
				.termsOfServiceUrl("demo").contact(new Contact("TEJ ILINDRA", "http://www.demo.com", null))
				.title("Employee Endpoint Services").version("1.0").build();
	}

}
