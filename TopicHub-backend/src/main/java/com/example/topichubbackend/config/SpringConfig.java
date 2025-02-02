package com.example.topichubbackend.config;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring6.*;
import org.thymeleaf.spring6.templateresolver.*;
import org.thymeleaf.spring6.view.*;
import org.thymeleaf.templatemode.*;
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.example.topichubbackend.controller.rest")
@ComponentScan(basePackages = "com.example.topichubbackend.controller.mvc")
@ComponentScan(basePackages = "com.example.topichubbackend.services")
@ComponentScan(basePackages = "com.example.topichubbackend.repository")
@ComponentScan(basePackages = "com.example.topichubbackend.config")
@ComponentScan(basePackages = "com.example.topichubbackend.mapper")
public class SpringConfig implements WebMvcConfigurer{

//    private final ApplicationContext applicationContext;

//    @Autowired
//    public SpringConfig(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
@Bean
public DispatcherServlet dispatcherServlet() {
DispatcherServlet dispatcherServlet = new DispatcherServlet();
dispatcherServlet.setThreadContextInheritable(true);
return dispatcherServlet;
}

    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
       // templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver springResourceTemplateResolver){
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(springResourceTemplateResolver);
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine(templateResolver()));
        resolver.setCharacterEncoding("UTF-8");
        registry.viewResolver(resolver);
    }


}
