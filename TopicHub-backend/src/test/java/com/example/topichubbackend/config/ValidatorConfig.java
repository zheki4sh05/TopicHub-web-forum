package com.example.topichubbackend.config;

import jakarta.inject.*;
import org.hibernate.validator.messageinterpolation.*;
import org.springframework.context.annotation.*;
import org.springframework.validation.beanvalidation.*;

@Configuration
public class ValidatorConfig {

//        @Bean
//        public LocalValidatorFactoryBean validator() {
//            return new LocalValidatorFactoryBean();
//        }
//        @Bean
//    public ParameterMessageInterpolator interpolator(){
//            return new ParameterMessageInterpolator();
//        }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }


}
