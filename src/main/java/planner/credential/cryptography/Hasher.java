//@@author LongLeCE

package planner.credential.cryptography;

import planner.util.cryptography.CryptographyUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    private final String defaultAlgorithm = "SHA-512";

    /**
     * Get hash value for input.
     * @param input input for hashing
     * @param algorithm algorithm to use
     * @return hash value
     * @throws NoSuchAlgorithmException when selected algorithm is not supported or invalid
     */
    public byte[] getHash(byte[] input, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        return md.digest(input);
    }

    public byte[] getHash(String input, String algorithm) throws NoSuchAlgorithmException {
        return this.getHash(input.getBytes(StandardCharsets.UTF_8), algorithm);
    }

    /**
     * Get hash value for input.
     * @param input input for hashing
     * @return hash value
     */
    public byte[] getHash(byte[] input) {
        try {
            return this.getHash(input, defaultAlgorithm);
        } catch (NoSuchAlgorithmException ignored) {
            return null;
        }
    }

    public byte[] getHash(String input) {
        return this.getHash(input.getBytes(StandardCharsets.UTF_8));
    }

    public String getHashString(String input, String algorithm) throws NoSuchAlgorithmException {
        return CryptographyUtils.byteArrayToHexString(this.getHash(input, algorithm));
    }

    public String getHashString(byte[] input, String algorithm) throws NoSuchAlgorithmException {
        return CryptographyUtils.byteArrayToHexString(this.getHash(input, algorithm));
    }

    public String getHashString(String input) {
        return CryptographyUtils.byteArrayToHexString(this.getHash(input));
    }

    public String getHashString(byte[] input) {
        return CryptographyUtils.byteArrayToHexString(this.getHash(input));
    }
}
