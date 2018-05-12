package com.authbridge.config;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
@EnableConfigurationProperties({ ResourceProperties.class })
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	
        
    	registry.addViewController("/").setViewName("login");
    	registry.addViewController("/login").setViewName("login");
        registry.addViewController("/dashboard").setViewName("dashboard");
        registry.addViewController("/search").setViewName("search");
        registry.addViewController("/alias").setViewName("alias");
        registry.addViewController("/abbr").setViewName("abbr");
        registry.addViewController("/stopword").setViewName("stopword");
        registry.addViewController("/weightage").setViewName("weightage");
        registry.addViewController("/schedular").setViewName("schedular");
        registry.addViewController("/addeditschedular").setViewName("addeditschedular");
        registry.addViewController("/403").setViewName("403");
        registry.addViewController("/error").setViewName("403");
    }
    
    @Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
    
    @Bean
    public ErrorPageFilter errorPageFilter() {
        return new ErrorPageFilter();
    }

    @Bean
    public FilterRegistrationBean disableSpringBootErrorFilter(ErrorPageFilter filter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setEnabled(false);
        return filterRegistrationBean;
    }
    

}