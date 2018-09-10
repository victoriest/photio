package me.victoriest.photio;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 *
 * @author VictoriEST
 * @date 2018/9/10
 * photio
 */
@EnableTurbine
@SpringBootApplication
public class TurbineApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(
                TurbineApp.class)
                .web(WebApplicationType.SERVLET).run(args);
    }

}
