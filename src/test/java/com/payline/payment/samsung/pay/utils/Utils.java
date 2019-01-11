package com.payline.payment.samsung.pay.utils;

import com.payline.payment.samsung.pay.utils.http.StringResponse;
import com.payline.pmapi.bean.common.Amount;
import com.payline.pmapi.bean.common.Buyer;
import com.payline.pmapi.bean.configuration.PartnerConfiguration;
import com.payline.pmapi.bean.configuration.request.ContractParametersCheckRequest;
import com.payline.pmapi.bean.payment.*;
import com.payline.pmapi.bean.payment.request.NotifyTransactionStatusRequest;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.request.RedirectionPaymentRequest;
import com.payline.pmapi.bean.payment.request.TransactionStatusRequest;
import com.payline.pmapi.bean.paymentform.request.PaymentFormConfigurationRequest;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.*;

public class Utils {
    private static final Locale FRENCH = Locale.FRENCH;
    private static final String EUR = "EUR";
    public static final String SUCCESS_URL = "https://succesurl.com/";
    public static final String FAILURE_URL = "http://cancelurl.com/";
    public static final String NOTIFICATION_URL = "http://notificationurl.com/";

    public static final String MERCHANT_ID = "virtual shop";
    public static final String SERVICE_ID = "db1294c3c8bc42fe9ce762";

    public static final String SANDBOX_URL_API = "https://api-ops.stg.mpay.samsung.com/";
    public static final String SANDBOX_URL_JS = "https://d35p4vvdul393k.cloudfront.net/sdk_library/us/stg/ops/pc_gsmpi_web_sdk.js";


    public static ContractParametersCheckRequest createContractParametersCheckRequest(String merchantName) {
        return createContractParametersCheckRequestBuilder(merchantName).build();
    }

    public static ContractParametersCheckRequest.CheckRequestBuilder createContractParametersCheckRequestBuilder(String merchantName) {
        Map<String, String> accountInfo = new HashMap<>();
//        accountInfo.put(CONTRACT_CONFIG_MERCHANT_NAME, merchantName);

        ContractConfiguration configuration = createContractConfiguration(merchantName);
        Environment environment = createDefaultPaylineEnvironment();

        return ContractParametersCheckRequest.CheckRequestBuilder.aCheckRequest()
                .withAccountInfo(accountInfo)
                .withLocale(FRENCH)
                .withContractConfiguration(configuration)
                .withEnvironment(environment)
                .withPartnerConfiguration(createDefaultPartnerConfiguration());

    }

    public static PaymentRequest.Builder createCompletePaymentBuilder() {
        final Amount amount = createAmount(EUR);
        final ContractConfiguration contractConfiguration = createContractConfiguration(MERCHANT_ID);
        final Environment paylineEnvironment = new Environment(NOTIFICATION_URL, SUCCESS_URL, FAILURE_URL, true);
        final String transactionID = createTransactionId();
        final Order order = createOrder(transactionID);
        final String softDescriptor = "softDescriptor";
        final Locale locale = Locale.FRANCE;
        final Buyer buyer = createDefaultBuyer();

        return PaymentRequest.builder()
                .withAmount(amount)
                .withBrowser(new Browser("", Locale.FRANCE))
                .withContractConfiguration(contractConfiguration)
                .withEnvironment(paylineEnvironment)
                .withOrder(order)
                .withLocale(locale)
                .withTransactionId(transactionID)
                .withSoftDescriptor(softDescriptor)
                .withBuyer(buyer)
                .withPartnerConfiguration(createDefaultPartnerConfiguration());
    }

    private static String createTransactionId() {
        return "transactionID" + Calendar.getInstance().getTimeInMillis();
    }

    private static Map<Buyer.AddressType, Buyer.Address> createAddresses(Buyer.Address address) {
        Map<Buyer.AddressType, Buyer.Address> addresses = new HashMap<>();
        addresses.put(Buyer.AddressType.DELIVERY, address);
        addresses.put(Buyer.AddressType.BILLING, address);

        return addresses;
    }

