package me.victoriest.photio.eureka;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka服务注册中心
 * <p>
 * 启动一个服务注册中心，只需要一个注解@EnableEurekaServer
 * 配置文件参见application.yml
 * <p>
 * profile文件为eureka-server
 * mvn : clean spring-boot:run -P eureka-server
 *
 * @author VictoriEST
 * @date 2017/12/11
 * spring-cloud-step-by-step
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServerApp.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
