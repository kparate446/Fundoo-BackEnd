package com.bridgelabz.user;
/**
 * @Created By :- krunal Parate
 * @Purpose :- It is used in object Mapper
 */
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ModelConfiguration {
	@Bean
	ModelMapper getModelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}
}
