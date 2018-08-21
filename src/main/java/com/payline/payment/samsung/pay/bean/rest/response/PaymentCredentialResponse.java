package com.payline.payment.samsung.pay.bean.rest.response;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.payline.payment.samsung.pay.bean.rest.response.nesteed.Certificate;
import com.payline.payment.samsung.pay.bean.rest.response.nesteed.Data3DS;

/**
 * Created by Thales on 20/08/2018.
 */
public class PaymentCredentialResponse extends AbstractJsonResponse {

    @SerializedName("method")
    private String method;

    @SerializedName("cardBrand")
    private String cardBrand;

    @SerializedName("cardLast4digit")
    private String cardLast4digit;

    @SerializedName("3DS")
    private Data3DS data3DS;

    @SerializedName("certificates")
    private List<Certificate> certificates;

    /**
     * Constructor
     */
    protected PaymentCredentialResponse() {
        super();
    }

    public String getMethod() {
        return this.method;
    }

    public String getCardBrand() {
        return this.cardBrand;
    }

    public String getCardLast4digit() {
        return this.cardLast4digit;
    }

    public Data3DS getData3DS() {
        return this.data3DS;
    }

    public List<Certificate> getCertificates() {
        return this.certificates;
    }

    public static class Builder {
        public PaymentCredentialResponse fromJson( String jsonContent ) {
            Gson gson = new Gson();
            return gson.fromJson( jsonContent, PaymentCredentialResponse.class );
        }
    }

}