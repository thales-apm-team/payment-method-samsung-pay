package com.payline.payment.samsung.pay.bean.rest.request.nesteed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thales on 20/08/2018.
 */
public class Amount {

    @SerializedName("option")
    private String option;

    @SerializedName("currency")
    private String currency;

    @SerializedName("total")
    private String total;

    /**
     * Public default constructor
     */
    public Amount() { }

    /**
     * Constructor
     */
    public Amount(String option,
                  String currency,
                  String total) {

        this.option     = option;
        this.currency   = currency;
        this.total      = total;

    }

    public Amount option(String option) {
        this.option = option;
        return this;
    }

    public Amount currency(String currency) {
        this.currency = currency;
        return this;
    }

    public Amount total(String total) {
        this.total = total;
        return this;
    }

}