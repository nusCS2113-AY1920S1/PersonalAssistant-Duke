package planner.credential;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {

    private String defaultAlgorithm = "SHA-512";

    public byte[] getHash(String input, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

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

    public String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }
}
