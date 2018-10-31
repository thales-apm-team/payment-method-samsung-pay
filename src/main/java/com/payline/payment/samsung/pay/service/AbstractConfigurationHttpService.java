package com.payline.payment.samsung.pay.service;

import com.payline.payment.samsung.pay.exception.InvalidRequestException;
import com.payline.payment.samsung.pay.utils.config.ConfigProperties;
import com.payline.payment.samsung.pay.utils.http.SamsungPayHttpClient;
import com.payline.payment.samsung.pay.utils.http.StringResponse;
import com.payline.payment.samsung.pay.utils.i18n.I18nService;
import com.payline.pmapi.bean.configuration.request.ContractParametersCheckRequest;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.*;
import static com.payline.pmapi.bean.configuration.request.ContractParametersCheckRequest.GENERIC_ERROR;

/**
 * This abstract service handles the common issues encountered when sending, receiving and processing a {@link ContractParametersCheckRequest} (or subclass)
 * It delegates the specific parts to the classes that will extends it, through the abstract methods.
 * This way, most of the exception handling can be done here, once.
 */
public abstract class AbstractConfigurationHttpService {

    private static final Logger logger = LogManager.getLogger(AbstractConfigurationHttpService.class);

    protected SamsungPayHttpClient httpClient;
    protected I18nService i18n;

    protected AbstractConfigurationHttpService() {

        this.httpClient = new SamsungPayHttpClient(
                Integer.parseInt(ConfigProperties.get(CONFIG__HTTP_CONNECT_TIMEOUT)),
                Integer.parseInt(ConfigProperties.get(CONFIG__HTTP_WRITE_TIMEOUT)),
                Integer.parseInt(ConfigProperties.get(CONFIG__HTTP_READ_TIMEOUT))
        );

        i18n = I18nService.getInstance();


    }

    /**
     * Builds the request, sends it through HTTP using the httpClient and recovers the response.
     *
     * @param configRequest The input request provided by Payline
     * @return The {@link StringResponse} from the HTTP call
     * @throws IOException              Can be thrown while sending the HTTP request
     * @throws InvalidRequestException  Thrown if the input request in not valid
     * @throws NoSuchAlgorithmException Thrown if the HMAC algorithm is not available
     */
    public abstract StringResponse createSendRequest(ContractParametersCheckRequest configRequest) throws IOException, InvalidRequestException, URISyntaxException;

    /**
     * Process the response from the HTTP call.
     * It focuses on business aspect of the processing : the technical part has already been done by {@link #processRequest(ContractParametersCheckRequest)} .
     *
     * @param response The {@link StringResponse} from the HTTP call, which HTTP code is 200 and which body is not null.
     * @return The {@link PaymentResponse}
     * @throws IOException Can be thrown while reading the response body
     */
    public abstract Map<String, String> processResponse(StringResponse response) throws IOException;

    /**
     * Process a {@link PaymentRequest} (or subclass), handling all the generic error cases
     *
     * @param paymentRequest The input request from Payline
     * @return The corresponding {@link PaymentResponse}
     */
    protected Map<String, String> processRequest(ContractParametersCheckRequest paymentRequest) {
        Map<String, String> errors = new HashMap<>();

        try {
            // Mandate the child class to create and send the request (which is specific to each implementation)
            StringResponse response = this.createSendRequest(paymentRequest);

            if (response != null && response.getCode() == HTTP_OK && response.getContent() != null) {
                // Mandate the child class to process the request when it's OK (which is specific to each implementation)
                return this.processResponse(response);
            } else {
                logger.error("The HTTP response or its body is null and should not be");
                errors.put(GENERIC_ERROR, i18n.getMessage("error.errorSamsungServer", paymentRequest.getLocale()));
            }

        } catch (InvalidRequestException e) {
            logger.error("Unable to create the SamsungPay request: {}", e.getMessage(), e);
            errors.put(GENERIC_ERROR, e.getMessage());
        } catch (IOException | URISyntaxException e) {
            logger.error("An error occurred sending the validation request to the SamsungPay server: {}", e.getMessage(), e);
            errors.put(ContractParametersCheckRequest.GENERIC_ERROR, e.getMessage());
        }
        return errors;
    }

}
