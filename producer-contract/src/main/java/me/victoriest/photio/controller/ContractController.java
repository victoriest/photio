package me.victoriest.photio.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.victoriest.photio.model.dto.ResponseDto;
import me.victoriest.photio.mq.ContractMqProcessor;
import me.victoriest.photio.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author VictoriEST
 * @date 2018/8/10
 * photio
 */
@RestController("contractController")
@RequestMapping(value = "/v1/api")
public class ContractController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractMqProcessor contractMqProcessor;


    @ApiOperation(value = "邀请伙伴")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "long"),
            @ApiImplicitParam(name = "scheduleId", value = "日程id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "targetUserId", value = "被邀请用户ID", required = true, dataType = "long")
    })
    @PutMapping("invitation/invite")
    @HystrixCommand(fallbackMethod = "inviteFallback")
    public ResponseDto invite(@RequestParam String token,
                              @RequestParam Long userId,
                              @RequestParam Long scheduleId,
                              @RequestParam Long targetUserId) {
        Optional<Long> result = contractService.invite(userId, scheduleId, targetUserId);
        return new ResponseDto().success(result.get());
    }

    public ResponseDto inviteFallback(String token,
                                      Long userId,
                                      Long scheduleId,
                                      Long targetUserId) {
        return new ResponseDto<>().fail("fallback");
    }

    @ApiOperation(value = "接受邀请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "long"),
            @ApiImplicitParam(name = "invitationId", value = "接受邀请的id", required = true, dataType = "long")
    })
    @PutMapping("invitation/accept")
    @HystrixCommand(fallbackMethod = "acceptInvitationFallback")
    public ResponseDto acceptInvitation(@RequestParam String token,
                                        @RequestParam Long userId,
                                        @RequestParam Long invitationId) {
        boolean result = contractService.updateInvitationState(userId, invitationId, 1);
        return result ? new ResponseDto().success() : new ResponseDto().fail("");
    }

    public ResponseDto acceptInvitationFallback(String token,
                                                Long userId,
                                                Long invitationId) {
        return new ResponseDto<>().fail("fallback");
    }

    @ApiOperation(value = "拒绝邀请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "long"),
            @ApiImplicitParam(name = "invitationId", value = "拒绝邀请的id", required = true, dataType = "long")
    })
    @PutMapping("invitation/reject")
    @HystrixCommand(fallbackMethod = "rejectInvitationFallback")
    public ResponseDto rejectInvitation(@RequestParam String token,
                                        @RequestParam Long userId,
                                        @RequestParam Long invitationId) {
        boolean result = contractService.updateInvitationState(userId, invitationId, 4);
        return result ? new ResponseDto().success() : new ResponseDto().fail("");
    }

    public ResponseDto rejectInvitationFallback(String token,
                                                Long userId,
                                                Long invitationId) {
        return new ResponseDto<>().fail("fallback");
    }

    @ApiOperation(value = "评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "long"),
            @ApiImplicitParam(name = "targetUserId", value = "被评价人的id", required = true, dataType = "long"),
            @ApiImplicitParam(name = "score", value = "评分", required = true, dataType = "int"),
            @ApiImplicitParam(name = "message", value = "评语", required = true, dataType = "String")
    })
    @PutMapping("invitation/evaluate")
    @HystrixCommand(fallbackMethod = "evaluateFallback")
    public ResponseDto evaluate(@RequestParam String token,
                                @RequestParam Long userId,
                                @RequestParam Long targetUserId,
                                @RequestParam int score,
                                @RequestParam String message) {
        Optional<Long> result = contractService.evaluate(userId, targetUserId, score, message);
        return new ResponseDto().success(result.get());
    }

    public ResponseDto evaluateFallback(String token,
                                        Long userId,
                                        Long targetUserId,
                                        int score,
                                        String message) {
        return new ResponseDto<>().fail("fallback");
    }


    @GetMapping("testmq")
    public ResponseDto testMq() {
        contractMqProcessor.responseInvite("responseInvite");
        return new ResponseDto().success();
    }
}
