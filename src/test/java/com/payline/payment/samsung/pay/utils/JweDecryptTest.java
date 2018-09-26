package com.payline.payment.samsung.pay.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class JweDecryptTest {

//    private String encPayload =
//            "eyJhbGciOiJSU0ExXzUiLCJraWQiOiJPK0YwcGFQZGZRT005UXhLSDBhd0ZUSTVpWVFETXY4Q0FDWlZRT24xeXBVPSIsInR5cCI6I" +
//            "kpPU0UiLCJjaGFubmVsU2VjdXJpdHlDb250ZXh0IjoiUlNBX1BLSSIsImVuYyI6IkExMjhHQ00ifQ.xmDaYGMyCDxfLcwT0q_xgnxM" +
//            "TsmeT0ZYQ7v04vdQG8_jliyXQA0DJZJqSrysxhKBSutn-" +
//            "CxLgFzrZ4w2WPlgu6sNcTaM42A1y1bjyPcwhHP_RfTPdZX1xlZi_huROHK4KVa9FhOug_1nUMcOJHJTbupUNWUlG-" +
//            "OFbdEAXNTrYAm-EVkKjo0_ZOxTsb2EZpKW94trJl-GGTwwOzx1Gib9JMPFvYlEH00qUdGDW_pTwUExuV3TDEgnrmUb6-" +
//            "rpMBlVqTJLsMKwrDnV5srAr2aQPzyvhNP8d1qJvORG2-g-IzfH91H9XhgSd6nhf0XqaCNE22ulQgKf5vFSSZjzCO-" +
//            "Whw.fFzZYk91o87S72k5.MbsuTH989WtsqThKpcPn8cB41QSp2prdmZlk12wz10a061WT25wm9Euwe4JXke_A04tarEFaIuQV-" +
//            "uhgWrfYFv3mDC9kbBSio7RjjxS5_PkvXURAcE0-OMLhjnHW7E-" +
//            "6K75A8IlsAsCgTTOsmTn90E7oTTEA3Kgqm13mWQANPMNXlcSwkdHI7ViMD4L7OGzXUP2zpwx5XrjhyDS5ybIdxG8XpusiEpkq9Qdzx" +
//            "_8dc9KH2pRrIQZxvtk.Ly-8HAjFp_3BkUeuRomqjA";

    private String encPayload =
            "eyJhbGciOiJSU0ExXzUiLCJraWQiOiJPK0YwcGFQZGZRT005UXhLSDBhd0ZUSTVpWVFETXY4Q0FDWlZRT24xeXBVPSIsInR5cCI6IkpPU0UiLCJjaGFubmVsU2VjdXJpdHlDb250ZXh0IjoiUlNBX1BLSSIsImVuYyI6IkExMjhHQ00ifQ.xmDaYGMyCDxfLcwT0q_xgnxMTsmeT0ZYQ7v04vdQG8_jliyXQA0DJZJqSrysxhKBSutn-CxLgFzrZ4w2WPlgu6sNcTaM42A1y1bjyPcwhHP_RfTPdZX1xlZi_huROHK4KVa9FhOug_1nUMcOJHJTbupUNWUlG-OFbdEAXNTrYAm-EVkKjo0_ZOxTsb2EZpKW94trJl-GGTwwOzx1Gib9JMPFvYlEH00qUdGDW_pTwUExuV3TDEgnrmUb6-rpMBlVqTJLsMKwrDnV5srAr2aQPzyvhNP8d1qJvORG2-g-IzfH91H9XhgSd6nhf0XqaCNE22ulQgKf5vFSSZjzCO-Whw.fFzZYk91o87S72k5.MbsuTH989WtsqThKpcPn8cB41QSp2prdmZlk12wz10a061WT25wm9Euwe4JXke_A04tarEFaIuQV-uhgWrfYFv3mDC9kbBSio7RjjxS5_PkvXURAcE0-OMLhjnHW7E-6K75A8IlsAsCgTTOsmTn90E7oTTEA3Kgqm13mWQANPMNXlcSwkdHI7ViMD4L7OGzXUP2zpwx5XrjhyDS5ybIdxG8XpusiEpkq9Qdzx_8dc9KH2pRrIQZxvtk.Ly-8HAjFp_3BkUeuRomqjA";

//    private String privateKeyFilePath = "./rsapriv.der";
    private String privateKeyFilePath = "keystore/rsapriv.der";
//    private String privateKeyFilePath = "D:/Brice/PROJET_PAYLINE/payment-method-samsung-pay/src/test/resources/keystore/rsapriv.der";

    private JweDecrypt service;

    @Before
    public void setUp(){
        // ras.
        service = new  JweDecrypt();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getDecryptedData() throws Exception {
        String res = service.getDecryptedData(encPayload, privateKeyFilePath);
        System.out.print(res);
        Assert.assertEquals(true, true);

    }

    @Test
    public void testPadding() throws Exception {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024, random);
        KeyPair keyPair = keyGen.generateKeyPair();

        /* constant 117 is a public key size - 11 */
        byte[] plaintext = new byte[117];
        random.nextBytes(plaintext);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] ciphertext = cipher.doFinal(plaintext);
        System.out.println(plaintext.length + " becomes " + ciphertext.length);
    }
}
