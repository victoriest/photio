package me.victoriest.photio.gateway.service.feign;

import me.victoriest.photio.model.dto.ResponseDto;
import me.victoriest.photio.model.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("v1/api/registry")
    ResponseDto registry(@RequestParam String account,
                                @RequestParam String name,
                                @RequestParam Integer userType);

    @GetMapping(value = "v1/api/getRsaKey")
    ResponseDto getRsaKey();


    @PostMapping(value = "v1/api/login")
    ResponseDto login(@RequestParam String rsaKeyId,
                             @RequestParam(required = false) String account,
                             @RequestParam String pwd);
}
