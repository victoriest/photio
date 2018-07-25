package me.victoriest.photio.service;

import me.victoriest.photio.Constants;
import me.victoriest.photio.dao.mapper.UserExMapper;
import me.victoriest.photio.dao.mapper.source.UserMapper;
import me.victoriest.photio.exception.BusinessLogicException;
import me.victoriest.photio.message.Messages;
import me.victoriest.photio.model.entity.User;
import me.victoriest.photio.rsa.RsaKeyService;
import me.victoriest.photio.util.Md5Utils;
import me.victoriest.photio.util.SnowFlakeIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author VictoriEST
 * @date 2018/7/25
 * photio
 */
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExMapper userExMapper;

    @Autowired
    private SnowFlakeIdGenerator snowFlakeIdGenerator;

    @Autowired
    private RsaKeyService rsaKeyService;

    public Optional<Long> registryUser(String account,
                                      String name,
                                      Integer userType) {
        Long id = snowFlakeIdGenerator.nextId();
        Date now = new Date();
        User user = new User();
        user.setId(id);
        user.setAccount(account);
        user.setPhone(account);
        user.setUserName(name);
        user.setScore(0L);
        user.setState(1);
        user.setPwd(Md5Utils.getMD5(Constants.DEFAULT_PASSWORD, account));
        if(!userType.equals(0) && !userType.equals(1)) {
            return Optional.empty();
        }
        user.setType(userType);
        user.setCreateDate(now);
        user.setUpdateDate(now);
        userMapper.insert(user);

        return Optional.of(id);
    }

    public Optional<User> getUserByUserId(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        return Optional.ofNullable(user);
    }

    public Optional<User> getByAccount(String account) {
        User user = userExMapper.selectByAccount(account);
        return Optional.ofNullable(user);
    }

    public boolean updateUser(Long id, String name) {
        User preUpdated = new User();
        preUpdated.setId(id);
        preUpdated.setUserName(name);
        int result = userMapper.updateByPrimaryKeySelective(preUpdated);
        return result > 0;
    }

    public boolean changePassword(Long id, String rsaKeyId, String oldPassword, String newPassword) {
        Optional<String> decryptOldPwd;
        Optional<String> decryptNewPwd;
        User user;
        try {
            decryptOldPwd = rsaKeyService.decrypt(rsaKeyId, oldPassword);
            decryptNewPwd = rsaKeyService.decrypt(rsaKeyId, newPassword);
        } catch (Exception e) {
            // 解密用户密码失败
            throw new BusinessLogicException(Messages.LOGIN_USER_PWD_RSA_DECRYPT_FAILED);
        }

        if (decryptOldPwd.isPresent()) {
            Optional<User> optionalUser = getUserByUserId(id);
            if(!optionalUser.isPresent()) {
                throw new BusinessLogicException(Messages.LOGIN_UER_NOT_FOUND);
            }
            user = optionalUser.get();
            // 旧密码不正确
            if (!Md5Utils.getMD5(decryptOldPwd.get(), user.getAccount())
                    .equals(user.getPwd())) {
                throw new BusinessLogicException(Messages.LOGIN_USER_PWD_RSA_DECRYPT_FAILED);
            }
        } else {
            throw new BusinessLogicException(Messages.LOGIN_USER_PWD_RSA_DECRYPT_FAILED);
        }

        if (decryptNewPwd.isPresent()) {

            User preUpdated = new User();
            preUpdated.setId(id);
            preUpdated.setPwd(Md5Utils.getMD5(decryptNewPwd.get(), user.getAccount()));
            int result = userMapper.updateByPrimaryKeySelective(preUpdated);
            return result > 0;
        } else {
            throw new BusinessLogicException(Messages.LOGIN_USER_PWD_RSA_DECRYPT_FAILED);
        }
    }

}
