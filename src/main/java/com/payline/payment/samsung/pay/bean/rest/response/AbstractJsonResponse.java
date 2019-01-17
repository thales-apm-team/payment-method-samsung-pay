package com.payline.payment.samsung.pay.bean.rest.response;

import com.google.gson.annotations.SerializedName;
import com.payline.payment.samsung.pay.utils.type.WSRequestResultEnum;

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
    protected AbstractJsonResponse() { }

    public String getResultCode() {
        return this.resultCode;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }

    /**
     * Check the response result
     *
     * @return true if result ok (no error - 0/SUCCESS), false if result KO
     */
    public boolean isResultOk() {
        boolean result =  false;
        if (WSRequestResultEnum.RESULT_0.equals(WSRequestResultEnum.fromResultCodeValue(this.getResultCode()))) {
            result = true;
        }
        return result;
    }

}