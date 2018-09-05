package me.victoriest.photio.model.rsa;

import java.io.Serializable;

/**
 * @author VictoriEST
 */
public class EncryptedRequest implements Serializable {
    private static final long serialVersionUID = 7341239407950545625L;

    private String keyId;

    private String secretText;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getSecretText() {
        return secretText;
    }

    public void setSecretText(String secretText) {
        this.secretText = secretText;
    }
}
