package me.victoriest.photio.rsa;


import com.alibaba.fastjson.JSON;
import me.victoriest.photio.model.rsa.EncryptedRequest;
import me.victoriest.photio.model.rsa.RSAPrivateKey;
import me.victoriest.photio.model.rsa.RSAPubKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author VictoriEST
 */
@Service
public class RsaKeyService {

    @Value("${token.rsa-encrypt-enabled}")
    private boolean rsaEncryptEnabled;


    private ConcurrentHashMap<String, RSAPrivateKey> keyStore = new ConcurrentHashMap<>();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private final long redisTimeOutSeconds = 900;
    private final String KEY_PREFIX = "RSA_KEY_ID_";

    public RSAPubKey getKey() throws NoSuchAlgorithmException {
        RSAPrivateKey privateKey = RSA.keyRSA();
        keyStore.put(privateKey.getId(), privateKey);
        try {
            // 同时写入redis缓存(避免这种特殊情况出现异常：用户获取到publicKey后，服务器突然重启了)
            redisTemplate.opsForValue().set(KEY_PREFIX + privateKey.getId(), privateKey, redisTimeOutSeconds, TimeUnit.SECONDS);
        } catch (JedisConnectionException ex) {
            // Could not get a resource from the pool
            // 捕获redis连接不上异常，写入redis失败，不影响业务流程
        }
        return privateKey.transformToPubKey();
    }

    /**
     * JSON based decrypt method
     */
    public <T> Optional<T> decrypt(EncryptedRequest request, Class<T> clazz) throws Exception {
        RSAPrivateKey key = keyStore.get(request.getKeyId());
        if (key == null) {
            // 从缓存中获取
            key = (RSAPrivateKey) redisTemplate.opsForValue().get(KEY_PREFIX + request.getKeyId());
        }
        if (key != null) {
            PrivateKey privateKey = RSA.generateRSAPrivateKey(key);
            return Optional.ofNullable(JSON.parseObject(RSA.decrypt(privateKey, request.getSecretText()), clazz));
        }
        return Optional.empty();
    }

    /**
     * 解密pwd
     * @param keyId
     * @param secretText
     * @return
     * @throws Exception
     */
    public Optional<String> decrypt(String keyId, String secretText) throws Exception {

        // 压测环境密码不加密
        if (!rsaEncryptEnabled) {
            return Optional.ofNullable(secretText);
        }

        RSAPrivateKey key = keyStore.get(keyId);
        if (key == null) {
            // 从缓存中获取
            key = (RSAPrivateKey) redisTemplate.opsForValue().get(KEY_PREFIX + keyId);
        }
        if (key != null) {
            PrivateKey privateKey = RSA.generateRSAPrivateKey(key);
            return Optional.ofNullable(RSA.decrypt(privateKey, secretText));
        }
        return Optional.empty();
    }

    /**
     * 删除指定的键值对
     * 双十一促销活动中，为了安全性，保证每一对密钥对只能请求一次
     * @param keyId
     */
    public void removeKey(String keyId) {
        if (null != keyStore) {
            if (keyStore.containsKey(keyId)) {
                keyStore.remove(keyId);
            }
        }

        // 缓存也清除
        RSAPrivateKey cacheObj = (RSAPrivateKey) redisTemplate.opsForValue().get(KEY_PREFIX + keyId);
        if (null != cacheObj) {
            redisTemplate.delete(KEY_PREFIX + keyId);
        }
    }

    /**
     * JSON based decrypt method
     */
    public Optional<String> decrypt(EncryptedRequest request) throws Exception {
        RSAPrivateKey key = keyStore.get(request.getKeyId());
        if (key != null) {
            PrivateKey privateKey = RSA.generateRSAPrivateKey(key);
            return Optional.ofNullable(RSA.decrypt(privateKey, request.getSecretText()));
        }
        return Optional.empty();
    }

    public boolean isRsaEncryptEnabled() {
        return rsaEncryptEnabled;
    }

    public void setRsaEncryptEnabled(boolean rsaEncryptEnabled) {
        this.rsaEncryptEnabled = rsaEncryptEnabled;
    }
}
