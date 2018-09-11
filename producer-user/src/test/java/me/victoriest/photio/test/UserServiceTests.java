package me.victoriest.photio.test;

import me.victoriest.photio.ProducerUserApp;
import me.victoriest.photio.model.entity.User;
import me.victoriest.photio.service.UserService;
import me.victoriest.photio.util.SnowFlakeIdGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by VictoriEST on 2018/9/11.
 * photio
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProducerUserApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("dev")
@Transactional
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private SnowFlakeIdGenerator snowFlakeIdGenerator;

    @Test
    public void userCRUDTest() {
        // registry user
        String testAccount = RandomStringUtils.randomAlphanumeric(10);
        String testName = RandomStringUtils.randomAlphanumeric(10);
        Optional<Long> optId = userService.registryUser(testAccount, testName, 0);
        Assert.assertNotEquals(Optional.empty(), optId);

        Long fakeId = snowFlakeIdGenerator.nextId();
        // get by id
        Optional<User> user = userService.getUserByUserId(fakeId);
        Assert.assertFalse(user.isPresent());

        user = userService.getUserByUserId(optId.get());
        Assert.assertNotNull(user.get());
        Assert.assertEquals(user.get().getAccount(), testAccount);
        Assert.assertEquals(user.get().getUserName(), testName);

        // get by account
        user = userService.getByAccount(testAccount);
        Assert.assertNotNull(user.get());
        Assert.assertEquals(user.get().getAccount(), testAccount);
        Assert.assertEquals(user.get().getUserName(), testName);

        String updateName = RandomStringUtils.randomAlphanumeric(10);
        // update
        Assert.assertTrue(userService.updateUser(optId.get(), updateName));
        user = userService.getUserByUserId(optId.get());
        Assert.assertNotNull(user.get());
        Assert.assertEquals(user.get().getAccount(), testAccount);
        Assert.assertEquals(user.get().getUserName(), updateName);

        // change password
    }

}
