//@@author LongLeCE

package planner.credential.cryptography;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EncryptionLayerTest {

    private EncryptionLayer layer;

    @DisplayName("Encryption Key Symmetry Test")
    @Test
    public void encryptionLayerForSymmetricAlgorithmShouldBeSymmetric() throws NoSuchAlgorithmException {
        String algorithm = "AES";
        KeyPair key = EncryptionLayer.genKey(algorithm, 256, new SecureRandom().nextLong());
        layer = new EncryptionLayer(algorithm, key);
        assertTrue(layer.isSymmetric());
    }

    @DisplayName("Encryption Key Asymmetry Test")
    @Test
    public void encryptionLayerForAsymmetricAlgorithmShouldBeAsymmetric() throws NoSuchAlgorithmException {
        String algorithm = "RSA";
        KeyPair key = EncryptionLayer.genKey(algorithm);
        layer = new EncryptionLayer(algorithm, key);
        assertFalse(layer.isSymmetric());
    }
}
