package me.victoriest.photio.test;

import me.victoriest.photio.ProducerContractApp;
import me.victoriest.photio.service.ContractService;
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

import java.util.Optional;

/**
 * Created by VictoriEST on 2018/9/13.
 * photio
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProducerContractApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ContractServiceTest {

    @Autowired
    private SnowFlakeIdGenerator snowFlakeIdGenerator;

    @Autowired
    private ContractService contractService;

    @Test
    public void test(){
        Long inviterUserId = snowFlakeIdGenerator.nextId();
        Long targetUserId = 219530994259202047L;
        Long scheduleId = snowFlakeIdGenerator.nextId();
        String message = RandomStringUtils.randomAlphanumeric(5);
        String token = RandomStringUtils.randomAlphanumeric(5);

        // create a invitation
        Optional<Long> optInvitationId = contractService.invite(inviterUserId, scheduleId, targetUserId);
        Assert.assertTrue(optInvitationId.isPresent());

        // update state of invitation
        Assert.assertFalse(contractService.updateInvitationState(token, inviterUserId, optInvitationId.get(), 1));

        Optional<Long> optEvaluateId = contractService.evaluate(inviterUserId, targetUserId, 5, message);
        Assert.assertTrue(optEvaluateId.isPresent());

    }

}
