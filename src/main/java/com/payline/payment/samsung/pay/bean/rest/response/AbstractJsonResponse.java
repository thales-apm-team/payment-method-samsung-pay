package com.payline.payment.samsung.pay.bean.rest.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thales on 21/08/2018.
 */
public abstract class AbstractJsonResponse {

    @SerializedName("resultCode")
    private String resultCode;

    @SerializedName("resultMessage")
    private String resultMessage;

    /**
     * Constructor
     */
    protected AbstractJsonResponse() {

    }

    public String getResultCode() {
        return this.resultCode;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }

}