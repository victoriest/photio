package me.victoriest.photio.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.victoriest.photio.model.dto.ResponseDto;
import me.victoriest.photio.model.entity.Schedule;
import me.victoriest.photio.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author VictoriEST
 * @date 2018/8/3
 * photio
 */
@RestController("scheduleController")
@RequestMapping(value = "/v1/api")
public class ScheduleController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ScheduleService scheduleService;


    @ApiOperation(value = "根据指定id获取日程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "schedule id", required = true, dataType = "long")
    })
    @GetMapping("/schedule/{id}")
    public ResponseDto<Schedule> getSchedule(@RequestParam String token, @PathVariable Long id) {


        Schedule result = scheduleService.getSchedule(id).get();
        return new ResponseDto<Schedule>().success(result);
    }

    @ApiOperation(value = "创建一个日程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "long"),
            @ApiImplicitParam(name = "date", value = "日程时间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "tags", value = "用户自定义tags", required = true, dataType = "String")
    })
    @PutMapping("/schedule")
    public ResponseDto createSchedule(@RequestParam String token,
                                      @RequestParam Long userId,
                                      @RequestParam Date date,
                                      @RequestParam String tags) {
        Optional<Long> result = scheduleService.createSchedule(userId, date, tags);
        return new ResponseDto().success(result.get());
    }

    @ApiOperation(value = "日程确定")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "long"),
            @ApiImplicitParam(name = "scheduleId", value = "日程ID", required = true, dataType = "long")
    })
    @PutMapping("/scheduled")
    public ResponseDto scheduledTheSchedule(@RequestParam String token,
                                            @RequestParam Long userId,
                                            @RequestParam Long scheduleId) {
        boolean result = scheduleService.scheduledTheSchedule(userId, scheduleId);
        return result ? new ResponseDto().success() : new ResponseDto().fail("");
    }

    @ApiOperation(value = "查找合作者")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "long"),
            @ApiImplicitParam(name = "beginDate", value = "日程时间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "endDate", value = "日程时间", dataType = "Date"),
            @ApiImplicitParam(name = "tags", value = "用户自定义tags", dataType = "String")
    })
    @GetMapping("/search")
    public ResponseDto searchPartner(@RequestParam String token,
                                     @RequestParam Long userId,
                                     @RequestParam Date beginDate,
                                     @RequestParam Date endDate,
                                     @RequestParam String tags) {
        List<Schedule> result = scheduleService.searchPartner(userId, beginDate, endDate, tags);
        return new ResponseDto().success(result);
    }

}
