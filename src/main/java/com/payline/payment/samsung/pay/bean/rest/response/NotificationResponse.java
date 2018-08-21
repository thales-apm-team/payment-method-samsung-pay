package com.payline.payment.samsung.pay.bean.rest.response;

import com.google.gson.Gson;

/**
 * Created by Thales on 20/08/2018.
 */
public class NotificationResponse extends AbstractJsonResponse {

    /**
     * Constructor
     */
    protected NotificationResponse() {
        super();
    }

    public static class Builder {
        public NotificationResponse fromJson( String jsonContent ) {
            Gson gson = new Gson();
            return gson.fromJson( jsonContent, NotificationResponse.class );
        }
    }

}