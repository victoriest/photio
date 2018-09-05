package me.victoriest.photio.dto;

import java.io.Serializable;

/**
 *
 * @author VictoriEST
 * @date 2018/9/5
 * photio
 */
public class EncryptedRequestDto implements Serializable {
    private static final long serialVersionUID = 7341239407950545625L;

    private String rsaKeyId;

    private String secretText;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRsaKeyId() {
        return rsaKeyId;
    }

    public void setRsaKeyId(String rsaKeyId) {
        this.rsaKeyId = rsaKeyId;
    }

    public String getSecretText() {
        return secretText;
    }

    public void setSecretText(String secretText) {
        this.secretText = secretText;
    }
}
