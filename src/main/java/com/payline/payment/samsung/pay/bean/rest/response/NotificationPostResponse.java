package com.payline.payment.samsung.pay.bean.rest.response;

import com.google.gson.Gson;

/**
 * Created by Thales on 20/08/2018.
 */
public class NotificationPostResponse extends AbstractJsonResponse {

    /**
     * Constructor
     */
    protected NotificationPostResponse() {
        super();
    }

    public static final class Builder {
        public NotificationPostResponse fromJson(String jsonContent ) {
            Gson gson = new Gson();
            return gson.fromJson( jsonContent, NotificationPostResponse.class );
        }
    }

}