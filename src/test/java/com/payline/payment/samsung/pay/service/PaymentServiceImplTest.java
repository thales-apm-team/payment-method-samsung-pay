package com.payline.payment.samsung.pay.service;

import com.payline.payment.samsung.pay.bean.rest.response.CreateTransactionPostResponse;
import com.payline.payment.samsung.pay.exception.ExternalCommunicationException;
import com.payline.payment.samsung.pay.exception.InvalidRequestException;
import com.payline.payment.samsung.pay.utils.Utils;
import com.payline.payment.samsung.pay.utils.http.SamsungPayHttpClient;
import com.payline.payment.samsung.pay.utils.http.StringResponse;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.anyString;

/**
 * Created by Thales on 27/08/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceImplTest {

    @Mock
    private SamsungPayHttpClient httpClient;

    @InjectMocks
    private PaymentServiceImpl service;

    @Before
    public void setup() {

    }

    @Test
    public void createSendRequest() throws URISyntaxException, IOException, InvalidRequestException, ExternalCommunicationException {
        String content = "thisIsAResponse";
        StringResponse response = new StringResponse();
        response.setCode(200);
        response.setContent(content);
        Mockito.when(httpClient.doPost(anyString(), anyString(), anyString(), anyString())).thenReturn(response);
        PaymentRequest request = Utils.createCompletePaymentBuilder().build();
        StringResponse httpResponse = service.createSendRequest(request);
        Assert.assertEquals(content, httpResponse.getContent());
    }

    @Test
    public void processResponse() throws MalformedURLException {
        String jsonContent = "{" +
                "   'resultCode': '0'," +
                "   'resultMessage': 'SUCCESS'," +
                "   'id': '000'," +
                "   'href': 'http://a.simple.url'," +
                "   'encInfo': { 'mod': '111', 'exp': '222', 'keyId': '333' }" +
                "}";
        StringResponse response = new StringResponse();
        response.setCode(200);
        response.setContent(jsonContent);

        service.setPaymentRequest(Utils.createCompletePaymentBuilder().build());

        PaymentResponse paymentResponse = service.processResponse(response);
        Assert.assertNotNull(paymentResponse);
    }

    @Test
    public void createConnectCall() {
        String goodConnectCall = "(function(){\n" +
                "    SamsungPay.connect(000 ,http://a.simple.url ," + Utils.SERVICE_ID + " ," + Utils.SUCCESS_URL + " ," + Utils.FAILURE_URL + " ,FR ,111 ,222 ,333 );\n" +
                "})()";

        String jsonResponse = "{" +
                "   'resultCode': '0'," +
                "   'resultMessage': 'SUCCESS'," +
                "   'id': '000'," +
                "   'href': 'http://a.simple.url'," +
                "   'encInfo': { 'mod': '111', 'exp': '222', 'keyId': '333' }" +
                "}";
        CreateTransactionPostResponse response = new CreateTransactionPostResponse.Builder().fromJson(jsonResponse);
        PaymentRequest request = Utils.createCompletePaymentBuilder().build();

        String connectCall = service.createConnectCall(request, response);
        Assert.assertEquals(goodConnectCall, connectCall);
    }

}