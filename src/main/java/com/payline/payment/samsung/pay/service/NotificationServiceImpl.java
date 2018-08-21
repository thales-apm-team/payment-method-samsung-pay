package com.payline.payment.samsung.pay.service;

import com.payline.pmapi.bean.notification.request.NotificationRequest;
import com.payline.pmapi.bean.notification.response.NotificationResponse;
import com.payline.pmapi.bean.payment.request.NotifyTransactionStatusRequest;
import com.payline.pmapi.service.NotificationService;

/**
 * Created by Thales on 16/08/2018.
 */
public class NotificationServiceImpl implements NotificationService {

    @Override
    public NotificationResponse parse(NotificationRequest notificationRequest) {
        return null;
    }

    @Override
    public void notifyTransactionStatus(NotifyTransactionStatusRequest notifyTransactionStatusRequest) {

    }

}
