package com.payline.payment.samsung.pay.bean.rest.request.nesteed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thales on 20/08/2018.
 */
public class Merchant {

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    @SerializedName("reference")
    private String reference;

    /**
     * Public default constructor
     */
    public Merchant() { }

    /**
     * Constructor
     */
    public Merchant(String name,
                    String url,
                    String reference) {

        this.name       = name;
        this.url        = url;
        this.reference  = reference;

    }

    public Merchant name(String name) {
        this.name = name;
        return this;
    }

    public Merchant url(String url) {
        this.url = url;
        return this;
    }

    public Merchant reference(String reference) {
        this.reference = reference;
        return this;
    }

}