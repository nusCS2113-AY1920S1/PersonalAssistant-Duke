//@@author LongLeCE

package planner.util.cryptography;

import planner.credential.cryptography.Hasher;

import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;

public class CryptographyUtils {

    private static Hasher hasher = new Hasher();

    /**
     * Convert byte array to hex String.
     * @param bytes input byte array
     * @return hex String representation of input byte array
     */
    public static String byteArrayToHexString(byte[] bytes) {
        BigInteger number = new BigInteger(1, bytes);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    /**
     * Convert hex String to byte array.
     * @param hex input hex String
     * @return byte array representation of input hex String
     */
    public static byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] data = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            data[i >> 1] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    public static Key toKey(byte[] key, String algorithm) {
        return new SecretKeySpec(key, 0, key.length, algorithm);
    }

    public static Key toKey(String key, String algorithm) {
        return CryptographyUtils.toKey(key.getBytes(StandardCharsets.UTF_8), algorithm);
    }

    /**
     * Check if message was tampered or not.
     * @param messageWithHash decoded message with hash value
     * @param hashLength length of hash
     * @return true if not tampered else false
     */
    public static boolean isOriginal(byte[] messageWithHash, int hashLength) {
        return Arrays.equals(
                CryptographyUtils.hasher.getHash(
                        Arrays.copyOf(messageWithHash, messageWithHash.length - hashLength)),
                Arrays.copyOfRange(messageWithHash, messageWithHash.length - hashLength, messageWithHash.length));
    }

    /**
     * Remove trailing hash from message.
     * @param messageWithHash message with hash
     * @param hashLength length of hash
     * @return original message
     */
    public static String removeTrailingHash(byte[] messageWithHash, int hashLength) {
        return new String(Arrays.copyOf(messageWithHash, messageWithHash.length - hashLength),
                StandardCharsets.UTF_8);
    }
}
