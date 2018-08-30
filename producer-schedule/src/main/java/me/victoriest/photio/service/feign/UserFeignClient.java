package me.victoriest.photio.service.feign;

import me.victoriest.photio.model.dto.ResponseDto;
import me.victoriest.photio.model.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author VictoriEST
 * @date 2018/8/29
 * photio
 */
@Component
@FeignClient(name = "producer-user")
public interface UserFeignClient {

    @RequestMapping(value = "v1/api/user/{userId}", method = RequestMethod.GET)
    ResponseDto<User> getById(@PathVariable(value = "userId") Long userId);
}
