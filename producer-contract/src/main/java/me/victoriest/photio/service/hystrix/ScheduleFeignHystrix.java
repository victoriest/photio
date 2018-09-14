package me.victoriest.photio.service.hystrix;

import me.victoriest.photio.model.dto.ResponseDto;
import me.victoriest.photio.model.entity.Schedule;
import me.victoriest.photio.service.feign.ScheduleFeignClient;
import org.springframework.stereotype.Component;

/**
 *
 * @author VictoriEST
 * @date 2018/8/30
 * photio
 */
@Component
public class ScheduleFeignHystrix implements ScheduleFeignClient {

    @Override
    public ResponseDto<Schedule> getScheduleById(Long id) {
        return new ResponseDto<Schedule>().fail("error hystrix when getScheduleById");
    }

    @Override
    public ResponseDto scheduledTheSchedule(String token, Long scheduleId) {
        return new ResponseDto<Schedule>().fail("error hystrix when scheduledTheSchedule");
    }

}
