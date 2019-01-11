package com.payline.payment.samsung.pay.utils;

import com.payline.payment.samsung.pay.exception.DecryptException;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.*;

public class JweDecrypt {
    private static JweDecrypt instance = new JweDecrypt();

    private JweDecrypt() {
        // ras.
    }

    public static JweDecrypt getInstance(){
        return instance;
    }

    /**
     * @param encPayload encPayload.
     * @return Decrypted text.
     */
    public String getDecryptedData(String encPayload, byte[] encodedPrivateKey) throws DecryptException{
        try {
            Base64.Decoder decoder = Base64.getUrlDecoder();
            Base64.Decoder mimeDecoder = Base64.getMimeDecoder();

            String[] tokens = encPayload.split(DELIMS);

            byte[] encKey = decoder.decode(tokens[1]);
            byte[] iv = decoder.decode(tokens[2]);
            byte[] cipherText = decoder.decode(tokens[3]);
            byte[] tag = decoder.decode(tokens[4]);
            byte[] plainText = new byte[cipherText.length];

            byte[] privateKeyBytes = mimeDecoder.decode(encodedPrivateKey);

            // Set private key spec
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey privKey = keyFactory.generatePrivate(spec);

            Cipher decryptCipher = Cipher.getInstance(RSA_ECB_PKCS_1_PADDING);
            decryptCipher.init(Cipher.DECRYPT_MODE, privKey);
            byte[] plainEncKey = decryptCipher.doFinal(encKey);

            final Cipher aes128Cipher = Cipher.getInstance(AES_GCM_NO_PADDING);
            final GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * Byte.SIZE, iv);
            final SecretKeySpec keySpec = new SecretKeySpec(plainEncKey, AES);
            aes128Cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

            int offset = aes128Cipher.update(cipherText, 0, cipherText.length, plainText, 0);
            aes128Cipher.update(tag, 0, tag.length, plainText, offset);
            aes128Cipher.doFinal(plainText, offset);

            return new String(plainText);
        } catch (ShortBufferException | InvalidKeySpecException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException e) {
            throw new DecryptException(e.getMessage());
        }
    }
}
