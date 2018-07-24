package me.victoriest.photio.model.rsa;

import org.springframework.beans.BeanUtils;

/**
 * @author VictoriEST
 */
public class RSAPrivateKey extends RSAPubKey {

    private static final long serialVersionUID = -369498672622835471L;

    private String privateKey;

    public RSAPrivateKey(RSAPubKey pubKey, String privateKey) {
        super.setPubKey(pubKey.getPubKey());
        super.setEncryptType(pubKey.getEncryptType());
        super.setId(pubKey.getId());
        super.setTimestamp(pubKey.getTimestamp());
        super.setTtl(pubKey.getTtl());
        this.privateKey = privateKey;
    }

    public RSAPubKey transformToPubKey() {
        RSAPubKey pubKey = new RSAPubKey();
        BeanUtils.copyProperties(this, pubKey);
        return pubKey;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
