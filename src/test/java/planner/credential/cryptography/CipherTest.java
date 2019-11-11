//@@author LongLeCE

package planner.credential.cryptography;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planner.logic.command.CommandTestFramework;
import planner.logic.exceptions.legacy.ModException;
import planner.util.cryptography.CryptographyUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CipherTest extends CommandTestFramework {

    private ArrayList<EncryptionLayer> encryptionLayersWithHash;
    private ArrayList<EncryptionLayer> encryptionLayersWithoutHash;
    private static final ArrayList<EncryptionLayer> emptyLayerList = new ArrayList<>();
    private Cipher cipher;
    private CipherState cipherState;

    /**
     * CipherTest constructor.
     * @throws ModException when initialization of CommandTestFramework throws an exception
     */
    public CipherTest() throws ModException {
        encryptionLayersWithHash = new ArrayList<>();
        encryptionLayersWithoutHash = new ArrayList<>();
        try {
            encryptionLayersWithHash.add(new EncryptionLayer("AES",
                    EncryptionLayer.genKey("AES",
                            256,
                            2457103743313381847L),
                    "SHA-512"));
            encryptionLayersWithHash.add(new EncryptionLayer("RSA",
                    EncryptionLayer.genKey("RSA",
                            2048,
                            6478135743227891177L))
                    .encryptKey());

            encryptionLayersWithoutHash.add(new EncryptionLayer("AES",
                    EncryptionLayer.genKey("AES",
                            256,
                            2457103743313381847L)));
            encryptionLayersWithoutHash.add(new EncryptionLayer("RSA",
                    EncryptionLayer.genKey("RSA",
                            2048,
                            6478135743227891177L))
                    .encryptKey());
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        cipher = new Cipher();
    }

    private void encrypt(CipherState cipherState,
                         ArrayList<EncryptionLayer> encryptionLayers,
                         boolean messagesShouldBeDifferent)
            throws IllegalBlockSizeException,
            InvalidKeyException,
            BadPaddingException,
            NoSuchAlgorithmException,
            NoSuchPaddingException {
        byte[] originalMessage = cipherState.getMessage();
        cipher.encode(cipherState, encryptionLayers, true);
        if (messagesShouldBeDifferent) {
            assertNotEquals(originalMessage, cipherState.getMessage());
        }
    }

    private void encrypt(CipherState cipherState,
                         ArrayList<EncryptionLayer> encryptionLayers)
            throws InvalidKeyException,
            BadPaddingException,
            NoSuchAlgorithmException,
            IllegalBlockSizeException,
            NoSuchPaddingException {
        encrypt(cipherState, encryptionLayers, true);
    }

    private void decrypt(CipherState cipherState,
                         ArrayList<EncryptionLayer> encryptionLayers,
                         boolean messagesShouldBeDifferent)
            throws IllegalBlockSizeException,
            InvalidKeyException,
            BadPaddingException,
            NoSuchAlgorithmException,
            NoSuchPaddingException {
        byte[] cipherMessage = cipherState.getMessage();
        cipher.decode(cipherState, encryptionLayers);
        if (messagesShouldBeDifferent) {
            assertNotEquals(cipherMessage, cipherState.getMessage());
        }
    }

    private void decrypt(CipherState cipherState,
                         ArrayList<EncryptionLayer> encryptionLayers)
            throws InvalidKeyException,
            BadPaddingException,
            NoSuchAlgorithmException,
            IllegalBlockSizeException,
            NoSuchPaddingException {
        decrypt(cipherState, encryptionLayers, true);
    }

    private void encryptThenDecrypt(String message,
                                    ArrayList<EncryptionLayer> encryptionLayers,
                                    boolean messagesShouldBeDifferent)
            throws InvalidKeyException,
            BadPaddingException,
            NoSuchAlgorithmException,
            IllegalBlockSizeException,
            NoSuchPaddingException {
        cipherState = new CipherState(message.getBytes());
        encrypt(cipherState, encryptionLayers, messagesShouldBeDifferent);
        decrypt(cipherState, encryptionLayers, messagesShouldBeDifferent);

    }

    private void encryptThenDecrypt(String message,
                                    ArrayList<EncryptionLayer> encryptionLayers)
            throws InvalidKeyException,
            BadPaddingException,
            NoSuchAlgorithmException,
            IllegalBlockSizeException,
            NoSuchPaddingException {
        encryptThenDecrypt(message, encryptionLayers, true);

    }

    @DisplayName("Cipher Encryption With Hash Test")
    @Test
    public void encryptionWithHashShouldReturnAValidDigest()
            throws IllegalBlockSizeException,
            InvalidKeyException,
            BadPaddingException,
            NoSuchAlgorithmException,
            NoSuchPaddingException {
        resetAll();
        String message = "test";
        cipherState = new CipherState(message.getBytes());
        encrypt(cipherState, encryptionLayersWithHash);
        assertEquals(80, cipherState.getMessage().length);
        assertEquals(256, cipherState.getKey().length);
    }

    @DisplayName("Cipher Encryption Without Hash Test")
    @Test
    public void encryptionWithoutHashShouldReturnAValidDigest()
            throws IllegalBlockSizeException,
            InvalidKeyException,
            BadPaddingException,
            NoSuchAlgorithmException,
            NoSuchPaddingException {
        resetAll();
        String message = "test";
        cipherState = new CipherState(message.getBytes());
        encrypt(cipherState, encryptionLayersWithoutHash);
        assertEquals(16, cipherState.getMessage().length);
        assertEquals(256, cipherState.getKey().length);
    }

    @DisplayName("Cipher Encryption Without Layers Test")
    @Test
    public void encryptionWithoutLayersShouldDoNothing()
            throws IllegalBlockSizeException,
            InvalidKeyException,
            BadPaddingException,
            NoSuchAlgorithmException,
            NoSuchPaddingException {
        resetAll();
        String message = "test";
        cipherState = new CipherState(message.getBytes());
        encrypt(cipherState, emptyLayerList, false);
        assertArrayEquals(message.getBytes(), cipherState.getMessage());
        assertNull(cipherState.getKey());
    }

    @DisplayName("Cipher Decryption With Hash Test")
    @Test
    public void decryptionOfMessageWithHashShouldReturnOriginalMessageWithHash()
            throws IllegalBlockSizeException,
            InvalidKeyException,
            BadPaddingException,
            NoSuchAlgorithmException,
            NoSuchPaddingException {
        resetAll();
        String originalMessage = "test";
        encryptThenDecrypt(originalMessage, encryptionLayersWithHash);
        byte[] decryptedMessage = cipherState.getMessage();
        String recoveredMessage = CryptographyUtils.removeTrailingHash(decryptedMessage, 64);
        assertEquals(originalMessage, recoveredMessage);
    }

    @DisplayName("Cipher Decryption Without Hash Test")
    @Test
    public void decryptionOfMessageWithoutHashShouldReturnOriginalMessageWithoutHash()
            throws IllegalBlockSizeException,
            InvalidKeyException,
            BadPaddingException,
            NoSuchAlgorithmException,
            NoSuchPaddingException {
        resetAll();
        String originalMessage = "test";
        encryptThenDecrypt(originalMessage, encryptionLayersWithoutHash);
        byte[] decryptedMessage = cipherState.getMessage();
        String recoveredMessage = new String(decryptedMessage, StandardCharsets.UTF_8);
        assertEquals(originalMessage, recoveredMessage);
    }

    @DisplayName("Cipher Decryption Without Layers Test")
    @Test
    public void decryptionWithoutLayersShouldDoNothing()
            throws IllegalBlockSizeException,
            InvalidKeyException,
            BadPaddingException,
            NoSuchAlgorithmException,
            NoSuchPaddingException {
        resetAll();
        String message = "test";
        cipherState = new CipherState(message.getBytes());
        encryptThenDecrypt(message, emptyLayerList, false);
        assertArrayEquals(message.getBytes(), cipherState.getMessage());
        assertNull(cipherState.getKey());
    }
}
