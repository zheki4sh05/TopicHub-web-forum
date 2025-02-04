package com.example.topichubbackend.config;

import org.springframework.context.annotation.*;
import org.springframework.validation.beanvalidation.*;

@Configuration
public class ValidatorConfig {

        @Bean
        public static MethodValidationPostProcessor methodValidationPostProcessor() {
            return new MethodValidationPostProcessor();
        }

        @Bean
        public LocalValidatorFactoryBean validator() {
            return new LocalValidatorFactoryBean();
        }

}
