package com.payline.payment.samsung.pay;

import com.payline.pmapi.bean.payment.ContractProperty;
import com.payline.pmapi.bean.payment.PaymentFormContext;
import com.payline.pmapi.integration.AbstractPaymentIntegration;

import java.util.Map;

/**
 * Created by Thales on 27/08/2018.
 */
public class SamsungPayIT extends AbstractPaymentIntegration {

    @Override
    protected Map<String, ContractProperty> generateParameterContract() {
        return null;
    }

    @Override
    protected PaymentFormContext generatePaymentFormContext() {
        return null;
    }

    @Override
    protected String payOnPartnerWebsite(String s) {
        return null;
    }

    @Override
    protected String cancelOnPartnerWebsite(String s) {
        return null;
    }

}