package me.victoriest.photio.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.victoriest.photio.annotation.IgnoreAuthorize;
import me.victoriest.photio.annotation.LoginUser;
import me.victoriest.photio.exception.BusinessLogicException;
import me.victoriest.photio.message.Messages;
import me.victoriest.photio.model.dto.ResponseDto;
import me.victoriest.photio.model.entity.User;
import me.victoriest.photio.model.rsa.RSAPubKey;
import me.victoriest.photio.rsa.RsaKeyService;
import me.victoriest.photio.rsa.TokenService;
import me.victoriest.photio.service.UserService;
import me.victoriest.photio.util.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.security.NoSuchAlgorithmException;
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
    private UserService userService;

    @ApiOperation(value = "注册用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userType", value = "用户类型", required = true, dataType = "int")
    })
    @PutMapping("/registry")
    public ResponseDto registry(@RequestParam String account,
                                @RequestParam String name,
                                @RequestParam Integer userType) {
        Long id = userService.registryUser(account, name, userType).get();
        return new ResponseDto<>().success(id);
    }

    @ApiOperation(value = "获取rsa公钥")
    @GetMapping(value = "/getRsaKey")
    public ResponseDto getRsaKey() {
        try {
            RSAPubKey key = rsaKeyService.getKey();
            return new ResponseDto<>().success(key);
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

            Optional<User> optUser = userService.getByAccount(account);
            if(!optUser.isPresent()) {
                throw new BusinessLogicException(Messages.LOGIN_UER_NOT_FOUND);
            }
            User user = optUser.get();

            account = user.getAccount();

            // 密码不正确
            if (!Md5Utils.getMD5(userPwd.get(), account).equals(user.getPwd())) {
                throw new BusinessLogicException(Messages.LOGIN_UER_NOT_FOUND);
            }

            // 生成token
            Map<String, Object> tokenInfo = tokenService.createToken(account);

            return new ResponseDto<>().success(tokenInfo);
        } else {
            // 解密用户密码失败
            throw new BusinessLogicException(Messages.LOGIN_USER_PWD_RSA_DECRYPT_FAILED);
        }
    }

    @PostMapping("/user/{id}/change-pwd")
    @ApiOperation(value = "更新用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rsaKeyId", value = "获取的rsa公钥id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "oldPwd", value = "rsa加密后的旧密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPwd", value = "rsa加密后的新密码", required = true, dataType = "String", paramType = "query")
    })
    public ResponseDto updatePassword(@ApiIgnore @LoginUser User user,
                                      @RequestParam String rsaKeyId,
                                      @RequestParam String oldPwd,
                                      @RequestParam String newPwd) {
        boolean result = userService.changePassword(user.getId(), rsaKeyId, oldPwd, newPwd);
        return result ? new ResponseDto().success() : new ResponseDto().fail("");
    }

    @PostMapping("/user/{id}")
    @ApiOperation(value = "更新用户名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "name", required = true, dataType = "String", paramType = "query")
    })
    public ResponseDto updateUser(@ApiIgnore @LoginUser User user,
                                  @RequestParam String name) {
        boolean result = userService.updateUser(user.getId(), name);
        return result ? new ResponseDto().success() : new ResponseDto().fail("");
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "query")
    public ResponseDto logout(@ApiIgnore @LoginUser User user) {
        tokenService.removeUserLastLoginToken(user.getAccount());
        return new ResponseDto().success();
    }

    @IgnoreAuthorize
    @ApiOperation(value = "获取指定id的用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int")
    })
    @GetMapping("/user/{id}")
    public ResponseDto<User> getById(@PathVariable Long id) {
        Optional<User> optUser = userService.getUserByUserId(id);
        if(optUser.isPresent()) {
            return new ResponseDto<User>().success(optUser.get());
        }
        return new ResponseDto<User>().fail("");
    }

    @ApiOperation(value = "获取指定account的用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户account", required = true, dataType = "String")
    })
    @GetMapping("/user/account/{account}")
    public ResponseDto<User> getByAccount(@PathVariable String account) {
        Optional<User> optUser = userService.getByAccount(account);
        if(optUser.isPresent()) {
            return new ResponseDto<User>().success(optUser.get());
        }
        return new ResponseDto<User>().fail("");
    }

}
