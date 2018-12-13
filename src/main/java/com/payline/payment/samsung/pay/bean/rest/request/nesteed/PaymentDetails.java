package com.payline.payment.samsung.pay.bean.rest.request.nesteed;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thales on 20/08/2018.
 */
public class PaymentDetails {

    @SerializedName("service")
    private Service service;

    @SerializedName("orderNumber")
    private String orderNumber;

    @SerializedName("recurring")
    private Boolean recurring;

    @SerializedName("protocol")
    private Protocol protocol;

    @SerializedName("amount")
    private Amount amount;

    @SerializedName("merchant")
    private Merchant merchant;

    @SerializedName("allowedBrands")
    private List<String> allowedBrands;

    /**
     * Public default constructor
     */
    public PaymentDetails() { }

    /**
     * Constructor
     */
    public PaymentDetails(Service service,
                          String orderNumber,
                          Boolean recurring,
                          Protocol protocol,
                          Amount amount,
                          Merchant merchant,
                          List<String> allowedBrands) {

        this.service        = service;
        this.orderNumber    = orderNumber;
        this.recurring      = recurring;
        this.protocol       = protocol;
        this.amount         = amount;
        this.merchant       = merchant;
        this.allowedBrands  = allowedBrands;

    }

    public PaymentDetails service(Service service) {
        this.service = service;
        return this;
    }

    public PaymentDetails orderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public PaymentDetails recurring(Boolean recurring) {
        this.recurring = recurring;
        return this;
    }

    public PaymentDetails protocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public PaymentDetails amount(Amount amount) {
        this.amount = amount;
        return this;
    }

    public PaymentDetails merchant(Merchant merchant) {
        this.merchant = merchant;
        return this;
    }

    public PaymentDetails allowedBrands(List<String> allowedBrands) {
        this.allowedBrands = allowedBrands;
        return this;
    }

}