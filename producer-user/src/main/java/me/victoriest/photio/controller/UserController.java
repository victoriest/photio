package me.victoriest.photio.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.victoriest.photio.annotation.IgnoreAuthorize;
import me.victoriest.photio.annotation.LoginUser;
import me.victoriest.photio.model.dto.EncryptedRequestDto;
import me.victoriest.photio.model.dto.LoginInfoDto;
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
    @HystrixCommand
    public ResponseDto registry(@RequestParam String account,
                                @RequestParam String name,
                                @RequestParam Integer userType) {
        Optional<Long> opt= userService.registryUser(account, name, userType);
        if(opt.isPresent()) {
            return new ResponseDto<>().success(opt.get());
        }
        throw new BusinessLogicException(Messages.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "获取rsa公钥")
    @GetMapping(value = "/getRsaKey")
    @HystrixCommand
    public ResponseDto getRsaKey() {
        try {
            RSAPubKey key = rsaKeyService.getKey();
            return new ResponseDto<>().success(key);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Call getRsaKey method failed. Message:" + e.getMessage());
            throw new BusinessLogicException(Messages.NO_SUCH_ALGORITHM_EXCEPTION);
        }
    }

    @ApiOperation(value = "测试用, 返回rsa加密后的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rsaKeyId", value = "获取的rsa公钥id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "account", value = "用户账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "pwd", value = "密码", required = true, dataType = "String")
    })
    @GetMapping(value = "/testRsa")
    public ResponseDto generateEncryptedLoginData(@RequestParam String rsaKeyId,
                                                  @RequestParam String account,
                                                  @RequestParam String pwd) {
        JSONObject json = new JSONObject();
        json.put("username", account);
        json.put("password", pwd);
        String result = "";
        try {
            Optional<String> opt = rsaKeyService.encrypt(rsaKeyId, json.toJSONString());
            if(opt.isPresent()){
                result = opt.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseDto<>().success(result);
    }

    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params", value = "加密后的数据", required = true, dataType = "EncryptedRequestDto")
    })
    @PostMapping(value = "/login")
    @HystrixCommand
    public ResponseDto login(@RequestBody EncryptedRequestDto params) {

        Optional<String> jsonString;
        try {
            /*// 测试发现前台使用encodeURIComponent()方法进行编码，传递到后台已经自动转码了，若未转码，请使用下面的方法进行转码操作
            pwd = StringEscapeUtils.escapeJava(pwd);*/
            jsonString = rsaKeyService.decrypt(params.getRsaKeyId(), params.getSecretText());
        } catch (Exception e) {
            throw new BusinessLogicException(Messages.LOGIN_USER_PWD_RSA_DECRYPT_FAILED);
        }

        if (jsonString.isPresent()) {

            LoginInfoDto dto = JSON.parseObject(jsonString.get(), LoginInfoDto.class);

            Optional<User> optUser = userService.getByAccount(dto.getUsername());
            if (!optUser.isPresent()) {
                throw new BusinessLogicException(Messages.LOGIN_UER_NOT_FOUND);
            }
            User user = optUser.get();

            String account = user.getAccount();

            // 密码不正确
            if (!Md5Utils.getMD5(dto.getPassword(), account).equals(user.getPwd())) {
                throw new BusinessLogicException(Messages.LOGIN_UER_NOT_FOUND);
            }

            // 生成token
            Map<String, Object> tokenInfo = tokenService.createToken(user);

            return new ResponseDto<>().success(tokenInfo);
        } else {
            // 解密用户密码失败
            throw new BusinessLogicException(Messages.LOGIN_USER_PWD_RSA_DECRYPT_FAILED);
        }
    }

    @ApiOperation(value = "验证token是否有效")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "待验证的token", required = true, dataType = "String")
    })
    @GetMapping(value = "/verifyToken")
    @HystrixCommand
    public ResponseDto verifyToken(@RequestParam String token) {
        if (tokenService.verifyToken(token)) {
            return new ResponseDto().success();
        }
        throw new BusinessLogicException(Messages.TOKEN_INVALID);
    }

    @PostMapping("/user/change-pwd")
    @ApiOperation(value = "更新用户密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rsaKeyId", value = "获取的rsa公钥id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "oldPwd", value = "rsa加密后的旧密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPwd", value = "rsa加密后的新密码", required = true, dataType = "String", paramType = "query")
    })
    @HystrixCommand
    public ResponseDto updatePassword(@ApiIgnore @LoginUser User user,
                                      @RequestParam String rsaKeyId,
                                      @RequestParam String oldPwd,
                                      @RequestParam String newPwd) {
        boolean result = userService.changePassword(user.getId(), rsaKeyId, oldPwd, newPwd);
        return result ? new ResponseDto().success() : new ResponseDto().fail("");
    }


    @PostMapping("/user")
    @ApiOperation(value = "更新用户名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "name", required = true, dataType = "String", paramType = "query")
    })
    @HystrixCommand
    public ResponseDto updateUser(@LoginUser @ApiIgnore User user,
                                  @RequestParam String name) {
        boolean result = userService.updateUser(user.getId(), name);
        return result ? new ResponseDto().success() : new ResponseDto().fail("");
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "query")
    @HystrixCommand
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
    @HystrixCommand
    public ResponseDto<User> getById(@PathVariable Long id) {
        Optional<User> optUser = userService.getUserByUserId(id);
        if (optUser.isPresent()) {
            return new ResponseDto<User>().success(optUser.get());
        }
        return new ResponseDto<User>().fail("");
    }

    @ApiOperation(value = "获取指定account的用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "用户account", required = true, dataType = "String")
    })
    @GetMapping("/user/account/{account}")
    @HystrixCommand
    public ResponseDto<User> getByAccount(@PathVariable String account) {
        Optional<User> optUser = userService.getByAccount(account);
        if (optUser.isPresent()) {
            return new ResponseDto<User>().success(optUser.get());
        }
        return new ResponseDto<User>().fail("");
    }

}
