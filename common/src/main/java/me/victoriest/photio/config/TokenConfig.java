package me.victoriest.photio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 移动端Token配置
 *
 * @author lihu
 * @date 2017/6/7
 */
@Configuration
public class TokenConfig {
    @Value("${token.expire-time-seconds}")
    private Integer tokenExpirseSecsonds;

    @Value("${token.enabled-simulate}")
    private boolean tokenSimulateEnabled;

    @Value("${token.unit-test-token-value}")
    private Integer unitTestTokenValue;

    public Integer getTokenExpirseSecsonds() {
        return tokenExpirseSecsonds;
    }

    public void setTokenExpirseSecsonds(Integer tokenExpirseSecsonds) {
        this.tokenExpirseSecsonds = tokenExpirseSecsonds;
    }

    public Integer getUnitTestTokenValue() {
        return unitTestTokenValue;
    }

    public void setUnitTestTokenValue(Integer unitTestTokenValue) {
        this.unitTestTokenValue = unitTestTokenValue;
    }

    public boolean isTokenSimulateEnabled() {
        return tokenSimulateEnabled;
    }

    public void setTokenSimulateEnabled(boolean tokenSimulateEnabled) {
        this.tokenSimulateEnabled = tokenSimulateEnabled;
    }
}
