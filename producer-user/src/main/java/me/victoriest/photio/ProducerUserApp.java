package me.victoriest.photio;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author VictoriEST
 * @date 2018/7/23
 * photio
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableAutoConfiguration
@ComponentScan
@EnableEurekaClient
public class ProducerUserApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(
                ProducerUserApp.class)
                .web(WebApplicationType.SERVLET).run(args);
    }

}
