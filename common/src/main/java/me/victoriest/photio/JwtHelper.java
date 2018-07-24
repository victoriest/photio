package me.victoriest.photio;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

/**
 *
 * @author VictoriEST
 * @date 2018/3/28
 * spring-cloud-step-by-step
 */
public class JwtHelper {

    /**
     * 由字符串生成加密key
     * @return
     */
    public SecretKey generalKey(){
        String stringKey = "estestest";
        byte[] encodedKey = Base64.getDecoder().decode(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static Claims parseToken(String jsonWebToken, String base64Security) {
        try {

            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String createToken(String account, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("23u90dsfjoia;9u0wrejmtiorejmwpotgju9043[iu530jm4rfejdsigo'jmdsiug9ir0ew[p5uite[0t");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //判断用户名密码是否合法，合法否在进行token生成
        // 令牌有效期30天
        LocalDate dt = LocalDate.now().plusDays(30);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = dt.atStartOfDay().atZone(zone).toInstant();
        Date expiration = Date.from(instant);

        // 生成令牌,参数可以自行组织
        //.setHeaderParam("alg", "HS256")
        String accessToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("accountId", account)
                .claim("expTime", expiration)
                .signWith(signatureAlgorithm, signingKey)
                .setExpiration(expiration)
                .setNotBefore(new Date()).compact();

        return accessToken;
    }
}
