package com.gbarwinski.organizerspring.registration_login_logout.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


@Configuration
public class MyWebInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {

        return new Class[]{WebSecurityConfig.class};
    }

    @Override
    protected String[] getServletMappings() {

        return new String[]{"/*"};
    }
}

