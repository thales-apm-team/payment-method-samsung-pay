package com.payline.payment.samsung.pay.bean.rest.request.nesteed;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thales on 21/08/2018.
 */
public class Payment {

    @SerializedName("reference")
    private String reference;

    @SerializedName("status")
    private String status;

    @SerializedName("provider")
    private String provider;

    @SerializedName("merchant")
    private Merchant merchant;

    /**
     * Public default constructor
     */
    public Payment() { }

    /**
     * Constructor
     */
    public Payment(String reference,
                   String status,
                   String provider,
                   Merchant merchant) {

        this.reference  = reference;
        this.status     = status;
        this.provider   = provider;
        this.merchant   = merchant;

    }

    public Payment reference(String reference) {
        this.reference = reference;
        return this;
    }

    public Payment status(String status) {
        this.status = status;
        return this;
    }

    public Payment provider(String provider) {
        this.provider = provider;
        return this;
    }

    public Payment merchant(Merchant merchant) {
        this.merchant = merchant;
        return this;
    }

}