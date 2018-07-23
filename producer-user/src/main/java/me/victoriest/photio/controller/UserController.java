package me.victoriest.photio.controller;

import me.victoriest.photio.annotation.IgnoreAuthorize;
import me.victoriest.photio.model.dto.ResponseDto;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 *
 * @author VictoriEST
 * @date 2018/3/23
 * spring-cloud-step-by-step
 */
@RestController("userController")
@RequestMapping(value = "/v1/api")
public class UserController {


    @IgnoreAuthorize
    @PutMapping("/registry")
    public ResponseDto registry(@RequestParam String account,
                                @RequestParam String name,
                                @RequestParam String userType) {
        throw new NotImplementedException();
//        Date now = new Date();
//        User user = new User();
//        user.setAccount(account);
//        user.setUname(name);
//        user.setPwd(Md5Utils.getMD5(Constants.DEFAULT_PASSWORD, account));
//        Integer ut = Integer.parseInt(userType);
//        if(!ut.equals(0) && !ut.equals(1)) {
//            return new ResponseDto().fail("");
//        }
//        user.setUserType(ut);
//        user.setCreateTime(now);
//        user.setUpdateTime(now);
//        int result = userMapper.insert(user);

//        return new ResponseDto().success(result + "");
    }

    public ResponseDto login() {
        throw new NotImplementedException();
    }

    // CHANGE PWD

    // UPDATE USER

    // LOGOUT

//    @ApiOperation(value = "获取指定id的用户信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer")
//    })
//    @GetMapping("/user/{id}")
//    public ResponseDto<User> getById(@PathVariable Integer id) {
//        User user = userMapper.selectByPrimaryKey(id);
//        return new ResponseDto<User>().success(user);
//    }
//
//    @ApiOperation(value = "获取指定account的用户信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "account", value = "用户account", required = true, dataType = "String")
//    })
//    @GetMapping("/user/account/{account}")
//    public ResponseDto<User> getByAccount(@PathVariable String account) {
//        User user = userMapper.selectByAccount(account);
//        return new ResponseDto<User>().success(user);
//    }
//
//    @ApiOperation(value = "更新指定id用户花费")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Integer"),
//            @ApiImplicitParam(name = "cost", value = "cost", required = true, dataType = "Integer")
//    })
//    @PostMapping("/user/cost/{id}")
//    public ResponseDto costMoney(@PathVariable Integer id, @RequestParam Integer cost) {
//        User user = userMapper.selectByPrimaryKey(id);
//        int result = userMapper.updateCost(id, user.getMoney(), user.getMoney() - cost);
//        return new ResponseDto().success(result);
//    }

}
