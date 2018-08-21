package com.payline.payment.samsung.pay.service;

import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.payline.payment.samsung.pay.bean.rest.request.CreateTransactionRequest;
import com.payline.payment.samsung.pay.exception.InvalidRequestException;
import com.payline.payment.samsung.pay.utils.config.ConfigEnvironment;
import com.payline.payment.samsung.pay.utils.config.ConfigProperties;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.service.PaymentService;

import okhttp3.Response;

/**
 * Created by Thales on 16/08/2018.
 */
public class PaymentServiceImpl extends AbstractPaymentHttpService<PaymentRequest> implements PaymentService {

    private static final Logger logger = LogManager.getLogger( PaymentServiceImpl.class );

    private CreateTransactionRequest.Builder requestBuilder;

    public PaymentServiceImpl() {
        super();
        this.requestBuilder = new CreateTransactionRequest.Builder();
    }

    @Override
    public PaymentResponse paymentRequest(PaymentRequest paymentRequest) {
        return processRequest(paymentRequest);
    }

    @Override
    public Response createSendRequest(PaymentRequest paymentRequest) throws IOException, InvalidRequestException, NoSuchAlgorithmException {

        // Create CreateTransaction request from Payline request
        CreateTransactionRequest createTransactionRequest = this.requestBuilder.fromPaymentRequest(paymentRequest);

        // Send CreateTransaction request
        ConfigEnvironment environment = Boolean.FALSE.equals( paymentRequest.getPaylineEnvironment().isSandbox() ) ? ConfigEnvironment.PROD : ConfigEnvironment.DEV;

        String scheme   = ConfigProperties.get(CONFIG__SHEME, environment);
        String host     = ConfigProperties.get(CONFIG__HOST, environment);
        String path     = ConfigProperties.get(CONFIG__PATH_TRANSACTION, environment);

        return httpClient.doPost(
                scheme,
                host,
                path,
                createTransactionRequest.buildBody()
        );

    }

    @Override
    public PaymentResponse processResponse(Response response) throws IOException {
        // TODO
        return null;
    }

}