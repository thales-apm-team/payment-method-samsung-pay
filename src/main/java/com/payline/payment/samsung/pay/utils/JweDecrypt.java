package com.payline.payment.samsung.pay.utils;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class JweDecrypt {

    private static final String RSA = "RSA";
    private static final String AES = "AES";
    private static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";
    private static final String RSA_ECB_PKCS_1_PADDING = "RSA/ECB/PKCS1Padding";
    private static final String DELIMS = "[.]";

    public JweDecrypt() {
        // ras.
    }

    /**
     * @param encPayload encPayload.
     * @param privateKeyFilePath private key path.
     * @return Decrypted text.
     */
    public String getDecryptedData(String encPayload, String privateKeyFilePath) throws Exception {
        String[] tokens = encPayload.split(DELIMS);
        Base64.Decoder urlDecoder = Base64.getUrlDecoder();
        byte[] encKey = urlDecoder.decode(tokens[1]);
        byte[] iv = urlDecoder.decode(tokens[2]);
        byte[] cipherText = urlDecoder.decode(tokens[3]);
        byte[] tag = urlDecoder.decode(tokens[4]);
        byte[] plainText = new byte[cipherText.length];
            // Read private key file
//            File privateKeyFile = new File(privateKeyFilePath);

            ClassLoader classLoader = JweDecrypt.class.getClassLoader();
            File keyFile = new File(classLoader.getResource(privateKeyFilePath).getFile());
//            DataInputStream dis = new DataInputStream(new FileInputStream(keyFile));


        Path path = Paths.get(keyFile.getPath());
        byte[] privKeyBytes = Files.readAllBytes(path);


//        byte[] privKeyBytes = new byte[(int) keyFile.length()];
//            dis.read(privKeyBytes);
//            dis.close();
            // Set private key spec
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            Cipher decryptCipher = Cipher.getInstance(RSA_ECB_PKCS_1_PADDING);
            PrivateKey privKey = keyFactory.generatePrivate(spec);
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
    }
}
