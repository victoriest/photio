package me.victoriest.photio.gateway;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 *
 * @author VictoriEST
 * @date 2018/8/30
 * photio
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class ZuulApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(
                ZuulApp.class)
                .web(WebApplicationType.SERVLET).run(args);
    }

}
