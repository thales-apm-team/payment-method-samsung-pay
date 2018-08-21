package com.payline.payment.samsung.pay.service;

import com.payline.pmapi.bean.payment.request.RedirectionPaymentRequest;
import com.payline.pmapi.bean.payment.request.TransactionStatusRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.service.PaymentWithRedirectionService;

/**
 * Created by Thales on 16/08/2018.
 */
public class PaymentWithRedirectionServiceImpl implements PaymentWithRedirectionService {

    @Override
    public PaymentResponse finalizeRedirectionPayment(RedirectionPaymentRequest redirectionPaymentRequest) {
        return null;
    }

    @Override
    public PaymentResponse handleSessionExpired(TransactionStatusRequest transactionStatusRequest) {
        return null;
    }

}
