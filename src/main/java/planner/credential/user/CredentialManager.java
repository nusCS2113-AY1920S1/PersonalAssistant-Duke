//@@author LongLeCE

package planner.credential.user;

import planner.credential.cryptography.EncryptionLayer;
import planner.credential.cryptography.Hasher;
import planner.ui.cli.PlannerUi;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class CredentialManager {

    private static Hasher hasher = new Hasher();
    private ArrayList<EncryptionLayer> encryptionLayers;

    /**
     * Constructor for CredentialManager.
     */
    public CredentialManager() {
        this.encryptionLayers = new ArrayList<>();
        try {
            this.encryptionLayers.add(new EncryptionLayer("AES",
                                                          EncryptionLayer.genKey("AES",
                                                                         256,
                                                                           6478135743227891177L),
                                             "SHA-512"));
            this.encryptionLayers.add(new EncryptionLayer("RSA",
                                                          EncryptionLayer.genKey("RSA",
                                                                         2048,
                                                                           2457103743313381847L))
                                                          .encryptKey());
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    public boolean compareHash(String password, String passwordHash, String algorithm)
            throws NoSuchAlgorithmException {
        return this.hash(password, algorithm).equals(passwordHash);
    }

    public boolean compareHash(String password, String passwordHash) {
        return this.hash(password).equals(passwordHash);
    }

    public String hash(String message, String algorithm) throws NoSuchAlgorithmException {
        return hasher.getHashString(message, algorithm);
    }

    public String hash(String message) {
        return hasher.getHashString(message);
    }

    public static String requirePassword(PlannerUi plannerUi) {
        return plannerUi.prompt("Please enter your password to continue:", false, true);
    }

    public ArrayList<EncryptionLayer> getEncryptionLayers() {
        return this.encryptionLayers;
    }

    public int getHashLength() {
        return 64;
    }

    /**
     * Xor operation on two byte arrays.
     * @param message first byte array
     * @param secret second byte array
     * @return xor result
     */
    private byte[] xor(byte[] message, byte[] secret) {
        byte[] ret = new byte[message.length];
        for (int i = 0; i < message.length; ++i) {
            ret[i] = (byte) (message[i] ^ secret[i % secret.length]);
        }
        return ret;
    }
}
