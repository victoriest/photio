package me.victoriest.photio.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.victoriest.photio.annotation.LoginUser;
import me.victoriest.photio.model.dto.ResponseDto;
import me.victoriest.photio.model.entity.User;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;

/**
 *
 * @author VictoriEST
 * @date 2018/8/3
 * photio
 */
@RestController("scheduleController")
@RequestMapping(value = "/v1/api")
public class ScheduleController {

    @ApiOperation(value = "创建一个日程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "date", value = "日程时间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "userType", value = "用户类型", required = true, dataType = "int"),
            @ApiImplicitParam(name = "tags", value = "用户自定义tags", required = true, dataType = "String")
    })
    @PutMapping("/schedule")
    public ResponseDto createSchedule(@ApiIgnore @LoginUser User user,
                                      @RequestParam Date date,
                                      @RequestParam Integer userType,
                                      @RequestParam String tags) {
        throw new NotImplementedException();
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
        throw new NotImplementedException();
    }

//    public ResponseDto invite() {}
//
//    public ResponseDto acceptInvitation() {}
//
//    public ResponseDto rejectInvitation() {}

}
