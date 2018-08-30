package me.victoriest.photio.service;

import me.victoriest.photio.dao.mapper.source.EvaluationMapper;
import me.victoriest.photio.dao.mapper.source.InvitationMapper;
import me.victoriest.photio.model.entity.*;
import me.victoriest.photio.service.feign.ScheduleFeignClient;
import me.victoriest.photio.service.feign.UserFeignClient;
import me.victoriest.photio.util.SnowFlakeIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Optional;

/**
 *
 * @author VictoriEST
 * @date 2018/8/10
 * photio
 */
@Service
public class ContractService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SnowFlakeIdGenerator snowFlakeIdGenerator;

    @Autowired
    private InvitationMapper invitationMapper;

    @Autowired
    private EvaluationMapper evaluationMapper;

    @Autowired
    UserFeignClient userFeignClient;

    @Autowired
    ScheduleFeignClient scheduleFeignClient;

    public Optional<Long> invite(@RequestParam Long userId,
                                 @RequestParam Long scheduleId,
                                 @RequestParam Long targetUserId) {
        // check the targetUser if is available
        Schedule schedule = scheduleFeignClient.getScheduleById(scheduleId).getData();

        if(schedule.getIsScheduled() == 1) {
            return Optional.empty();
        }

        Long id = snowFlakeIdGenerator.nextId();
        Date now = new Date();
        Invitation invitation = new Invitation();
        invitation.setId(id);
        invitation.setInvitorId(userId);
        invitation.setTargetScheduleId(scheduleId);
        invitation.setScheduledDate(schedule.getFreeDate());
        invitation.setTargetUserId(targetUserId);
        invitation.setCreatorId(userId);
        invitation.setState(0);
        invitation.setCreateDate(now);

        invitationMapper.insert(invitation);

        return Optional.of(id);
    }

    public boolean updateInvitationState(Long userId, Long invitationId, int state) {

        // if the invitation has been accepted, then update the state of schedule
        // TODO there is a distributed transaction problem
        if(state == 1) {
            Invitation invit = invitationMapper.selectByPrimaryKey(invitationId);
            scheduleFeignClient.scheduledTheSchedule(userId, invit.getTargetScheduleId());
        }

        InvitationExample example = new InvitationExample();
        InvitationExample.Criteria criteria = example.createCriteria();

        Invitation invitation = new Invitation();
        invitation.setInvitorId(invitationId);
        invitation.setState(state);

        return invitationMapper.updateByPrimaryKey(invitation) > 0;
    }

    public Optional<Long> evaluate(Long userId,
                                   Long targetUserId,
                                   int score,
                                   String message) {
        // TODO check if the scheduled date is early then date now
        Long id = snowFlakeIdGenerator.nextId();
        Date now = new Date();
        Evaluation evaluation = new Evaluation();
        evaluation.setId(id);
        evaluation.setEvatuateUserId(targetUserId);
        evaluation.setUserId(userId);
        evaluation.setScore(score);
        evaluation.setMsg(message);
        evaluation.setCreatorId(userId);
        evaluation.setCreateDate(now);

        evaluationMapper.insert(evaluation);

        return Optional.of(id);
    }

}
