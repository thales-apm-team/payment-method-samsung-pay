package com.payline.payment.samsung.pay.bean.rest.request.nesteed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thales on 20/08/2018.
 */
public class Service {

    @SerializedName("id")
    private String id;

    /**
     * Public default constructor
     */
    public Service() { }

    /**
     * Constructor
     */
    public Service(String id) {
        this.id = id;
    }

    public Service id (String id) {
        this.id = id;
        return this;
    }

}