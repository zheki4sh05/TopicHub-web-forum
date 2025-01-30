//package com.example.topichubbackend.config;
//
//import jakarta.servlet.*;
//import org.springframework.context.annotation.*;
//import org.springframework.web.*;
//import org.springframework.web.context.*;
//import org.springframework.web.context.support.*;
//import org.springframework.web.servlet.*;
//
//@Configuration
//public class WebConfigurer implements WebApplicationInitializer {
//
//
//    @Override
//    public void onStartup(ServletContext container) {
//        // Create the 'root' Spring application context
//        AnnotationConfigWebApplicationContext rootContext =
//                new AnnotationConfigWebApplicationContext();
//        rootContext.register(SpringConfig.class);
//
//        // Manage the lifecycle of the root application context
//        container.addListener(new ContextLoaderListener(rootContext));
//
//        // Create the dispatcher servlet's Spring application context
//        AnnotationConfigWebApplicationContext dispatcherContext =
//                new AnnotationConfigWebApplicationContext();
//        dispatcherContext.register(DispatcherConfig.class);
//
//        // Register and map the dispatcher servlet
//        ServletRegistration.Dynamic dispatcher =
//                container.addServlet("dispatcher2", new DispatcherServlet(dispatcherContext));
//        dispatcher.setLoadOnStartup(1);
//        dispatcher.addMapping("/");
//    }
//
//}
