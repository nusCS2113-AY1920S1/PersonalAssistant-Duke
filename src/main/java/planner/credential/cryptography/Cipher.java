//@@author LongLeCE

package planner.credential.cryptography;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Cipher {

    private javax.crypto.Cipher cipher;
    private static Hasher hasher = new Hasher();

    private void encode(CipherState state, EncryptionLayer layer, boolean withHash)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            BadPaddingException,
            IllegalBlockSizeException,
            InvalidKeyException {
        this.cipher = javax.crypto.Cipher.getInstance(layer.getEncryptionAlgorithm());
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, layer.getPublicKey());
        byte[] toEncrypt = state.next(layer.isEncryptKey());
        if (withHash && layer.getHashAlgorithm() != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            try {
                stream.write(toEncrypt);
                stream.write(Cipher.hasher.getHash(toEncrypt, layer.getHashAlgorithm()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            toEncrypt = stream.toByteArray();
        }
        state.updateState(cipher.doFinal(toEncrypt), layer);
    }

    /**
     * Encode data.
     * @param state current cipher state
     * @param layers encryption layers
     * @param withHash with or without hash of innermost layer
     * @return final cipher state
     * @throws NoSuchPaddingException if encryption padding is invalid
     * @throws NoSuchAlgorithmException if algorithm provided in encryption layer is not supported
     * @throws BadPaddingException if encryption padding is valid but not usable in current encryption state
     * @throws IllegalBlockSizeException if block size of data is invalid
     * @throws InvalidKeyException if key used is invalid
     */
    public CipherState encode(CipherState state, List<EncryptionLayer> layers, boolean withHash)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            BadPaddingException,
            IllegalBlockSizeException,
            InvalidKeyException {
        this.encode(state, layers.get(0), withHash);
        for (int i = 1; i < layers.size(); ++i) {
            this.encode(state, layers.get(i),false);
        }
        return state;
    }

    private void decode(CipherState state, EncryptionLayer layer)
            throws NoSuchPaddingException,
                   NoSuchAlgorithmException,
                   BadPaddingException,
                   IllegalBlockSizeException,
                   InvalidKeyException {
        this.cipher = javax.crypto.Cipher.getInstance(layer.getEncryptionAlgorithm());
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, layer.getPrivateKey());
        byte[] toDecrypt = state.next(layer.isEncryptKey());
        state.updateState(cipher.doFinal(toDecrypt), layer);
    }

    /**
     * Decode data.
     * @param state current cipher state
     * @param layers encryption layers
     * @return final cipher state
     * @throws NoSuchPaddingException if encryption padding is invalid
     * @throws NoSuchAlgorithmException if algorithm provided in encryption layer is not supported
     * @throws BadPaddingException if encryption padding is valid but not usable in current encryption state
     * @throws IllegalBlockSizeException if block size of data is invalid
     * @throws InvalidKeyException if key used is invalid
     */
    public CipherState decode(CipherState state, List<EncryptionLayer> layers)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            BadPaddingException,
            IllegalBlockSizeException,
            InvalidKeyException {
        for (int i = layers.size(); i-- > 0;) {
            this.decode(state, layers.get(i));
        }
        return state;
    }
}
