package com.payline.payment.samsung.pay.service;

import com.payline.payment.samsung.pay.utils.i18n.I18nService;
import com.payline.pmapi.bean.paymentform.bean.PaymentFormLogo;
import com.payline.pmapi.bean.paymentform.request.PaymentFormConfigurationRequest;
import com.payline.pmapi.bean.paymentform.request.PaymentFormLogoRequest;
import com.payline.pmapi.bean.paymentform.response.configuration.PaymentFormConfigurationResponse;
import com.payline.pmapi.bean.paymentform.response.configuration.impl.PaymentFormConfigurationResponseProvided;
import com.payline.pmapi.bean.paymentform.response.logo.PaymentFormLogoResponse;
import com.payline.pmapi.bean.paymentform.response.logo.impl.PaymentFormLogoResponseFile;
import com.payline.pmapi.service.PaymentFormConfigurationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;

import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.*;

/**
 * Created by Thales on 27/08/2018.
 */
public class PaymentFormConfigurationServiceImpl implements PaymentFormConfigurationService {
    private static final Logger logger = LogManager.getLogger("PaymentFormConfigurationService");

    private static final String LOGO_CONTENT_TYPE = "image/jpeg";
    private static final int LOGO_HEIGHT = 92;
    private static final int LOGO_WIDTH = 390;

    @Override
    public PaymentFormConfigurationResponse getPaymentFormConfiguration(PaymentFormConfigurationRequest paymentFormConfigurationRequest) {
        return PaymentFormConfigurationResponseProvided.PaymentFormConfigurationResponseBuilder.aPaymentFormConfigurationResponse()
                .withContextPaymentForm(new HashMap<>())
                .build();
    }

    @Override
    public PaymentFormLogoResponse getPaymentFormLogo(PaymentFormLogoRequest paymentFormLogoRequest) {

        I18nService i18n = I18nService.getInstance();

        return PaymentFormLogoResponseFile.PaymentFormLogoResponseFileBuilder.aPaymentFormLogoResponseFile()
                .withHeight(LOGO_HEIGHT)
                .withWidth(LOGO_WIDTH)
                .withTitle(i18n.getMessage(FORM_CONFIG__LOGO_TITLE, paymentFormLogoRequest.getLocale()))
                .withAlt(i18n.getMessage(FORM_CONFIG__LOGO_ALT, paymentFormLogoRequest.getLocale()))
                .build();
    }

    @Override
    public PaymentFormLogo getLogo(String var1, Locale locale) {
        try {
            // Read logo file
            InputStream input = PaymentFormConfigurationServiceImpl.class.getClassLoader().getResourceAsStream(RES_LOGO_NAME);
            BufferedImage logo = ImageIO.read(input);

            // Recover byte array from image
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(logo, "jpg", baos);

            return PaymentFormLogo.PaymentFormLogoBuilder.aPaymentFormLogo()
                    .withFile(baos.toByteArray())
                    .withContentType(LOGO_CONTENT_TYPE)
                    .build();
        } catch (IOException e) {
            logger.error("Unable to load the logo", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}