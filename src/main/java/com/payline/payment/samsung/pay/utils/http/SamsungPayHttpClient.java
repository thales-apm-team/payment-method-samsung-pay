package com.payline.payment.samsung.pay.utils.http;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class SamsungPayHttpClient extends AbstractHttpClient {
    private static final SamsungPayHttpClient instance = new SamsungPayHttpClient();

    private SamsungPayHttpClient() {
        super( 10, 10, 15 );
    }

    /**
     * Instantiate a HTTP client.
     *
     * @param connectTimeout Default connect timeout (in seconds) for new connections. A value of 0 means no timeout.
     * @param writeTimeout   Default write timeout (in seconds) for new connections. A value of 0 means no timeout.
     * @param readTimeout    Default read timeout (in seconds) for new connections. A value of 0 means no timeout.
     */
    private SamsungPayHttpClient(int connectTimeout, int writeTimeout, int readTimeout ) {
        super( connectTimeout, writeTimeout, readTimeout );
    }

    public static SamsungPayHttpClient getInstance(){
        return instance;
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
    public StringResponse doPost(String scheme, String host, String path, String jsonContent ) throws IOException, URISyntaxException {

        // FIXME : Cf. Confluence Q4 - Use the right X-Request-Id
        String requestId = "0123456789";

        StringEntity entity = new StringEntity(jsonContent);

        return super.doPost( scheme, host, path, entity, ContentType.APPLICATION_JSON.toString(), requestId );

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
    public StringResponse doGet(String scheme, String host, String path, Map<String, String> queryAttributes ) throws IOException, URISyntaxException {

        // FIXME : Cf. Confluence Q4 - Use the right X-Request-Id
        String requestId = "0123456789";

        return super.doGet( scheme, host, path, queryAttributes, ContentType.APPLICATION_JSON.toString(), requestId );

    }

}
