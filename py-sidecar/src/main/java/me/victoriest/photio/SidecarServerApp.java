package me.victoriest.photio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

/**
 * @author VictoriEST
 */
@EnableSidecar
@SpringBootApplication
public class SidecarServerApp {

    public static void main(String[] args) {
        SpringApplication.run(SidecarServerApp.class, args);
    }
}
