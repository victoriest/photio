package me.victoriest.photio.dto;

import java.io.Serializable;

/**
 *
 * @author VictoriEST
 * @date 2018/9/5
 * photio
 */
public class LoginInfoDto implements Serializable {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
