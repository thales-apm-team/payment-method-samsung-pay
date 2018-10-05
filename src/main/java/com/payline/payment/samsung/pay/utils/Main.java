package com.payline.payment.samsung.pay.utils;

import java.io.IOException;

import com.payline.payment.samsung.pay.bean.rest.response.NotificationPostResponse;

/**
 * Created by Thales on 17/08/2018.
 */
public class Main {

    public static void main( String[] args ) throws IOException {

        //##############################################################################################################
        // Test CreateTransactionPostRequest Object to Json
//        List<String> allowedBrands = new ArrayList<>();
//        allowedBrands.add("VI");
//        allowedBrands.add("MC");
//
//        CreateTransactionPostRequest request = new CreateTransactionPostRequest(
//                "",
//                new PaymentDetails()
//                        .service(
//                                new Service().id("")
//                        )
//                        .orderNumber("DSTRF345789dsgTY")
//                        .recurring(new Boolean(true))
//                        .protocol(
//                                new Protocol()
//                                        .type(PAYMENT_DETAILS__PROTOCOL_TYPE)
//                                        .version(PAYMENT_DETAILS__PROTOCOL_VERSION)
//                        )
//                        .amount(
//                                new Amount()
//                                        .option("FORMAT_TOTAL_ESTIMATED_AMOUNT")
//                                        .currency("USD")
//                                        .total(new BigInteger("300"))
//                        )
//                        .merchant(
//                                new Merchant().name("virtual shop").url("virtualshop.com").reference("xn7qfnd")
//                        )
//                        .allowedBrands(allowedBrands)
//        );
//
//        String requestBody = request.buildBody();
//
//        System.out.println(requestBody);

        //##############################################################################################################
        // Test CreateTransactionPostResponse Json to Object
//        String jsonResponse = "{\n" +
//                "\t\"resultCode\": \"0\",\n" +
//                "\t\"resultMessage\": \"SUCCESS\",\n" +
//                "\t\"id\": \"09407c0a5eb642c6813ac0d\",\n" +
//                "\t\"href\": \"http://localhost:48080/onlinepay/\",\n" +
//                "\t\"encInfo\": {\n" +
//                "\t\t\"mod\": \"bc0dc52cbf0599c8fahz2dz6876zd2h867zdht872dzt687dz2h687dz6872dz2698hdz4hz2d68hdz7dt5dh7zt86h7dz687z1d687tdz687d1h6aq7h6za87e61f8zth\",\n" +
//                "\t\t\"exp\": \"10001\",\n" +
//                "\t\t\"keyId\": \"DO-YUNWOO81-K05_23c5814aa07946f2ae3c\"\n" +
//                "\t}\n" +
//                "}";
//
//        CreateTransactionPostResponse response = new CreateTransactionPostResponse.Builder().fromJson(jsonResponse);
//
//        System.out.println(response);

        //##############################################################################################################
        // Test PaymentCredentialGetRequest


        //##############################################################################################################
        // Test PaymentCredentialGetResponse Json to Object
//        String jsonResponse = "{\n" +
//                "\t\"resultCode\": \"0\",\n" +
//                "\t\"resultMessage\": \"SUCCESS\",\n" +
//                "\t\"method\": \"3DS\",\n" +
//                "\t\"cardBrand\": \"MC\",\n" +
//                "\t\"cardLast4digit\": \"1234\",\n" +
//                "\t\"3DS\": {\n" +
//                "\t\t\"type\": \"S\",\n" +
//                "\t\t\"version\": \"100\",\n" +
//                "\t\t\"data\": \"bc0dc52cbf0599c8fahz2dz6876zd2h867zdht872dzt687dz2h687dz6872dz2698hdz4hz2d68hdz7dt5dh7zt86h7dz687z1d687tdz687d1h6aq7h6za87e61f8zth\"\n" +
//                "\t},\n" +
//                "\t\"certificates\": [\n" +
//                "\t\t{\n" +
//                "\t\t\t\"usage\": \"CA\",\n" +
//                "\t\t\t\"alias\": \"ca_cert\",\n" +
//                "\t\t\t\"content\": \"cert_content\"\n" +
//                "\t\t},\n" +
//                "\t\t{\n" +
//                "\t\t\t\"usage\": \"VER\",\n" +
//                "\t\t\t\"alias\": \"verification_cert\",\n" +
//                "\t\t\t\"content\": \"cert_content\"\n" +
//                "\t\t}\n" +
//                "\t]\n" +
//                "}";
//
//        PaymentCredentialGetResponse response = new PaymentCredentialGetResponse.Builder().fromJson(jsonResponse);
//
//        System.out.println(response);

        //##############################################################################################################
        // Test NotificationPostRequest


        //##############################################################################################################
        // Test NotificationPostResponse Json to Object
        String jsonResponse = "{\n" +
                "\t\"resultCode\": \"0\",\n" +
                "\t\"resultMessage\": \"SUCCESS\"\n" +
                "}";

        NotificationPostResponse response = new NotificationPostResponse.Builder().fromJson(jsonResponse);

        System.out.println(response);

    }

}