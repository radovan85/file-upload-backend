package com.radovan.spring.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.radovan.spring")
public class SpringMvcConfiguration {

	@Bean
	public ModelMapper getMapper() {
		ModelMapper returnValue = new ModelMapper();
		returnValue.getConfiguration().setAmbiguityIgnored(true).setFieldAccessLevel(AccessLevel.PRIVATE);
		returnValue.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return returnValue;
	}

	/*
	 * @Bean public MultipartResolver multipartResolver() { CommonsMultipartResolver
	 * multipartResolver = new CommonsMultipartResolver();
	 * multipartResolver.setMaxUploadSize(10240000); return multipartResolver; }
	 */
}
