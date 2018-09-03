package me.victoriest.photio.gateway.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author VictoriEST
 * @date 2018/9/3
 * photio
 */
@Component
@Primary
@EnableAutoConfiguration
public class SwaggerConfig implements SwaggerResourcesProvider {
    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
        resources.add(swaggerResource("producer-user", "/producer-user/v2/api-docs", "2.0"));
        resources.add(swaggerResource("producer-schedule", "/producer-schedule/v2/api-docs", "2.0"));
        resources.add(swaggerResource("producer-contract", "/producer-contract/v2/api-docs", "2.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}
