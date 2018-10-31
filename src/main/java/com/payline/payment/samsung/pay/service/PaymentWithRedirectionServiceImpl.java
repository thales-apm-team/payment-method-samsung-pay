package com.payline.payment.samsung.pay.service;

import com.payline.payment.samsung.pay.bean.rest.request.PaymentCredentialGetRequest;
import com.payline.payment.samsung.pay.bean.rest.response.PaymentCredentialGetResponse;
import com.payline.payment.samsung.pay.exception.InvalidRequestException;
import com.payline.payment.samsung.pay.utils.config.ConfigEnvironment;
import com.payline.payment.samsung.pay.utils.config.ConfigProperties;
import com.payline.payment.samsung.pay.utils.http.StringResponse;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.payment.request.RedirectionPaymentRequest;
import com.payline.pmapi.bean.payment.request.TransactionStatusRequest;
import com.payline.pmapi.bean.payment.response.PaymentModeCard;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.buyerpaymentidentifier.Card;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseDoPayment;
import com.payline.pmapi.service.PaymentWithRedirectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.*;

/**
 * Created by Thales on 16/08/2018.
 */
public class PaymentWithRedirectionServiceImpl extends AbstractPaymentHttpService<RedirectionPaymentRequest> implements PaymentWithRedirectionService {

    private static final Logger logger = LogManager.getLogger( PaymentWithRedirectionServiceImpl.class );

    private PaymentCredentialGetRequest.Builder requestBuilder;

    private RedirectionPaymentRequest redirectionPaymentRequest;

    /**
     * Default public constructor
     */
    public PaymentWithRedirectionServiceImpl() {
        super();
        this.requestBuilder = new PaymentCredentialGetRequest.Builder();
    }

    @Override
    public PaymentResponse finalizeRedirectionPayment(RedirectionPaymentRequest redirectionPaymentRequest) {
        this.redirectionPaymentRequest = redirectionPaymentRequest;
        return this.processRequest(redirectionPaymentRequest);
    }

    @Override
    public PaymentResponse handleSessionExpired(TransactionStatusRequest transactionStatusRequest) {
        return buildPaymentResponseFailure(TIMEOUT, FailureCause.SESSION_EXPIRED);
    }

    @Override
    public StringResponse createSendRequest(RedirectionPaymentRequest paymentRequest) throws IOException, InvalidRequestException, URISyntaxException {

        // Create PaymentCredential request form Payline request
        PaymentCredentialGetRequest paymentCredentialGetRequest = this.requestBuilder.fromRedirectionPaymentRequest(paymentRequest);

        // Send PaymentCredential request
        ConfigEnvironment environment = Boolean.FALSE.equals( paymentRequest.getEnvironment().isSandbox() ) ? ConfigEnvironment.PROD : ConfigEnvironment.DEV;

        String scheme   = ConfigProperties.get(CONFIG__SHEME, environment);
        String host     = ConfigProperties.get(CONFIG__HOST, environment);
        String path     = ConfigProperties.get(CONFIG__PATH_TRANSACTION_PAYMENT_CREDENTIAL);

        // Build the request query attributes map
        Map<String, String> queryAttributes = new HashMap<>();
        queryAttributes.put(ID, paymentCredentialGetRequest.getId());
        queryAttributes.put(SERVICE_ID, paymentCredentialGetRequest.getServiceId());

        return this.httpClient.doGet(
                scheme,
                host,
                path,
                queryAttributes
        );

    }

    @Override
    public PaymentResponse processResponse(StringResponse response) throws IOException {

        // Parse response
        PaymentCredentialGetResponse paymentCredentialGetResponse = new PaymentCredentialGetResponse.Builder().fromJson(response.getContent());

        if (paymentCredentialGetResponse.isResultOk()) {

            // Decrypt 3DS data to retrieve Payment Mode info
            // TODO
            Card card = Card.CardBuilder.aCard()
                    // Pan param mandatory for builder checkIntegrity test
                    .withPan("")
                    // ExpirationDate param mandatory for builder checkIntegrity test
                    .withExpirationDate(YearMonth.of(22, 12))
                    .build();

            PaymentModeCard paymentModeCard = PaymentModeCard.PaymentModeCardBuilder.aPaymentModeCard()
                    // Card param mandatory for builder checkIntegrity test
                    .withCard(card)
                    .build();

            // FIXME : cf. Confluence Q6
            return PaymentResponseDoPayment.PaymentResponseDoPaymentBuilder.aPaymentResponseDoPayment()
                    // PaymentMode param mandatory for builder checkIntegrity test
                    .withPaymentMode(paymentModeCard)
                    // TransactionIdentifier param mandatory for builder checkIntegrity test
                    .withPartnerTransactionId(this.redirectionPaymentRequest.getTransactionId())
                    .build();

        } else {

            return this.processGenericErrorResponse(paymentCredentialGetResponse);

        }

    }

    private void decryptData3DS(String encryptedData3DS) {

    }

}
