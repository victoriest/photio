package me.victoriest.photio.gateway;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *
 * @author VictoriEST
 * @date 2018/8/30
 * photio
 */
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
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
