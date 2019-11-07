//@@author LongLeCE

package planner.credential.cryptography;

import planner.util.cryptography.CryptographyUtils;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class EncryptionLayer {

    private String encryptionAlgorithm;
    private KeyPair key;
    private String hashAlgorithm;
    private boolean encryptKey;

    /**
     * Constructor for encryption layer.
     * @param encryptionAlgorithm encryption algorithm to use
     * @param key key to use for encryption
     * @param hashAlgorithm hash algorithm to use
     */
    public EncryptionLayer(String encryptionAlgorithm, KeyPair key, String hashAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.key = key;
        this.hashAlgorithm = hashAlgorithm;
        this.encryptKey = false;
    }

    public EncryptionLayer(String encryptionAlgorithm, String hashAlgorithm) throws NoSuchAlgorithmException {
        this(encryptionAlgorithm, EncryptionLayer.genKey(encryptionAlgorithm), hashAlgorithm);
    }

    public EncryptionLayer(String encryptionAlgorithm, KeyPair key) {
        this(encryptionAlgorithm, key, null);
    }

    public EncryptionLayer(String encryptionAlgorithm,
                           PublicKey publicKey,
                           PrivateKey privateKey,
                           String hashAlgorithm) {
        this(encryptionAlgorithm, new KeyPair(publicKey, privateKey), hashAlgorithm);
    }

    public EncryptionLayer(String encryptionAlgorithm, Key publicKey, Key privateKey, String hashAlgorithm) {
        this(encryptionAlgorithm, (PublicKey) publicKey, (PrivateKey) privateKey, hashAlgorithm);
    }

    public EncryptionLayer(String encryptionAlgorithm, Key key, String hashAlgorithm) {
        this(encryptionAlgorithm, key, key, hashAlgorithm);
    }

    public EncryptionLayer(String encryptionAlgorithm, Key key) {
        this(encryptionAlgorithm, key, null);
    }

    public EncryptionLayer(String encryptionAlgorithm, String publicKey, String privateKey, String hashAlgorithm) {
        this(encryptionAlgorithm,
                CryptographyUtils.toKey(publicKey, encryptionAlgorithm),
                CryptographyUtils.toKey(privateKey, encryptionAlgorithm),
                hashAlgorithm);
    }

    public EncryptionLayer(String encryptionAlgorithm, String key, String hashAlgorithm) {
        this(encryptionAlgorithm, key, key, hashAlgorithm);
    }

    public EncryptionLayer(String encryptionAlgorithm, byte[] publicKey, byte[] privateKey, String hashAlgorithm) {
        this(encryptionAlgorithm,
                CryptographyUtils.toKey(publicKey, encryptionAlgorithm),
                CryptographyUtils.toKey(privateKey, encryptionAlgorithm),
                hashAlgorithm);
    }

    public EncryptionLayer(String encryptionAlgorithm, byte[] publicKey, byte[] privateKey) {
        this(encryptionAlgorithm, publicKey, privateKey, null);
    }

    public EncryptionLayer(String encryptionAlgorithm, byte[] key, String hashAlgorithm) {
        this(encryptionAlgorithm, key, key, hashAlgorithm);
    }

    public EncryptionLayer(String encryptionAlgorithm, byte[] key) {
        this(encryptionAlgorithm, key, (String) null);
    }

    public EncryptionLayer(String encryptionAlgorithm) throws NoSuchAlgorithmException {
        this(encryptionAlgorithm, (String) null);
    }

    public boolean isSymmetric() {
        return this.key.getPublic().equals(this.key.getPrivate());
    }

    public String getEncryptionAlgorithm() {
        return this.encryptionAlgorithm;
    }

    public String getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public KeyPair getKey() {
        return this.key;
    }

    public Key getPublicKey() {
        return this.key.getPublic();
    }

    public Key getPrivateKey() {
        return this.key.getPrivate();
    }

    public boolean isEncryptKey() {
        return this.encryptKey;
    }

    /**
     * Set to encrypt last layer's key instead of the data.
     * @return this
     */
    public EncryptionLayer encryptKey() {
        this.encryptKey = true;
        return this;
    }

    /**
     * Generate KeyPair for cipher.
     * @param algorithm algorithm to use
     * @param keySize key size
     * @param seed seed for SecureRandom
     * @return generated KeyPair
     * @throws NoSuchAlgorithmException if algorithm required is not supported
     */
    public static KeyPair genKey(String algorithm, int keySize, long seed) throws NoSuchAlgorithmException {
        if (algorithm.contains("/")) {
            algorithm = algorithm.substring(0, algorithm.indexOf('/'));
        }
        KeyPair key;
        Key tmp;
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(seed);
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm);
            generator.initialize(keySize, random);
            key = new KeyPair(generator.genKeyPair());
        } catch (NoSuchAlgorithmException ex) {
            KeyGenerator generator = KeyGenerator.getInstance(algorithm);
            generator.init(keySize, random);
            tmp = generator.generateKey();
            key = new KeyPair(tmp, tmp);
        }
        return key;
    }

    public static KeyPair genKey(String algorithm) throws NoSuchAlgorithmException {
        return EncryptionLayer.genKey(algorithm, 2048, new SecureRandom().nextLong());
    }
}
