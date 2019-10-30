//@@author LongLeCE

package planner.credential;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    private String defaultAlgorithm = "SHA-512";

    /**
     * Get hash value for input.
     * @param input input for hashing
     * @param algorithm algorithm to use
     * @return hash value
     * @throws NoSuchAlgorithmException when selected algorithm is not supported or invalid
     */
    public byte[] getHash(String input, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Get hash value for input.
     * @param input input for hashing
     * @return hash value
     */
    public byte[] getHash(String input) {
        try {
            return this.getHash(input, defaultAlgorithm);
        } catch (NoSuchAlgorithmException ignored) {
            return null;
        }
    }

    public String getHashString(String input, String algorithm) throws NoSuchAlgorithmException {
        return this.toHexString(this.getHash(input, algorithm));
    }

    public String getHashString(String input) {
        return this.toHexString(this.getHash(input));
    }

    /**
     * Convert password hash bytes to hex String.
     * @param hash input hash
     * @return hex String representation of input hash
     */
    public String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
}
