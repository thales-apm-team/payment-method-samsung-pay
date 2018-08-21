package com.payline.payment.samsung.pay.bean.rest.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

/**
 * Created by Thales on 17/08/2018.
 */
public abstract class AbstractJsonRequest {

    public String buildBody() {
        //return new GsonBuilder().create().toJson(this);
        return new Gson().toJson(this);
    }

}