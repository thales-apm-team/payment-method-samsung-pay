package com.payline.payment.samsung.pay.bean.rest.request;

import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.*;

import java.util.Map;

import com.google.gson.annotations.SerializedName;
import com.payline.payment.samsung.pay.bean.rest.request.nesteed.*;
import com.payline.payment.samsung.pay.exception.InvalidRequestException;
import com.payline.pmapi.bean.payment.ContractProperty;
import com.payline.pmapi.bean.payment.Order;
import com.payline.pmapi.bean.payment.PaylineEnvironment;
import com.payline.pmapi.bean.payment.request.PaymentRequest;

/**
 * Created by Thales on 16/08/2018.
 */
public class CreateTransactionRequest extends AbstractJsonRequest {

    @SerializedName("callback")
    private String callback;

    @SerializedName( "paymentDetails" )
    private PaymentDetails paymentDetails;

    /**
     * Constructor
     */
    protected CreateTransactionRequest(String callback,
                                       PaymentDetails paymentDetails) {

        this.callback           = callback;
        this.paymentDetails     = paymentDetails;

    }

    public static class Builder {

        public CreateTransactionRequest fromPaymentRequest(PaymentRequest paylineRequest) throws InvalidRequestException {

            // Check the input request for NPEs and mandatory fields
            this.checkInputRequest(paylineRequest);

            // Instantiate the CreateTransactionRequest from input request
            CreateTransactionRequest request = new CreateTransactionRequest(
                    paylineRequest.getPaylineEnvironment().getRedirectionReturnURL(),
                    this.getPaymentDetailsFromPaymentRequest(paylineRequest)
            );

            return request;

        }

        /**
         * Verifies that the input request contains all the required fields.
         *
         * @param paylineRequest The input request
         * @throws InvalidRequestException If recovering the field value would result in a NPE or if the value is null or empty.
         */
        private void checkInputRequest(PaymentRequest paylineRequest) throws InvalidRequestException {
            if ( paylineRequest == null ) {
                throw new InvalidRequestException( "Request must not be null" );
            }

            // Check attributes from PaymentRequest.Amount
            if ( paylineRequest.getAmount() == null ) {
                throw new InvalidRequestException( "Amount properties object must not be null" );
            }
            com.payline.pmapi.bean.common.Amount amount = paylineRequest.getAmount();
            if ( amount.getAmountInSmallestUnit() == null) {
                throw new InvalidRequestException( "Missing Amount property: amount smallest unit" );
            }
            if ( amount.getCurrency() == null) {
                throw new InvalidRequestException( "Missing Amount property: currency" );
            }

            // Check attributes from PaymentRequest.Order
            if ( paylineRequest.getOrder() == null ) {
                throw new InvalidRequestException( "Order properties object must not be null" );
            }
            Order order = paylineRequest.getOrder();
            if ( order.getReference() == null ) {
                throw new InvalidRequestException( "Missing Order reference property: currency" );
            }

            // Check attributes from PaymentRequest.ContractConfiguration
            if ( paylineRequest.getContractConfiguration() == null
                    || paylineRequest.getContractConfiguration().getContractProperties() == null ) {
                throw new InvalidRequestException( "ContractConfiguration properties object must not be null" );
            }
            Map<String, ContractProperty> contractProperties = paylineRequest.getContractConfiguration().getContractProperties();
            if ( contractProperties.get(CONTRACT_CONFIG__MERCHANT_NAME) == null ) {
                throw new InvalidRequestException( "Missing ContractConfiguration property: merchant name" );
            }

            // Check attributes from PaymentRequest.PaylineEnvironment
            if ( paylineRequest.getPaylineEnvironment() == null ) {
                throw new InvalidRequestException( "PaylineEnvironment properties object must not be null" );
            }
            PaylineEnvironment paylineEnvironment = paylineRequest.getPaylineEnvironment();
            if ( paylineEnvironment.getRedirectionReturnURL() == null
                    || paylineEnvironment.getRedirectionReturnURL().isEmpty() ) {
                throw new InvalidRequestException( "Missing PaylineEnvironment property: redirection return URL" );
            }

            // Check attributes from PaymentRequest.PartnerConfiguration
            if ( paylineRequest.getPartnerConfiguration() == null ) {
                throw new InvalidRequestException( "PartnerConfiguration properties object must not be null" );
            }
            if ( paylineRequest.getPartnerConfiguration().getProperty(PARTNER_CONFIG__SERVICE_ID) == null ) {
                throw new InvalidRequestException( "Missing PartnerConfiguration property: service id" );
            }

        }

        /**
         * Construit la structure de données PaymentDetails à partir de la requête PaymentRequest
         *
         * @param paylineRequest
         * @return
         */
        private PaymentDetails getPaymentDetailsFromPaymentRequest(PaymentRequest paylineRequest) {
            return new PaymentDetails()
                    .service(
                            new Service().id(
                                    paylineRequest
                                            .getPartnerConfiguration()
                                            .getProperty(PARTNER_CONFIG__SERVICE_ID)
                            )
                    )
                    .orderNumber(
                            paylineRequest
                                    .getOrder()
                                    .getReference()
                    )
                    .protocol(
                            new Protocol()
                                    .type(PAYMENT_DETAILS__PROTOCOL_TYPE)
                                    .version(PAYMENT_DETAILS__PROTOCOL_VERSION)
                    )
                    .amount(
                            new Amount()
                                    .currency(
                                            paylineRequest
                                                    .getOrder()
                                                    .getAmount()
                                                    .getCurrency()
                                                    .getCurrencyCode()
                                    )
                                    .total(
                                            paylineRequest
                                                    .getOrder()
                                                    .getAmount()
                                                    .getAmountInSmallestUnit()
                                    )
                    )
                    .merchant(
                            new Merchant()
                                    .name(
                                            paylineRequest
                                                    .getContractConfiguration()
                                                    .getContractProperties()
                                                    .get(CONTRACT_CONFIG__MERCHANT_NAME)
                                                    .getValue()
                                    )
                    );
        }

    }

}