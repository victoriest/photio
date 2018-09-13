package me.victoriest.photio.test;

import me.victoriest.photio.ProducerScheduleApp;
import me.victoriest.photio.model.entity.Schedule;
import me.victoriest.photio.service.ScheduleService;
import me.victoriest.photio.util.SnowFlakeIdGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by VictoriEST on 2018/9/13.
 * photio
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProducerScheduleApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private SnowFlakeIdGenerator snowFlakeIdGenerator;

    @Test
    public void test() {
        Long testUserId1 = snowFlakeIdGenerator.nextId();
        Long testUserId2 = snowFlakeIdGenerator.nextId();
        DateTime testDate1 = DateTime.now().withTimeAtStartOfDay();
        DateTime testDate2 = testDate1.plusDays(2).withTimeAtStartOfDay();
        String testTag1 = RandomStringUtils.randomAlphanumeric(5);
        String testTag2 = RandomStringUtils.randomAlphanumeric(5);

        // create a schedule
        Optional<Long> optScheduleId1 = scheduleService.createSchedule(testUserId1, testDate1.toDate(), testTag1);
        Assert.assertTrue(optScheduleId1.isPresent());

        // get the schedule
        Optional<Schedule> optSchedule1 = scheduleService.getSchedule(optScheduleId1.get());
        Assert.assertTrue(optSchedule1.isPresent());
        Schedule schedule1 = optSchedule1.get();
        Assert.assertEquals(testUserId1, schedule1.getUserId());
        Assert.assertEquals((Integer) 0, schedule1.getIsScheduled());
        Assert.assertEquals(testDate1.toDate(), schedule1.getFreeDate());
        Assert.assertEquals(testTag1, schedule1.getTags());

        // create a schedule for another date
        Optional<Long> optScheduleId2 = scheduleService.createSchedule(testUserId2, testDate2.toDate(), testTag2);
        Assert.assertTrue(optScheduleId2.isPresent());

        // search a schedule
        List<Schedule> result = scheduleService.searchPartner(testUserId1, testDate1.toDate(), null, "");
        Assert.assertEquals(2, result.size());

        result = scheduleService.searchPartner(testUserId1, testDate1.toDate(), testDate2.toDate(), "");
        Assert.assertEquals(2, result.size());

        result = scheduleService.searchPartner(testUserId1, testDate1.plusDays(1).toDate(), testDate2.toDate(), "");
        Assert.assertEquals(1, result.size());

        result = scheduleService.searchPartner(testUserId1, testDate2.toDate(), testDate2.toDate(), "");
        Assert.assertEquals(1, result.size());

        result = scheduleService.searchPartner(testUserId1, testDate2.plusDays(1).toDate(), null, "");
        Assert.assertEquals(0, result.size());

        // change the schedule state
        Assert.assertTrue(scheduleService.scheduledTheSchedule(testUserId1, optScheduleId1.get()));
        Assert.assertFalse(scheduleService.scheduledTheSchedule(testUserId1, optScheduleId2.get()));

        result = scheduleService.searchPartner(testUserId1, testDate1.toDate(), null, "");
        Assert.assertEquals(1, result.size());

    }
}
