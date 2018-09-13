package me.victoriest.photio.config;

import me.victoriest.photio.aop.LoginUserHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 *
 * @author VictoriEST
 * @date 2018/3/28
 * spring-cloud-step-by-step
 */
@Configuration
@ConditionalOnWebApplication
@Import({MybatisConfig.class,
        RedisConfig.class,
        RedisClusterConfigurationProperties.class
})
public class AppConfig extends WebMvcConfigurationSupport {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private LoginUserHandlerMethodArgumentResolver myHandlerMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(myHandlerMethodArgumentResolver);
    }

    /**
     * 访问swagger-ui.html总是404
     * 原因:
     * 这个自定义的类继承自WebMvcConfigurationSupport，
     * 如果你在IDE中搜索这个类的实现类，
     * 可以发现spring boot有一个子类EnableWebMvcConfiguration，
     * 并且是自动config的.
     * 我们知道，如果一个类用户自己在容器中生成了bean，
     * spring boot就不会帮你自动config。
     * 所以，问题的原因是我们把spring boot自定义的那个bean覆盖了。
     * @return
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowCredentials(true).maxAge(3600);
    }

}
