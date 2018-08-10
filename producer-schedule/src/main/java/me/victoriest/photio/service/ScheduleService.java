package me.victoriest.photio.service;

import me.victoriest.photio.dao.mapper.source.ScheduleMapper;
import me.victoriest.photio.model.entity.Schedule;
import me.victoriest.photio.model.entity.ScheduleExample;
import me.victoriest.photio.model.entity.User;
import me.victoriest.photio.util.SnowFlakeIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author VictoriEST
 * @date 2018/8/9
 * photio
 */
@Service
public class ScheduleService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SnowFlakeIdGenerator snowFlakeIdGenerator;

    @Autowired
    private ScheduleMapper scheduleMapper;

    public Optional<Long> createSchedule(Long userId,
                                         Date date,
                                         String tags) {
        // TODO 查询用户信息

        Long id = snowFlakeIdGenerator.nextId();
        Date now = new Date();
        Schedule schedule = new Schedule();
        schedule.setId(id);
        schedule.setUserId(userId);
        schedule.setFreeDate(date);
        schedule.setTags(tags);
        schedule.setIsScheduled(0);

        schedule.setCreatorId(userId);
        schedule.setCreateDate(date);

        scheduleMapper.insert(schedule);

        return Optional.of(id);
    }

    public boolean scheduledTheSchedule(Long userId, Long scheduleId) {
        // TODO 查询用户信息

        Schedule schedule = new Schedule();
        schedule.setId(scheduleId);
        schedule.setIsScheduled(1);
        int result = scheduleMapper.updateByPrimaryKeySelective(schedule);
        return result > 0;
    }

    public List<Schedule> searchPartner(Long userId,
                                        Date beginDate,
                                        Date endDate,
                                        String tags) {
        // TODO 查询用户信息
        User user;
        ScheduleExample example = new ScheduleExample();

        ScheduleExample.Criteria criteria = example.createCriteria();
        if(beginDate != null && endDate != null) {
            criteria.andCreateDateBetween(beginDate, endDate);
        }
        else if(beginDate != null) {
            criteria.andCreateDateGreaterThanOrEqualTo(beginDate);
        }
        List<Schedule> list = scheduleMapper.selectByExample(example);
        return list;
    }

}
