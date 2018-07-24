package me.victoriest.photio.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.victoriest.photio.Constants;
import me.victoriest.photio.annotation.IgnoreAuthorize;
import me.victoriest.photio.annotation.LoginUser;
import me.victoriest.photio.dao.mapper.UserExMapper;
import me.victoriest.photio.dao.mapper.source.UserMapper;
import me.victoriest.photio.exception.BusinessLogicException;
import me.victoriest.photio.message.Messages;
import me.victoriest.photio.model.dto.ResponseDto;
import me.victoriest.photio.model.entity.User;
import me.victoriest.photio.model.rsa.RSAPubKey;
import me.victoriest.photio.rsa.RsaKeyService;
import me.victoriest.photio.rsa.TokenService;
import me.victoriest.photio.util.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;


/**
 *
 * @author VictoriEST
 * @date 2018/3/23
 * spring-cloud-step-by-step
 */
@RestController("userController")
@RequestMapping(value = "/v1/api")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RsaKeyService rsaKeyService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExMapper userExMapper;

    @ApiOperation(value = "注册用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userType", value = "用户类型", required = true, dataType = "Integer")
    })
    @IgnoreAuthorize
    @PutMapping("/registry")
    public ResponseDto registry(@RequestParam String account,
                                @RequestParam String name,
                                @RequestParam String userType) {
        Date now = new Date();
        User user = new User();
        user.setAccount(account);
        user.setUserName(name);
        user.setPwd(Md5Utils.getMD5(Constants.DEFAULT_PASSWORD, account));
        Integer ut = Integer.parseInt(userType);
        if(!ut.equals(0) && !ut.equals(1)) {
            return new ResponseDto().fail("");
        }
        user.setType(ut);
        user.setCreateDate(now);
        user.setUpdateDate(now);
        int result = userMapper.insert(user);

        return new ResponseDto().success(result + "");
    }

    @ApiOperation(value = "获取rsa公钥")
    @IgnoreAuthorize
    @GetMapping(value = "/getRsaKey")
    public ResponseDto getRsaKey() {
        try {
            RSAPubKey key = rsaKeyService.getKey();
            return new ResponseDto().success(key);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Call getRsaKey method failed. Message:" + e.getMessage());
            throw new BusinessLogicException(Messages.NO_SUCH_ALGORITHM_EXCEPTION);
        }
    }

    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rsaKeyId", value = "获取的rsa公钥id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "account", value = "用户账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pwd", value = "rsa加密后的密码", required = true, dataType = "String")
    })
    @IgnoreAuthorize
    @PostMapping(value = "/login")
    public ResponseDto login(@RequestParam String rsaKeyId,
                             @RequestParam(required = false) String account,
                             @RequestParam String pwd) {

        Optional<String> userPwd;
        try {
            /*// 测试发现前台使用encodeURIComponent()方法进行编码，传递到后台已经自动转码了，若未转码，请使用下面的方法进行转码操作
            pwd = StringEscapeUtils.escapeJava(pwd);*/
            userPwd = rsaKeyService.decrypt(rsaKeyId, pwd);
        } catch (Exception e) {
            throw new BusinessLogicException(Messages.LOGIN_USER_PWD_RSA_DECRYPT_FAILED);
        }

        if (userPwd.isPresent()) {

            User user = userExMapper.selectByAccount(account);

            account = user.getAccount();

            // 密码不正确
            if (!Md5Utils.getMD5(userPwd.get(), account).equals(user.getPwd())) {
                throw new BusinessLogicException(Messages.LOGIN_UER_NOT_FOUND);
            }

            // 生成token
            Map<String, Object> tokenInfo = tokenService.createToken(account);

            return new ResponseDto().success(tokenInfo);
        } else {
            // 解密用户密码失败
            throw new BusinessLogicException(Messages.LOGIN_USER_PWD_RSA_DECRYPT_FAILED);
        }
    }
    // TODO CHANGE PWD

    // TODO UPDATE USER

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "query")
    public ResponseDto logout(@ApiIgnore @LoginUser User user) {
        tokenService.removeUserLastLoginToken(user.getId());
        return new ResponseDto().success();
    }

    @ApiOperation(value = "获取指定id的用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer")
    })
    @GetMapping("/user/{id}")
    public ResponseDto<User> getById(@PathVariable Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        return new ResponseDto<User>().success(user);
    }

    @ApiOperation(value = "获取指定account的用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户account", required = true, dataType = "String")
    })
    @GetMapping("/user/account/{account}")
    public ResponseDto<User> getByAccount(@PathVariable String account) {
        User user = userExMapper.selectByAccount(account);
        return new ResponseDto<User>().success(user);
    }

}