    private static Map<Buyer.AddressType, Buyer.Address> createDefaultAddresses() {
        Buyer.Address address = createDefaultAddress();
        return createAddresses(address);
    }

    private static Amount createAmount(String currency) {
        return new Amount(BigInteger.TEN, Currency.getInstance(currency));
    }

    private static Order createOrder(String transactionID) {
        return Order.OrderBuilder.anOrder().withReference(transactionID).build();
    }

    public static Order createOrder(String transactionID, Amount amount) {
        return Order.OrderBuilder.anOrder().withReference(transactionID).withAmount(amount).build();
    }

    private static Buyer.FullName createFullName() {
        return new Buyer.FullName("foo", "bar", "1");
    }

    private static Map<Buyer.PhoneNumberType, String> createDefaultPhoneNumbers() {
        Map<Buyer.PhoneNumberType, String> phoneNumbers = new HashMap<>();
        phoneNumbers.put(Buyer.PhoneNumberType.BILLING, "0606060606");

        return phoneNumbers;
    }

    public static ContractConfiguration createContractConfiguration(String merchantName) {
        final ContractConfiguration contractConfiguration = new ContractConfiguration("", new HashMap<>());
        contractConfiguration.getContractProperties().put(CONTRACT_CONFIG_MERCHANT_NAME, new ContractProperty(merchantName));

        return contractConfiguration;
    }

    public static ContractConfiguration createDefaultContractConfiguration() {
        final ContractConfiguration contractConfiguration = new ContractConfiguration("", new HashMap<>());
        contractConfiguration.getContractProperties().put(CONTRACT_CONFIG_MERCHANT_NAME, new ContractProperty(MERCHANT_ID));
        return contractConfiguration;
    }

    private static Buyer.Address createAddress(String street, String city, String zip) {
        return Buyer.Address.AddressBuilder.anAddress()
                .withStreet1(street)
                .withCity(city)
                .withZipCode(zip)
                .withCountry("country")
                .build();
    }

    private static Buyer.Address createDefaultAddress() {
        return createAddress("a street", "a city", "a zip");
    }

    private static Buyer createBuyer(Map<Buyer.PhoneNumberType, String> phoneNumbers, Map<Buyer.AddressType, Buyer.Address> addresses, Buyer.FullName fullName) {
        return Buyer.BuyerBuilder.aBuyer()
                .withCustomerIdentifier("customerId")
                .withEmail("foo@bar.baz")
                .withPhoneNumbers(phoneNumbers)
                .withAddresses(addresses)
                .withFullName(fullName)
                .build();
    }

    private static Buyer createDefaultBuyer() {
        return createBuyer(createDefaultPhoneNumbers(), createDefaultAddresses(), createFullName());
    }

    public static Environment createDefaultPaylineEnvironment() {
        return new Environment(NOTIFICATION_URL, SUCCESS_URL, FAILURE_URL, true);
    }

