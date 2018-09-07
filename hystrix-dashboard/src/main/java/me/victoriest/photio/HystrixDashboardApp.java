package me.victoriest.photio;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@SpringBootApplication
public class HystrixDashboardApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(
                HystrixDashboardApp.class)
                .web(WebApplicationType.SERVLET).run(args);
    }

}
