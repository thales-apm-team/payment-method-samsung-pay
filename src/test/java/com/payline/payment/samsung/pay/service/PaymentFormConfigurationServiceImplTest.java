package com.payline.payment.samsung.pay.service;

import com.payline.pmapi.bean.paymentform.bean.PaymentFormLogo;
import com.payline.pmapi.bean.paymentform.request.PaymentFormLogoRequest;
import com.payline.pmapi.bean.paymentform.response.configuration.PaymentFormConfigurationResponse;
import com.payline.pmapi.bean.paymentform.response.configuration.impl.PaymentFormConfigurationResponseSpecific;
import com.payline.pmapi.bean.paymentform.response.logo.PaymentFormLogoResponse;
import com.payline.pmapi.bean.paymentform.response.logo.impl.PaymentFormLogoResponseFile;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.LOGO_NAME;
import static com.payline.payment.samsung.pay.utils.Utils.*;

/**
 * Created by Thales on 27/08/2018.
 */
@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class PaymentFormConfigurationServiceImplTest {

    @InjectMocks
    private PaymentFormConfigurationServiceImpl service;

    @Test
    public void testGetPaymentFormConfiguration() {
        // when: getPaymentFormConfiguration is called
        PaymentFormConfigurationResponse response = service.getPaymentFormConfiguration(createDefaultPaymentFormConfigurationRequest());

        // then: returned object is an instance of PaymentFormConfigurationResponseSpecific
        Assert.assertTrue(response instanceof PaymentFormConfigurationResponseSpecific);
    }

    @Test
    public void testGetLogo() {
        // when: getLogo is called
        PaymentFormLogo paymentFormLogo = service.getLogo("", Locale.getDefault());

        // then: returned elements are not null
        Assert.assertNotNull(paymentFormLogo);
        Assert.assertNotNull(paymentFormLogo.getFile());
        Assert.assertNotNull(paymentFormLogo.getContentType());
    }

    @Test
    public void testGetPaymentFormLogo() throws IOException {
        // given: the logo image read from resources
        InputStream input = PaymentFormConfigurationServiceImpl.class.getClassLoader().getResourceAsStream(LOGO_NAME);
        BufferedImage image = ImageIO.read(input);

        // when: getPaymentFormLogo is called
        PaymentFormLogoRequest request = PaymentFormLogoRequest.PaymentFormLogoRequestBuilder.aPaymentFormLogoRequest()
                .withLocale(Locale.getDefault())
                .withEnvironment(createDefaultPaylineEnvironment())
                .withContractConfiguration(createDefaultContractConfiguration())
                .withPartnerConfiguration(createDefaultPartnerConfiguration())
                .build();
        PaymentFormLogoResponse paymentFormLogoResponse = service.getPaymentFormLogo(request);

        // then: returned elements match the image file data
        Assert.assertTrue(paymentFormLogoResponse instanceof PaymentFormLogoResponseFile);
        PaymentFormLogoResponseFile casted = (PaymentFormLogoResponseFile) paymentFormLogoResponse;
        Assert.assertEquals(image.getHeight(), casted.getHeight());
        Assert.assertEquals(image.getWidth(), casted.getWidth());
        Assert.assertNotNull(casted.getTitle());
        Assert.assertNotNull(casted.getAlt());
    }

}