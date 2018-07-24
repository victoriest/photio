package me.victoriest.photio.model.rsa;

/**
 * @author VictoriEST
 */
public class RSAPubKey extends RSAKey {

    private static final long serialVersionUID = 7987664241230431372L;

    private String pubKey;

    public RSAPubKey() {
    }

    public RSAPubKey(String pubKey) {
        this.pubKey = pubKey;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }
}
