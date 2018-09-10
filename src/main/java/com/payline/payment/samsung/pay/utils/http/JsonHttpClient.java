package com.payline.payment.samsung.pay.utils.http;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.*;

public class JsonHttpClient extends HttpClient {

    /**
     * Instantiate a HTTP client.
     *
     * @param connectTimeout Default connect timeout (in seconds) for new connections. A value of 0 means no timeout.
     * @param writeTimeout   Default write timeout (in seconds) for new connections. A value of 0 means no timeout.
     * @param readTimeout    Default read timeout (in seconds) for new connections. A value of 0 means no timeout.
     */
    public JsonHttpClient( int connectTimeout, int writeTimeout, int readTimeout ) {
        super( connectTimeout, writeTimeout, readTimeout );
    }

    /**
     * Send a POST request, with a JSON content type.
     *
     * @param scheme URL scheme
     * @param host URL host
     * @param path URL path
     * @param jsonContent The JSON content, as a string
     * @return The response returned from the HTTP call
     * @throws IOException
     */
    public Response doPost(String scheme, String host, String path, String jsonContent ) throws IOException {
        // FIXME : Cf. Confluence Q4 - Use the right X-Request-Id
        String requestId = "0123456789";
        RequestBody body = RequestBody.create( MediaType.parse( APPLICATION_JSON ), jsonContent );
        return super.doPost( scheme, host, path, body, APPLICATION_JSON, requestId );
    }

    /**
     * Send a GET request.
     *
     * @param scheme URL scheme
     * @param host URL host
     * @param path URL path
     * @return The response returned from the HTTP call
     * @throws IOException
     */
    public Response doGet(String scheme, String host, String path, Map<String, String> queryAttributes ) throws IOException {
        // FIXME : Cf. Confluence Q4 - Use the right X-Request-Id
        String requestId = "0123456789";
        return super.doGet( scheme, host, path, queryAttributes, CONTENT_TYPE, requestId );
    }

}
