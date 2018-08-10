package me.victoriest.photio.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.victoriest.photio.annotation.LoginUser;
import me.victoriest.photio.model.dto.ResponseDto;
import me.victoriest.photio.model.entity.Schedule;
import me.victoriest.photio.model.entity.User;
import me.victoriest.photio.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
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

    @ApiOperation(value = "创建一个日程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "date", value = "日程时间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "tags", value = "用户自定义tags", required = true, dataType = "String")
    })
    @PutMapping("/schedule")
    public ResponseDto createSchedule(@ApiIgnore @LoginUser User user,
                                      @RequestParam Date date,
                                      @RequestParam String tags) {
        Optional<Long> result = scheduleService.createSchedule(user.getId(), date, tags);
        return new ResponseDto().success(result.get());
    }

    @ApiOperation(value = "日程确定")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "scheduleId", value = "日程ID", required = true, dataType = "Long")
    })
    @PutMapping("/scheduled")
    public ResponseDto scheduledTheSchedule(@ApiIgnore @LoginUser User user,
                                            @RequestParam Long scheduleId) {
        boolean result = scheduleService.scheduledTheSchedule(user.getId(), scheduleId);
        return result ? new ResponseDto().success() : new ResponseDto().fail("");
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "beginDate", value = "日程时间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "endDate", value = "日程时间", dataType = "Date"),
            @ApiImplicitParam(name = "tags", value = "用户自定义tags", dataType = "String")
    })
    @GetMapping("/search")
    public ResponseDto searchPartner(@ApiIgnore @LoginUser User user,
                                     @RequestParam Date beginDate,
                                     @RequestParam Date endDate,
                                     @RequestParam String tags) {
        List<Schedule> result = scheduleService.searchPartner(user.getId(), beginDate, endDate, tags);
        return new ResponseDto().success(result);
    }

}
