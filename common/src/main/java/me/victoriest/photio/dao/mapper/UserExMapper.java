package me.victoriest.photio.dao.mapper;


import me.victoriest.photio.model.entity.User;

/**
 *
 * @author VictoriEST
 * @date 2018/7/24
 * photio
 */
public interface UserExMapper {

    User selectByAccount(String account);

}