    public static PartnerConfiguration createDefaultPartnerConfiguration() {
        String PRIVATE_KEY_STG = null;
        try {
            PRIVATE_KEY_STG = new String(Files.readAllBytes(Paths.get(JweDecrypt.class.getClassLoader().getResource("keystore/encodedPrivateKey.txt").toURI())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> partnerConfigMap = new HashMap<>();
        partnerConfigMap.put(SamsungPayConstants.PARTNER_CONFIG_SERVICE_ID, SERVICE_ID);
        partnerConfigMap.put(SamsungPayConstants.PARTNER_URL_API_SANDBOX, SANDBOX_URL_API);
        partnerConfigMap.put(SamsungPayConstants.PARTNER_URL_JS_SANDBOX, SANDBOX_URL_JS);

        Map<String, String> partnerConfigMapSensitive = new HashMap<>();
        partnerConfigMapSensitive.put(SamsungPayConstants.PARTNER_PRIVATE_KEY_SANDBOX, PRIVATE_KEY_STG);

        return new PartnerConfiguration(partnerConfigMap, partnerConfigMapSensitive);

    }

    public static PaymentFormConfigurationRequest createDefaultPaymentFormConfigurationRequest() {
        return PaymentFormConfigurationRequest.PaymentFormConfigurationRequestBuilder.aPaymentFormConfigurationRequest()
                .withLocale(Locale.FRANCE)
                .withBuyer(createDefaultBuyer())
                .withAmount(new Amount(null, Currency.getInstance(EUR)))
                .withContractConfiguration(createContractConfiguration(MERCHANT_ID))
                .withOrder(createOrder("007"))
                .withEnvironment(createDefaultPaylineEnvironment())
                .withPartnerConfiguration(createDefaultPartnerConfiguration())
                .build();
    }

    public static StringResponse createStringResponse(String body, int code) {
        StringResponse response = new StringResponse();
        response.setCode(code);
        response.setContent(body);
        return response;
    }

    public static RedirectionPaymentRequest createRedirectionPaymentRequest() {
        return createRedirectionPaymentRequestBuilder().build();
    }

    public static RedirectionPaymentRequest.Builder<RedirectionPaymentRequest> createRedirectionPaymentRequestBuilder() {
        final Amount amount = createAmount(EUR);
        final ContractConfiguration contractConfiguration = createContractConfiguration(MERCHANT_ID);
        final Environment paylineEnvironment = new Environment(NOTIFICATION_URL, SUCCESS_URL, FAILURE_URL, true);
        final String transactionID = createTransactionId();
        final Order order = createOrder(transactionID);
        final String softDescriptor = "softDescriptor";
        final Locale locale = Locale.FRANCE;
        final Buyer buyer = createDefaultBuyer();

        final String[] parameterValue = new String[1];
        parameterValue[0] = "thisIsAnId";
        final Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put(REF_ID, parameterValue);

        return (RedirectionPaymentRequest.Builder<RedirectionPaymentRequest>) RedirectionPaymentRequest.builder()
                .withHttpRequestParametersMap(parameterMap)
                .withAmount(amount)
                .withBrowser(new Browser("", Locale.FRANCE))
                .withContractConfiguration(contractConfiguration)
                .withEnvironment(paylineEnvironment)
                .withOrder(order)
                .withLocale(locale)
                .withTransactionId(transactionID)
                .withSoftDescriptor(softDescriptor)
                .withBuyer(buyer)
                .withPartnerConfiguration(createDefaultPartnerConfiguration());
    }

    public static NotifyTransactionStatusRequest createNotifyTransactionRequest() {
        return createNotifyTransactionRequestBuilder().build();
    }

    public static NotifyTransactionStatusRequest.NotifyTransactionStatusRequestBuilder createNotifyTransactionRequestBuilder() {
        return NotifyTransactionStatusRequest.NotifyTransactionStatusRequestBuilder.aNotifyTransactionStatusRequest()
                .withPartnerTransactionId("1")
                .withTransactionSatus(NotifyTransactionStatusRequest.TransactionStatus.SUCCESS)
                .withAmount(createAmount(EUR))
                .withContractConfiguration(createContractConfiguration(MERCHANT_ID))
                .withEnvironment(createDefaultPaylineEnvironment())
                .withPartnerConfiguration(createDefaultPartnerConfiguration());
    }

    public static TransactionStatusRequest createTransactionStatusRequest() {
        String transactionId = createTransactionId();
        return TransactionStatusRequest.TransactionStatusRequestBuilder.aNotificationRequest()
                .withAmount(createAmount(EUR))
                .withOrder(createOrder(transactionId))
                .withBuyer(createDefaultBuyer())
                .withEnvironment(createDefaultPaylineEnvironment())
                .withPartnerConfiguration(createDefaultPartnerConfiguration())
                .withContractConfiguration(createContractConfiguration(MERCHANT_ID))
                .withTransactionId(transactionId)
                .build();
    }
}
