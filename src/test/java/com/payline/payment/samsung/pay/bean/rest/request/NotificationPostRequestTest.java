package com.payline.payment.samsung.pay.bean.rest.request;

import com.payline.payment.samsung.pay.exception.InvalidRequestException;
import com.payline.payment.samsung.pay.utils.Utils;
import com.payline.pmapi.bean.configuration.PartnerConfiguration;
import com.payline.pmapi.bean.payment.request.NotifyTransactionStatusRequest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Thales on 27/08/2018.
 */
public class NotificationPostRequestTest {
    @Test
    public void fromPaymentRequest() throws InvalidRequestException {
        NotifyTransactionStatusRequest request = Utils.createNotifyTransactionRequest();
        NotificationPostRequest samsungRequest = new NotificationPostRequest.Builder().fromNotifyTransactionStatusRequest(request);
        Assert.assertNotNull(samsungRequest);
    }


    @Test
    public void fromPaymentRequestNoPropertyServiceId() {
        try {
            PartnerConfiguration configuration = new PartnerConfiguration(null, null);
            NotifyTransactionStatusRequest request = Utils.createNotifyTransactionRequestBuilder().withPartnerConfiguration(configuration).build();
            new NotificationPostRequest.Builder().fromNotifyTransactionStatusRequest(request);
        }catch (Exception e){
            Assert.assertEquals("Missing PartnerConfiguration property: service id", e.getMessage());
        }
    }
}