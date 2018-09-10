package com.payline.payment.samsung.pay.utils.http;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.*;

import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.*;

/**
 * This utility class provides a basic HTTP client to send requests, using OkHttp library.
 * It must be extended to match each payment method needs.
 */
public abstract class HttpClient {

    private OkHttpClient client;

    /**
     *  Instantiate a HTTP client.
     *
     * @param connectTimeout Default connect timeout (in seconds) for new connections. A value of 0 means no timeout.
     * @param writeTimeout Default write timeout (in seconds) for new connections. A value of 0 means no timeout.
     * @param readTimeout Default read timeout (in seconds) for new connections. A value of 0 means no timeout.
     */
    protected HttpClient( int connectTimeout, int writeTimeout, int readTimeout ) {

        this.client = new OkHttpClient.Builder()
                .connectTimeout( connectTimeout, TimeUnit.SECONDS )
                .writeTimeout( writeTimeout, TimeUnit.SECONDS )
                .readTimeout( readTimeout, TimeUnit.SECONDS )
                .build();

    }

    /**
     * Send a POST request.
     *
     * @param scheme URL scheme
     * @param host URL host
     * @param path URL path
     * @param body Request body
     * @param contentType The content type of the request body
     * @param requestId The unique identifier for the request
     * @return The response returned from the HTTP call
     * @throws IOException
     */
    protected Response doPost(String scheme, String host, String path, RequestBody body, String contentType, String requestId)
            throws IOException {

        // create url
        HttpUrl url = new HttpUrl.Builder()
                .scheme( scheme )
                .host( host )
                .addPathSegment( path )
                .build();

        // create request
        Request request = new Request.Builder()
                .addHeader( CONTENT_TYPE, contentType )
                .addHeader( X_REQUEST_ID, requestId )
                .url( url )
                .post( body )
                .build();

        // do the request
        return this.client.newCall( request ).execute();

    }

    /**
     * Send a GET request
     *
     * @param scheme URL scheme
     * @param host URL host
     * @param path URL path
     * @param queryAttributes Query attribute map
     * @param contentType The content type of the request body
     * @param requestId The unique identifier for the request
     * @return The response returned from the HTTP call
     * @throws IOException
     */
    protected Response doGet(String scheme, String host, String path, Map<String, String> queryAttributes, String contentType, String requestId)
            throws IOException {

        // create url
        HttpUrl.Builder builder = new HttpUrl.Builder()
                .scheme( scheme )
                .host( host )
                .addPathSegment( path );

        if (queryAttributes != null && !queryAttributes.isEmpty()) {
            for (String key : queryAttributes.keySet()) {
                builder.addEncodedQueryParameter(key, queryAttributes.get(key));
            }
        }

        HttpUrl url = builder.build();

        // create request
        Request request = new Request.Builder()
                .addHeader( CONTENT_TYPE, contentType )
                .addHeader( X_REQUEST_ID, requestId )
                .url( url )
                .get()
                .build();

        // do the request
        return this.client.newCall( request ).execute();

    }

}
