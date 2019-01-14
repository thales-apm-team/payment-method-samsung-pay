package com.payline.payment.samsung.pay.service;

import com.payline.payment.samsung.pay.bean.rest.request.PaymentCredentialGetRequest;
import com.payline.payment.samsung.pay.bean.rest.response.PaymentCredentialGetResponse;
import com.payline.payment.samsung.pay.bean.rest.response.nesteed.DecryptedCard;
import com.payline.payment.samsung.pay.exception.DecryptException;
import com.payline.payment.samsung.pay.exception.ExternalCommunicationException;
import com.payline.payment.samsung.pay.exception.InvalidRequestException;
import com.payline.payment.samsung.pay.utils.JweDecrypt;
import com.payline.payment.samsung.pay.utils.http.StringResponse;
import com.payline.pmapi.bean.common.FailureCause;
import com.payline.pmapi.bean.payment.request.RedirectionPaymentRequest;
import com.payline.pmapi.bean.payment.request.TransactionStatusRequest;
import com.payline.pmapi.bean.payment.response.PaymentModeCard;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.buyerpaymentidentifier.Card;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseDoPayment;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import com.payline.pmapi.service.PaymentWithRedirectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URISyntaxException;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.*;

/**
 * Created by Thales on 16/08/2018.
 */
public class PaymentWithRedirectionServiceImpl extends AbstractPaymentHttpService<RedirectionPaymentRequest> implements PaymentWithRedirectionService {

    private static final Logger LOGGER = LogManager.getLogger(PaymentWithRedirectionServiceImpl.class);

    private PaymentCredentialGetRequest.Builder requestBuilder;

    private RedirectionPaymentRequest redirectionPaymentRequest;
    private JweDecrypt jweDecrypt;

    /**
     * Default public constructor
     */
    public PaymentWithRedirectionServiceImpl() {
        super();
        this.requestBuilder = new PaymentCredentialGetRequest.Builder();
        jweDecrypt = JweDecrypt.getInstance();
    }

    @Override
    public PaymentResponse finalizeRedirectionPayment(RedirectionPaymentRequest redirectionPaymentRequest) {
        this.redirectionPaymentRequest = redirectionPaymentRequest;
        return this.processRequest(redirectionPaymentRequest);
    }

    @Override
    public PaymentResponse handleSessionExpired(TransactionStatusRequest transactionStatusRequest) {
        return buildPaymentResponseFailure(DEFAULT_ERROR_CODE, FailureCause.SESSION_EXPIRED);
    }

    @Override
    public StringResponse createSendRequest(RedirectionPaymentRequest paymentRequest) throws InvalidRequestException, URISyntaxException, ExternalCommunicationException {

        // Create PaymentCredential request form Payline request
        PaymentCredentialGetRequest paymentCredentialGetRequest = this.requestBuilder.fromRedirectionPaymentRequest(paymentRequest);

        // Send PaymentCredential request
        String hostKey = paymentRequest.getEnvironment().isSandbox() ? PARTNER_URL_API_SANDBOX : PARTNER_URL_API_PROD;
        String host = paymentRequest.getPartnerConfiguration().getProperty(hostKey);
        String path = GET_PAYMENT_CREDENTIALS_PATH + "/" + paymentCredentialGetRequest.getId();

        // Build the request query attributes map
        Map<String, String> queryAttributes = new HashMap<>();
        queryAttributes.put(SERVICE_ID, paymentCredentialGetRequest.getServiceId());

        return this.httpClient.doGet(host, path, queryAttributes, redirectionPaymentRequest.getTransactionId());

    }

    @Override
    public PaymentResponse processResponse(StringResponse response) {

        // Parse response
        PaymentCredentialGetResponse paymentCredentialGetResponse = new PaymentCredentialGetResponse.Builder().fromJson(response.getContent());

        if (paymentCredentialGetResponse.isResultOk()) {
            try {
                // get data for decryption step
                String cipheredData = paymentCredentialGetResponse.getData3DS().getData();
                String property = redirectionPaymentRequest.getEnvironment().isSandbox()? PARTNER_PRIVATE_KEY_SANDBOX: PARTNER_PRIVATE_KEY_PROD;
                byte[] privateKey = redirectionPaymentRequest.getPartnerConfiguration().getProperty(property).getBytes();

                // Decrypt 3DS data to retrieve Payment Mode info
                String cardData = jweDecrypt.getDecryptedData(cipheredData, privateKey);
                DecryptedCard decryptedCard = new DecryptedCard.Builder().fromJson(cardData);

                Card card = Card.CardBuilder.aCard()
                        .withPan(decryptedCard.getTokenPan())
                        .withExpirationDate(YearMonth.of(decryptedCard.getExpiryYear(), decryptedCard.getExpiryMonth()))
                        .build();

                PaymentModeCard paymentModeCard = PaymentModeCard.PaymentModeCardBuilder.aPaymentModeCard()
                        .withCard(card)
                        .build();

                return PaymentResponseDoPayment.PaymentResponseDoPaymentBuilder.aPaymentResponseDoPayment()
                        .withPaymentMode(paymentModeCard)
                        .withPartnerTransactionId(getPartnerTransactionId())
                        .build();
            } catch (DecryptException e) {
                LOGGER.error("Unable to decrypt data", e);
                return PaymentResponseFailure.PaymentResponseFailureBuilder
                        .aPaymentResponseFailure()
                        .withFailureCause(FailureCause.INVALID_FIELD_FORMAT)
                        .withPartnerTransactionId(getPartnerTransactionId())
                        .build();
            }

        } else {
            return this.processGenericErrorResponse(paymentCredentialGetResponse);
        }

    }

    public String getPartnerTransactionId() {
        return this.redirectionPaymentRequest.getTransactionId();
    }

    public void setRedirectionPaymentRequest(RedirectionPaymentRequest redirectionPaymentRequest) {
        this.redirectionPaymentRequest = redirectionPaymentRequest;
    }
}
