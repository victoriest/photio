package me.victoriest.photio.service.feign;

import me.victoriest.photio.model.dto.ResponseDto;
import me.victoriest.photio.model.entity.Schedule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author VictoriEST
 * @date 2018/8/30
 * photio
 */
@Component
@FeignClient(name = "producer-schedule")
public interface ScheduleFeignClient {

    @RequestMapping(value = "v1/api/schedule/{id}", method = RequestMethod.GET)
    ResponseDto<Schedule> getScheduleById(@PathVariable(value = "id") Long id);

    @RequestMapping(value = "v1/api/scheduled", method = RequestMethod.GET)
    ResponseDto scheduledTheSchedule(@RequestParam String token,
                                     @RequestParam Long scheduleId);

}
