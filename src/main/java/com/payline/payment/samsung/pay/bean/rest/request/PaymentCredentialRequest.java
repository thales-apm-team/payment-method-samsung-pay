package com.payline.payment.samsung.pay.bean.rest.request;

import com.payline.payment.samsung.pay.exception.InvalidRequestException;
import com.payline.pmapi.bean.payment.ContractProperty;
import com.payline.pmapi.bean.payment.Order;
import com.payline.pmapi.bean.payment.PaylineEnvironment;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.request.RedirectionPaymentRequest;

import java.util.Map;

import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.CONTRACT_CONFIG__MERCHANT_NAME;
import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.PARTNER_CONFIG__SERVICE_ID;

/**
 * Created by Thales on 16/08/2018.
 */
public class PaymentCredentialRequest {

    private String id;

    private String serviceId;

    protected PaymentCredentialRequest(String id,
                                       String serviceId) {

        this.id         = id;
        this.serviceId  = serviceId;

    }

    public String getId() {
        return this.id;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public static class Builder {

        public PaymentCredentialRequest fromRedirectionPaymentRequest(RedirectionPaymentRequest paylineRequest) throws InvalidRequestException {

            // Check the input request for NPEs and mandatory fields
            this.checkInputRequest(paylineRequest);

            // Instantiate the PaymentCredentialRequest from input request
            PaymentCredentialRequest request = new PaymentCredentialRequest(
                    paylineRequest.getTransactionId(),
                    paylineRequest.getPartnerConfiguration().getProperty(PARTNER_CONFIG__SERVICE_ID)
            );

            return request;

        }

        /**
         * Verifies that the input request contains all the required fields.
         *
         * @param paymentRequest The input request
         * @throws InvalidRequestException If recovering the field value would result in a NPE or if the value is null or empty.
         */
        private void checkInputRequest(RedirectionPaymentRequest paymentRequest) throws InvalidRequestException {
            if ( paymentRequest == null ) {
                throw new InvalidRequestException( "Request must not be null" );
            }

            // Check attributes from PaymentRequest
            if ( paymentRequest.getTransactionId() == null ) {
                throw new InvalidRequestException( "Missing PaymentRequest property: transaction id" );
            }

            // Check attributes from PaymentRequest.PartnerConfiguration
            if ( paymentRequest.getPartnerConfiguration() == null ) {
                throw new InvalidRequestException( "PartnerConfiguration properties object must not be null" );
            }
            if ( paymentRequest.getPartnerConfiguration().getProperty(PARTNER_CONFIG__SERVICE_ID) == null ) {
                throw new InvalidRequestException( "Missing PartnerConfiguration property: service id" );
            }

        }

    }

}