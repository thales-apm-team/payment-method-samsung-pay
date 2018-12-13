package com.payline.payment.samsung.pay.bean.rest.response.nesteed;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class DecryptedCard {

    @SerializedName("amount")
    private String amount;

    @SerializedName("cryptogram")
    private String cryptogram;

    @SerializedName("currency_code")
    private String currencyCode;

    @SerializedName("eci_indicator")
    private String eciIndicator;

    @SerializedName("tokenPanExpiration")
    private String tokenPanExpiration;

    @SerializedName("utc")
    private String utc;

    @SerializedName("tokenPAN")
    private String tokenPan;

    public String getAmount() {
        return amount;
    }

    public String getCryptogram() {
        return cryptogram;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getEciIndicator() {
        return eciIndicator;
    }

    public String getTokenPanExpiration() {
        return tokenPanExpiration;
    }

    public String getUtc() {
        return utc;
    }

    public String getTokenPan() {
        return tokenPan;
    }

    public int getExpiryYear() {
        return Integer.parseInt(this.tokenPanExpiration.substring(2, 4));
    }

    public int getExpiryMonth() {
        return Integer.parseInt(this.tokenPanExpiration.substring(0, 2));
    }


    public static final class Builder {
        public DecryptedCard fromJson(String jsonContent) {
            Gson gson = new Gson();
            return gson.fromJson(jsonContent, DecryptedCard.class);
        }
    }

}
