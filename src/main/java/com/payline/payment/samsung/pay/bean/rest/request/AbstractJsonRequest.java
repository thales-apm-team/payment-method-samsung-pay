package com.payline.payment.samsung.pay.bean.rest.request;

import com.google.gson.Gson;

/**
 * Created by Thales on 17/08/2018.
 */
public abstract class AbstractJsonRequest {

    public String buildBody() {
        return new Gson().toJson(this);
    }

}