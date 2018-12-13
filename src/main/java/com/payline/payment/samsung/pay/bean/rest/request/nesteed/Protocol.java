package com.payline.payment.samsung.pay.bean.rest.request.nesteed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thales on 20/08/2018.
 */
public class Protocol {

    @SerializedName("type")
    private String type;

    @SerializedName("version")
    private String version;

    /**
     * Public default constructor
     */
    public Protocol() { }

    /**
     * Constructor
     */
    public Protocol(String type,
                    String version) {

        this.type       = type;
        this.version    = version;

    }

    public Protocol type (String type) {
        this.type = type;
        return this;
    }

    public Protocol version (String version) {
        this.version = version;
        return this;
    }

}