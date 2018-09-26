package com.payline.payment.samsung.pay.service;

import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.*;
import static com.payline.pmapi.bean.payment.response.PaymentResponseRedirect.RedirectionRequest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.payline.payment.samsung.pay.bean.rest.request.CreateTransactionPostRequest;
import com.payline.payment.samsung.pay.bean.rest.response.CreateTransactionPostResponse;
import com.payline.payment.samsung.pay.exception.InvalidRequestException;
import com.payline.payment.samsung.pay.utils.config.ConfigEnvironment;
import com.payline.payment.samsung.pay.utils.config.ConfigProperties;
import com.payline.payment.samsung.pay.utils.http.StringResponse;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.PaymentResponseRedirect;
import com.payline.pmapi.service.PaymentService;

/**
 * Created by Thales on 16/08/2018.
 */
public class PaymentServiceImpl extends AbstractPaymentHttpService<PaymentRequest> implements PaymentService {

    private static final Logger logger = LogManager.getLogger( PaymentServiceImpl.class );

    private CreateTransactionPostRequest.Builder requestBuilder;

    private PaymentRequest paymentRequest;

    /**
     * Default public constructor
     */
    public PaymentServiceImpl() {
        super();
        this.requestBuilder = new CreateTransactionPostRequest.Builder();
    }

    @Override
    public PaymentResponse paymentRequest(PaymentRequest paymentRequest) {
        this.paymentRequest = paymentRequest;
        return this.processRequest(paymentRequest);
    }

    @Override
    public StringResponse createSendRequest(PaymentRequest paymentRequest) throws IOException, InvalidRequestException, URISyntaxException {

        // Create CreateTransaction request from Payline request
        CreateTransactionPostRequest createTransactionPostRequest = this.requestBuilder.fromPaymentRequest(paymentRequest);

        // Send CreateTransaction request
        ConfigEnvironment environment = Boolean.FALSE.equals( paymentRequest.getPaylineEnvironment().isSandbox() ) ? ConfigEnvironment.PROD : ConfigEnvironment.DEV;

        String scheme   = ConfigProperties.get(CONFIG__SHEME, environment);
        String host     = ConfigProperties.get(CONFIG__HOST, environment);
        String path     = ConfigProperties.get(CONFIG__PATH_TRANSACTION);

        return this.httpClient.doPost(
                scheme,
                host,
                path,
                createTransactionPostRequest.buildBody()
        );

    }

    @Override
    public PaymentResponse processResponse(StringResponse response) throws IOException {

        // Parse response
        CreateTransactionPostResponse createTransactionPostResponse = new CreateTransactionPostResponse.Builder().fromJson(response.getContent());

        if (createTransactionPostResponse.isResultOk()) {

            // FIXME : Cf. Confluence Q5
            URL url = new URL(null);

            // FIXME : Cf. Confluence Q5
            Map<String, String> postFormData = new HashMap<>();

            postFormData.put(HREF, createTransactionPostResponse.getHref());
            if (createTransactionPostResponse.getEncryptionInfo() != null) {
                postFormData.put(MOD, createTransactionPostResponse.getEncryptionInfo().getMod());
                postFormData.put(EXP, createTransactionPostResponse.getEncryptionInfo().getExp());
                postFormData.put(KEY_ID, createTransactionPostResponse.getEncryptionInfo().getKeyId());
            }

            RedirectionRequest redirectionRequest = new RedirectionRequest( url, postFormData );

            return PaymentResponseRedirect.PaymentResponseRedirectBuilder.aPaymentResponseRedirect()
                    // RedirectionRequest param mandatory for builder checkIntegrity test
                    .withRedirectionRequest(redirectionRequest)
                    .withTransactionIdentifier(createTransactionPostResponse.getId())
                    .build();

        } else {

            return this.processGenericErrorResponse(createTransactionPostResponse);

        }

    }

}