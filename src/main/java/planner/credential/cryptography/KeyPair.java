//@@author LongLeCE

package planner.credential.cryptography;

import java.io.Serializable;
import java.security.Key;

/**
 * Wrapper for KeyPair to overcome some encryption implementation difficulties.
 */
public class KeyPair implements Serializable {
    private static final long serialVersionUID = -7565189502268009837L;
    private Key privateKey;
    private Key publicKey;

    /**
     * Constructor for KeyPair.
     * @param publicKey public key
     * @param privateKey private key
     */
    public KeyPair(Key publicKey, Key privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * Constructor for KeyPair.
     * @param keyPair Java's KeyPair implementation
     */
    public KeyPair(java.security.KeyPair keyPair) {
        this.publicKey = keyPair.getPublic();
        this.privateKey = keyPair.getPrivate();
    }

    public Key getPublic() {
        return this.publicKey;
    }

    public Key getPrivate() {
        return this.privateKey;
    }
}
