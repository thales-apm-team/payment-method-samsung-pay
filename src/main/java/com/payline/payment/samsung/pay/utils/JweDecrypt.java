package com.payline.payment.samsung.pay.utils;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class JweDecrypt {


    public static final String RSA = "RSA";
    public static final String AES = "AES";
    public static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";
    public static final String RSA_ECB_PKCS_1_PADDING = "RSA/ECB/PKCS1Padding";
    public static final String DELIMS = "[.]";

    /**
     * @param args
     */
    public static void main(String[] args) {
        String encPayload = "eyJhbGciOiJSU0ExXzUiLCJraWQiOiJPK0YwcGFQZGZRT005UXhLSDBhd0ZUSTVpWVFETXY4Q0FDWlZRT24xeXBVPSIsInR5cCI6IkpPU0UiLCJjaGFubmVsU2VjdXJpdHlDb250ZXh0IjoiUlNBX1BLSSIsImVuYyI6IkExMjhHQ00ifQ.xmDaYGMyCDxfLcwT0q_xgnxMTsmeT0ZYQ7v04vdQG8_jliyXQA0DJZJqSrysxhKBSutn-CxLgFzrZ4w2WPlgu6sNcTaM42A1y1bjyPcwhHP_RfTPdZX1xlZi_huROHK4KVa9FhOug_1nUMcOJHJTbupUNWUlG-OFbdEAXNTrYAm-EVkKjo0_ZOxTsb2EZpKW94trJl-GGTwwOzx1Gib9JMPFvYlEH00qUdGDW_pTwUExuV3TDEgnrmUb6-rpMBlVqTJLsMKwrDnV5srAr2aQPzyvhNP8d1qJvORG2-g-IzfH91H9XhgSd6nhf0XqaCNE22ulQgKf5vFSSZjzCO-Whw.fFzZYk91o87S72k5.MbsuTH989WtsqThKpcPn8cB41QSp2prdmZlk12wz10a061WT25wm9Euwe4JXke_A04tarEFaIuQV-uhgWrfYFv3mDC9kbBSio7RjjxS5_PkvXURAcE0-OMLhjnHW7E-6K75A8IlsAsCgTTOsmTn90E7oTTEA3Kgqm13mWQANPMNXlcSwkdHI7ViMD4L7OGzXUP2zpwx5XrjhyDS5ybIdxG8XpusiEpkq9Qdzx_8dc9KH2pRrIQZxvtk.Ly-8HAjFp_3BkUeuRomqjA";
        String privateKeyFilePath = "./rsapriv.der";
        getDecryptedData(encPayload, privateKeyFilePath);
    }
    /**
     * @param encPayload encPayload.
     * @param privateKeyFilePath private key path.
     * @return Decrypted text.
     */
    public static String getDecryptedData(String encPayload, String privateKeyFilePath) {
        String[] tokens = encPayload.split(DELIMS);
        Base64.Decoder urlDecoder = Base64.getUrlDecoder();
        byte[] encKey = urlDecoder.decode(tokens[1]);
        byte[] iv = urlDecoder.decode(tokens[2]);
        byte[] cipherText = urlDecoder.decode(tokens[3]);
        byte[] tag = urlDecoder.decode(tokens[4]);
        byte[] plainText = new byte[cipherText.length];
        try {
            // Read private key file
            File privateKeyFile = new File(privateKeyFilePath);
            DataInputStream dis = new DataInputStream(new FileInputStream(privateKeyFile));
            byte[] privKeyBytes = new byte[(int) privateKeyFile.length()];
            dis.read(privKeyBytes);
            dis.close();
            // Set private key spec
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privKeyBytes);
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
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException | BadPaddingException
                | InvalidAlgorithmParameterException | ShortBufferException e) {
        }
        return new String(plainText);
    }
}
