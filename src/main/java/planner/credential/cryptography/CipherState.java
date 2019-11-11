//@@author LongLeCE

package planner.credential.cryptography;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class CipherState {

    private byte[] message;
    private byte[] key;

    /**
     * Constructor for CipherState.
     * @param message current cipher message
     * @param key current private key
     */
    public CipherState(byte[] message, byte[] key) {
        this.message = message;
        this.key = key;
    }

    public CipherState(byte[] message) {
        this(message, null);
    }

    public CipherState() {
    }

    public byte[] getMessage() {
        return this.message;
    }

    public byte[] getKey() {
        return this.key;
    }

    /**
     * Update cipher state.
     * @param cipherText new cipher text
     * @param layer current encryption layer
     */
    public void updateState(byte[] cipherText, EncryptionLayer layer) {
        if (layer.isEncryptKey()) {
            this.key = cipherText;
        } else {
            this.message = cipherText;
            this.key = layer.getPrivateKey().getEncoded();
        }
    }

    public byte[] next(boolean isEncryptKey) {
        return isEncryptKey ? this.key : this.message;
    }

    /**
     * String representation of CipherState.
     * @return HashMap-like String representation of CipherState
     */
    public String toString() {
        String messageRepresentation;
        String keyRepresentation;
        if (this.message == null) {
            messageRepresentation = "";
        } else {
            messageRepresentation = new String(this.message, StandardCharsets.UTF_8);
        }
        if (this.key == null) {
            keyRepresentation = "";
        } else {
            keyRepresentation = new String(this.key, StandardCharsets.UTF_8);
        }
        HashMap<String, String> state = new HashMap<>();
        state.put("message", messageRepresentation);
        state.put("privateKey", keyRepresentation);
        return state.toString();
    }
}
