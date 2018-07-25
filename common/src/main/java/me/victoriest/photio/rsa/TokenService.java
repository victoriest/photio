package me.victoriest.photio.rsa;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.victoriest.photio.cache.CacheKey;
import me.victoriest.photio.config.TokenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * 用户Token
 * @author VictoriEST
 */
@Service
public class TokenService {

    @Autowired
    private TokenConfig tokenSettings;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * JWT方式生成token
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
     * change log:1.1.1版本将原来使用的token和account的绑定改为和userId绑定，因为account值在修改手机号后会改变
     *
     * @param account 当前登录成功的用户
     * @return 返回token相关信息
     */
    public Map<String, Object> createToken(String account) {
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
        map.put("account", account);
        map.put("expireTime", String.valueOf(expireTime.getTime()));

        // 将token信息写入到redis
        redisTemplate.opsForValue().set(key, map);

        // 将account对应的token保存起来，实现唯一设备登录功能
        redisTemplate.opsForValue().set(account, token);

        // token绑定关系由account变为userId后，用户登录成功后，解除用户之前account和token的绑定关系（清理缓存）
        Optional<String> accountLastLoginToken = Optional.ofNullable((String) redisTemplate.opsForValue().get(account));
        if (accountLastLoginToken.isPresent()) {
            String accountTokenKey = CacheKey.TOKEN_KEY_PREFIX + accountLastLoginToken.get();
            redisTemplate.delete(accountTokenKey);
            redisTemplate.delete(account);
        }

        return map;
    }

    private void writeTokenIntoCache() {

    }


    /**
     * 根据传递过来的token获取redis中保存的token相关信息
     *
     * @param token
     * @return
     */
    public Optional<Map<String, Object>> getTokenInfo(String token) {
        String key = CacheKey.TOKEN_KEY_PREFIX + token;
        return Optional.ofNullable((Map<String, Object>) redisTemplate.opsForValue().get(key));
    }

    /**
     * 获取用户最后一次登录获取的token
     * changelog: 1.1.1 版本参数由account 修改为 userId,方法名由getAccountLastLoginToken修改为getUserLastLoginToken
     *
     * @param userId 用户id(主键)
     * @return
     */
    public Optional<String> getUserLastLoginToken(Long userId) {
        return Optional.ofNullable((String) redisTemplate.opsForValue().get(userId.toString()));
    }

    /**
     * 账号冻结后，让账号登录状态失效
     * changelog: 1.1.1 版本参数由account 修改为 userId,方法名由removeAccountLastLoginToken修改为removeUserLastLoginToken
     *
     * @param userId 用户id(主键)
     */
    public void removeUserLastLoginToken(Long userId) {
        Optional<String> userLastLoginToken = getUserLastLoginToken(userId);

        if (userLastLoginToken.isPresent()) {
            String key = CacheKey.TOKEN_KEY_PREFIX + userLastLoginToken.get();
            redisTemplate.delete(key);
            redisTemplate.delete(userId.toString());
        }
    }

}
