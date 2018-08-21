package com.payline.payment.samsung.pay.utils;

/**
 * Created by Thales on 16/08/2018.
 */
public class SamsungPayConstants {

    // Request attributes
    public static final String ID               = "id";
    public static final String SERVICE          = "service";
    public static final String ORDER_NUMBER     = "orderNumber";
    public static final String RECURRING        = "recurring";
    public static final String PROTOCOL         = "protocol";
    public static final String TYPE             = "type";
    public static final String VERSION          = "version";
    public static final String AMOUNT           = "amount";
    public static final String OPTION           = "option";
    public static final String CURRENCY         = "currency";
    public static final String TOTAL            = "total";
    public static final String MERCHANT         = "merchant";
    public static final String REFERENCE        = "reference";
    public static final String URL              = "url";
    public static final String NAME             = "name";
    public static final String ALLOWED_BRANDS   = "allowedBrands";


    public static final String CONTRACT_CONFIG__MERCHANT_NAME = "merchantName";

    public static final String CONTRACT_CONFIG__MERCHANT_NAME_PROPERTY_LABEL        = "contractConfiguration.merchantName.label";
    public static final String CONTRACT_CONFIG__MERCHANT_NAME_PROPERTY_DESCRIPTION  = "contractConfiguration.merchantName.description";


    public static final String PARTNER_CONFIG__SERVICE_ID = "serviceId";


    public static final String CONFIG__HTTP_CONNECT_TIMEOUT                 = "http.connectTimeout";
    public static final String CONFIG__HTTP_WRITE_TIMEOUT                   = "http.writeTimeout";
    public static final String CONFIG__HTTP_READ_TIMEOUT                    = "http.readTimeout";
    public static final String CONFIG__SHEME                                = "samsungpay.scheme";
    public static final String CONFIG__HOST                                 = "samsungpay.host";
    public static final String CONFIG__PATH_TRANSACTION                     = "ops/v1/transactions";
    public static final String CONFIG__PATH_TRANSACTION_PAYMENT_CREDENTIAL  = "ops/v1/transactions/paymentCredentials";
    public static final String CONFIG__PATH_NOTIFICATION                    = "ops/v1/notifications";


    public static final String PAYMENT_DETAILS__PROTOCOL_TYPE       = "3DS";
    public static final String PAYMENT_DETAILS__PROTOCOL_VERSION    = "80";

    public static final String PAYMENT__PROVIDER = "PAYLINE";


}