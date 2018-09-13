package me.victoriest.photio.test;

import me.victoriest.photio.ProducerContractApp;
import me.victoriest.photio.service.ContractService;
import me.victoriest.photio.util.SnowFlakeIdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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

    }

}
