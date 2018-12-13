package com.payline.payment.samsung.pay.utils;

import org.junit.Assert;
import org.junit.Test;

public class SamsungPayStringUtilsTest {

    @Test
    public void isEmpty() {
        Assert.assertTrue(SamsungPayStringUtils.isEmpty(null));
        Assert.assertTrue(SamsungPayStringUtils.isEmpty(""));
        Assert.assertTrue(SamsungPayStringUtils.isEmpty(" "));
        Assert.assertTrue(SamsungPayStringUtils.isEmpty("      "));
        Assert.assertFalse(SamsungPayStringUtils.isEmpty("     . "));
    }
}
