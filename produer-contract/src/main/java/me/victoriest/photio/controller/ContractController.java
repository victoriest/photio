package me.victoriest.photio.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.victoriest.photio.model.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author VictoriEST
 * @date 2018/8/10
 * photio
 */
@RestController("contractController")
@RequestMapping(value = "/v1/api")
public class ContractController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation(value = "邀请伙伴")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "scheduleId", value = "日程id", required = true, dataType = "long")
    })
    @PutMapping("invitation/invite")
    public ResponseDto invite(@RequestParam String account,
                              @RequestParam Long scheduleId) {
        throw new NotImplementedException();
    }

    @ApiOperation(value = "接受邀请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "invitationId", value = "接受邀请的id", required = true, dataType = "long")
    })
    @PutMapping("invitation/accept")
    public ResponseDto acceptInvitation(@RequestParam Long invitationId) {
        throw new NotImplementedException();
    }

    @ApiOperation(value = "拒绝邀请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "invitationId", value = "拒绝邀请的id", required = true, dataType = "long")
    })
    @PutMapping("invitation/reject")
    public ResponseDto rejectInvitation(@RequestParam Long invitationId) {
        throw new NotImplementedException();
    }

    @ApiOperation(value = "评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "被评价人的id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "score", value = "评分", required = true, dataType = "int"),
            @ApiImplicitParam(name = "message", value = "评语", required = true, dataType = "String")
    })
    @PutMapping("invitation/evaluate")
    public ResponseDto evaluate(@RequestParam Long userId,
                                @RequestParam int score,
                                @RequestParam String message) {
        throw new NotImplementedException();
    }
}
