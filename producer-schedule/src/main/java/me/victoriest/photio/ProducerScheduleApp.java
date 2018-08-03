package me.victoriest.photio;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author VictoriEST
 * @date 2018/8/3
 * photio
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableAutoConfiguration
@ComponentScan
@EnableEurekaClient
public class ProducerScheduleApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(
                ProducerScheduleApp.class)
                .web(WebApplicationType.SERVLET).run(args);
    }

}
