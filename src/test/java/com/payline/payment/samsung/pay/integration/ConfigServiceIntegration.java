package com.payline.payment.samsung.pay.integration;

import com.payline.payment.samsung.pay.service.ConfigurationServiceImpl;
import com.payline.payment.samsung.pay.utils.Utils;
import com.payline.pmapi.bean.configuration.request.ContractParametersCheckRequest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class ConfigServiceIntegration {
    private ConfigurationServiceImpl service = new ConfigurationServiceImpl();


    @Test
    public void check() {
        ContractParametersCheckRequest request = Utils.createContractParametersCheckRequest(Utils.MERCHANT_ID);
        Map<String, String> errors = service.check(request);
        Assert.assertEquals(0, errors.size());
    }
}
