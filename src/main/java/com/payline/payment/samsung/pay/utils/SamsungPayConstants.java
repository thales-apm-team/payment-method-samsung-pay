package com.payline.payment.samsung.pay.utils;

/**
 * Created by Thales on 16/08/2018.
 */
public class SamsungPayConstants {

    public static final String DEFAULT_ERROR_CODE = "no code transmitted";

    // Request attributes
    public static final String ID               = "id";
    public static final String SERVICE          = "service";
    public static final String SERVICE_ID       = "serviceId";
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
    public static final String MOD              = "mod";
    public static final String EXP              = "exp";
    public static final String KEY_ID           = "keyId";
    public static final String TIMEOUT          = "timeout";

    public static final String CONTRACT_CONFIG__MERCHANT_NAME = "merchantName";

    public static final String CONTRACT_CONFIG__MERCHANT_NAME_PROPERTY_LABEL        = "contractConfiguration.merchantName.label";
    public static final String CONTRACT_CONFIG__MERCHANT_NAME_PROPERTY_DESCRIPTION  = "contractConfiguration.merchantName.description";

    public static final String PARTNER_CONFIG__SERVICE_ID = "serviceId";

    public static final String CONFIG__HTTP_CONNECT_TIMEOUT                 = "http.connectTimeout";
    public static final String CONFIG__HTTP_WRITE_TIMEOUT                   = "http.writeTimeout";
    public static final String CONFIG__HTTP_READ_TIMEOUT                    = "http.readTimeout";
    public static final String CONFIG__SHEME                                = "samsungpay.scheme";
    public static final String CONFIG__HOST                                 = "samsungpay.host";
    public static final String CONFIG__PATH_TRANSACTION                     = "samsungpay.transaction.path";
    public static final String CONFIG__PATH_TRANSACTION_PAYMENT_CREDENTIAL  = "samsungpay.transaction.paymentCredential.path";
    public static final String CONFIG__PATH_NOTIFICATION                    = "samsungpay.notification.pat";

    public static final String PAYMENT_DETAILS__PROTOCOL_TYPE       = "3DS";
    public static final String PAYMENT_DETAILS__PROTOCOL_VERSION    = "80";

    public static final String PAYMENT__PROVIDER = "PAYLINE";

    public static final int HTTP_OK = 200;

    public static final String HTTP_CODE_200 = "200";
    public static final String HTTP_CODE_400 = "400";
    public static final String HTTP_CODE_404 = "404";
    public static final String HTTP_CODE_409 = "409";
    public static final String HTTP_CODE_500 = "500";

    public static final String SAMSUNG_PAY_CODE_0 = "0";
    public static final String SAMSUNG_PAY_CODE_OPM1N1001 = "OPM1N1001";
    public static final String SAMSUNG_PAY_CODE_OPM1N1002 = "OPM1N1002";
    public static final String SAMSUNG_PAY_CODE_OPM1N1003 = "OPM1N1003";
    public static final String SAMSUNG_PAY_CODE_OPM1N1026 = "OPM1N1026";
    public static final String SAMSUNG_PAY_CODE_OPM1N1033 = "OPM1N1033";
    public static final String SAMSUNG_PAY_CODE_OPM1N1035 = "OPM1N1035";
    public static final String SAMSUNG_PAY_CODE_OPM2N1103 = "OPM2N1103";
    public static final String SAMSUNG_PAY_CODE_OPM3N1002 = "OPM3N1002";
    public static final String SAMSUNG_PAY_CODE_OPM3N1003 = "OPM3N1003";
    public static final String SAMSUNG_PAY_CODE_OPM5N9001 = "OPM5N9001";

    public static final String SAMSUNG_PAY_CODE_0_DESCRIPTION = "SUCCESS";
    public static final String SAMSUNG_PAY_CODE_OPM1N1001_DESCRIPTION = "Mandatory parameter does not exist";
    public static final String SAMSUNG_PAY_CODE_OPM1N1002_DESCRIPTION = "Parameter value is invalid";
    public static final String SAMSUNG_PAY_CODE_OPM1N1003_DESCRIPTION = "Parameter format is invalid or Could not read document";
    public static final String SAMSUNG_PAY_CODE_OPM1N1026_DESCRIPTION = "Requested is inconsistent with Id";
    public static final String SAMSUNG_PAY_CODE_OPM1N1033_DESCRIPTION = "This partner is not approved status";
    public static final String SAMSUNG_PAY_CODE_OPM1N1035_DESCRIPTION = "Service does not exist";
    public static final String SAMSUNG_PAY_CODE_OPM2N1103_DESCRIPTION = "This transaction already bound to device";
    public static final String SAMSUNG_PAY_CODE_OPM3N1002_DESCRIPTION = "Data encryption error";
    public static final String SAMSUNG_PAY_CODE_OPM3N1003_DESCRIPTION = "Internal server processing error";
    public static final String SAMSUNG_PAY_CODE_OPM5N9001_DESCRIPTION = "DB data processing error";


}