package me.victoriest.photio.gateway.service.hystrix;

import me.victoriest.photio.gateway.service.feign.UserFeignClient;
import me.victoriest.photio.model.dto.ResponseDto;
import me.victoriest.photio.model.entity.User;
import org.springframework.stereotype.Component;

/**
 *
 * @author VictoriEST
 * @date 2018/8/29
 * photio
 */
@Component
public class UserFeignHystrix implements UserFeignClient {
    @Override
    public ResponseDto<User> getById(Long userId) {
        return new ResponseDto<User>().fail("error hystrix");
    }

    @Override
    public ResponseDto registry(String account, String name, Integer userType) {
        return new ResponseDto<User>().fail("error hystrix");
    }

    @Override
    public ResponseDto getRsaKey() {
        return new ResponseDto<User>().fail("error hystrix");
    }

    @Override
    public ResponseDto login(String rsaKeyId, String account, String pwd) {
        return new ResponseDto<User>().fail("error hystrix");
    }
}
