package me.victoriest.photio.service;

import me.victoriest.photio.dao.mapper.source.ScheduleMapper;
import me.victoriest.photio.model.entity.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    private ScheduleMapper scheduleMapper;

    public Optional<Long> createSchedule(Long userId,
                                         Date date,
                                         Integer userType,
                                         String tags) {
        throw new NotImplementedException();
    }

    public List<Schedule> searchPartner(Long userId,
                                        Date beginDate,
                                        Date endDate,
                                        String tags) {
        throw new NotImplementedException();
    }

}
