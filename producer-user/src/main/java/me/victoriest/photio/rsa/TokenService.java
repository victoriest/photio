package me.victoriest.photio.rsa;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.victoriest.photio.cache.CacheKey;
import me.victoriest.photio.cache.RedisCache;
import me.victoriest.photio.config.TokenConfig;
import me.victoriest.photio.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * 用户Token
 *
 * @author VictoriEST
 */
@Service
public class TokenService {

    @Autowired
    private TokenConfig tokenSettings;

    @Autowired
    private RedisCache redisCache;

    /**
     * JWT方式生成token
     *
     * @param account
     * @return
     */
    public String createJWTToken(String account) {
        //判断用户名密码是否合法，合法否在进行token生成
        // 令牌有效期30天
        LocalDate dt = LocalDate.now().plusDays(30);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = dt.atStartOfDay().atZone(zone).toInstant();
        Date expiration = Date.from(instant);

        // 生成令牌,参数可以自行组织
        String accessToken = Jwts.builder().setHeaderParam("alg", "HS256")
                .setHeaderParam("typ", "JWT")
                .claim("accountId", account)
                .claim("expTime", expiration)
                .signWith(SignatureAlgorithm.HS256,
                        "secret").compact();
        return accessToken;
    }

    /**
     * 生成Token
     *
     * @param user 当前登录成功的用户
     * @return 返回token相关信息
     */
    public Map<String, Object> createToken(User user) {

        // clear old token info in cache
        Optional<String> accountLastLoginToken = Optional.ofNullable(
                (String) redisCache.getValueOps().get(user.getAccount()));
        if (accountLastLoginToken.isPresent()) {
            String accountTokenKey = CacheKey.TOKEN_KEY_PREFIX + accountLastLoginToken.get();
            redisCache.getValueOps().getOperations().delete(accountTokenKey);
            redisCache.getValueOps().getOperations().delete(user.getAccount());
        }

        // 生成一个token
        String token = UUID.randomUUID().toString();

        // 当前时间
        Date now = new Date();

        // 过期时间
        long timeOutSeconds = tokenSettings.getTokenExpirseSecsonds();
        Date expireTime = new Date(now.getTime() + timeOutSeconds * 1000);

        // token信息
        String key = CacheKey.TOKEN_KEY_PREFIX + token;
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("id", user.getId());
        map.put("account", user.getAccount());
        map.put("expireTime", expireTime.getTime());

        // 将token信息写入到redis
        redisCache.getValueOps().setWithExpire(key, map, tokenSettings.getTokenExpirseSecsonds());

        // 将account对应的token保存起来，实现唯一设备登录功能
        redisCache.getValueOps().setWithExpire(user.getAccount(), token, tokenSettings.getTokenExpirseSecsonds());


        return map;
    }


    /**
     * 根据传递过来的token获取redis中保存的token相关信息
     *
     * @param token
     * @return
     */
    public Optional<Map<String, Object>> getTokenInfo(String token) {
        String key = CacheKey.TOKEN_KEY_PREFIX + token;
        return Optional.ofNullable((Map<String, Object>) redisCache.getValueOps().get(key));
    }

    /**
     * 获取用户最后一次登录获取的token
     *
     * @return
     */
    public Optional<String> getUserLastLoginToken(String account) {
        return Optional.ofNullable((String) redisCache.getValueOps().get(account));
    }

    /**
     * 验证token是否合法
     *
     * @param token
     * @return
     */
    public boolean verifyToken(String token) {
        Optional<Map<String, Object>> tokenInfo = getTokenInfo(token);
        if (!tokenInfo.isPresent()) {
            return false;
        }
        Map<String, Object> map = tokenInfo.get();
        if (map.containsKey("account")) {
            // 判断账号是否被踢下线
            String account = (String) map.get("account");
            Optional<String> userLastLoginToken = getUserLastLoginToken(account);
            if (userLastLoginToken.isPresent() && !userLastLoginToken.get().equals(token)) {
                // 账号在其它设备登录用户被踢下线
                return false;
            }
        }
        Date now = new Date();
        if (!tokenInfo.get().containsKey("expireTime")) {
            return false;
        }
        Date expireTime = (Date) tokenInfo.get().get("expireTime");

        return expireTime.after(now);
    }

    /**
     * 账号冻结后，让账号登录状态失效
     */
    public void removeUserLastLoginToken(String account) {
        Optional<String> userLastLoginToken = getUserLastLoginToken(account);

        if (userLastLoginToken.isPresent()) {
            String key = CacheKey.TOKEN_KEY_PREFIX + userLastLoginToken.get();
            redisCache.getValueOps().getOperations().delete(key);
            redisCache.getValueOps().getOperations().delete(account);
        }
    }

}
