package com.payline.payment.samsung.pay.integration;

import com.payline.payment.samsung.pay.service.PaymentServiceImpl;
import com.payline.payment.samsung.pay.utils.Utils;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFormUpdated;
import org.junit.Assert;
import org.junit.Test;

public class PaymentServiceIT {
    private PaymentServiceImpl service = new PaymentServiceImpl();


    @Test
    public void paymentRequest() {
        PaymentRequest request = Utils.createCompletePaymentBuilder().build();
        PaymentResponse response = service.paymentRequest(request);

        Assert.assertEquals(PaymentResponseFormUpdated.class, response.getClass());
    }
}
