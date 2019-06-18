package com.example.jpademo.configuration;

//import com.example.jpademo.Interceptor.loginInterceptor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories("com.example.jpademo.repository")
@EntityScan("com.example.jpademo.domain")
public class JpaConfiguration extends WebMvcConfigurationSupport {

    private static final Logger logger = LoggerFactory.getLogger(JpaConfiguration.class);

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // 注册Spring data jpa pageable的参数分解器
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//
//        registry.addViewController("/").setViewName("/login/LoginIndex");
//
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//
//        super.addViewControllers(registry);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void configureDefaultServletHandling( DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
