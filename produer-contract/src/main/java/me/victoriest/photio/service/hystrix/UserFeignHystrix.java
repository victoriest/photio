package me.victoriest.photio.service.hystrix;

import me.victoriest.photio.model.dto.ResponseDto;
import me.victoriest.photio.model.entity.User;
import me.victoriest.photio.service.feign.UserFeignClient;
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
}
