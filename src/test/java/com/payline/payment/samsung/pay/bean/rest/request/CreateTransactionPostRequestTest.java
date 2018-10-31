package com.payline.payment.samsung.pay.bean.rest.request;

import com.payline.payment.samsung.pay.exception.InvalidRequestException;
import com.payline.payment.samsung.pay.utils.Utils;
import com.payline.pmapi.bean.configuration.PartnerConfiguration;
import com.payline.pmapi.bean.payment.ContractConfiguration;
import com.payline.pmapi.bean.payment.Environment;
import com.payline.pmapi.bean.payment.Order;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static com.payline.payment.samsung.pay.utils.Utils.NOTIFICATION_URL;
import static com.payline.pmapi.integration.AbstractPaymentIntegration.CANCEL_URL;

/**
 * Created by Thales on 27/08/2018.
 */
public class CreateTransactionPostRequestTest {

    @Test
    public void fromPaymentRequest() throws InvalidRequestException {
        PaymentRequest request = Utils.createCompletePaymentBuilder().build();

        CreateTransactionPostRequest samsungRequest = new CreateTransactionPostRequest.Builder().fromPaymentRequest(request);
        Assert.assertNotNull(samsungRequest);
    }

    @Test(expected = InvalidRequestException.class)
    public void fromPaymentRequestNoOrderReference() throws InvalidRequestException {
        Order emptyOrder = Order.OrderBuilder.anOrder().build();
        PaymentRequest request = Utils.createCompletePaymentBuilder().withOrder(emptyOrder).build();
        new CreateTransactionPostRequest.Builder().fromPaymentRequest(request);
    }

    @Test(expected = InvalidRequestException.class)
    public void fromPaymentRequestNoMerchantNameProperties() throws InvalidRequestException {
        ContractConfiguration configuration = new ContractConfiguration("", new HashMap<>());
        PaymentRequest request = Utils.createCompletePaymentBuilder().withContractConfiguration(configuration).build();
        new CreateTransactionPostRequest.Builder().fromPaymentRequest(request);
    }

    @Test(expected = InvalidRequestException.class)
    public void fromPaymentRequestNoCallBackUrl() throws InvalidRequestException {
        Environment environment = new Environment(NOTIFICATION_URL,null,CANCEL_URL,true);
        PaymentRequest request = Utils.createCompletePaymentBuilder().withEnvironment(environment).build();
        new CreateTransactionPostRequest.Builder().fromPaymentRequest(request);
    }

    @Test(expected = InvalidRequestException.class)
    public void fromPaymentRequestNoServiceId() throws InvalidRequestException {
        PartnerConfiguration configuration = new PartnerConfiguration(new HashMap<>(),new HashMap<>());
        PaymentRequest request = Utils.createCompletePaymentBuilder().withPartnerConfiguration(configuration).build();
        new CreateTransactionPostRequest.Builder().fromPaymentRequest(request);
    }
}