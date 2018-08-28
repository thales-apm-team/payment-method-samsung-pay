package com.payline.payment.samsung.pay.utils.http;

import okhttp3.*;

/**
 * Utility test class which enable to mock {@link Response} objects.
 */
public class ResponseMocker {

    private static final Protocol TEST_HTTP_PROTOCOL = Protocol.HTTP_1_1;

    public static Response mock( int httpCode, String httpMessage, String jsonBody ){
        return ( new Response.Builder() )
                .code( httpCode )
                .message( httpMessage )
                .body( jsonBody == null ? null : ResponseBody.create( MediaType.parse( "application/json" ), jsonBody ) )
                .request( (new Request.Builder()).url("http://fake.fr").build() )
                .protocol( TEST_HTTP_PROTOCOL )
                .build();
    }

}
