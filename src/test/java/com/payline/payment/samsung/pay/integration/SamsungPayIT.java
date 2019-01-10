package com.payline.payment.samsung.pay.integration;

import com.payline.payment.samsung.pay.service.PaymentServiceImpl;
import com.payline.payment.samsung.pay.service.PaymentWithRedirectionServiceImpl;
import com.payline.payment.samsung.pay.utils.SamsungPayConstants;
import com.payline.payment.samsung.pay.utils.Utils;
import com.payline.pmapi.bean.payment.ContractProperty;
import com.payline.pmapi.bean.payment.PaymentFormContext;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.request.RedirectionPaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentModeCard;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseDoPayment;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFormUpdated;
import com.payline.pmapi.bean.paymentform.bean.form.PartnerWidgetForm;
import com.payline.pmapi.bean.paymentform.response.configuration.impl.PaymentFormConfigurationResponseSpecific;
import com.payline.pmapi.service.PaymentService;
import com.payline.pmapi.service.PaymentWithRedirectionService;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static com.payline.payment.samsung.pay.utils.Utils.FAILURE_URL;
import static com.payline.payment.samsung.pay.utils.Utils.SUCCESS_URL;

/**
 * Created by Thales on 27/08/2018.
 */
public class SamsungPayIT {
    private static final String merchantName = "foo";
    private static final String EMAIL = "payline.pilote@monext.net";

    private PaymentServiceImpl paymentService = new PaymentServiceImpl();
    private PaymentWithRedirectionServiceImpl paymentWithRedirectionService = new PaymentWithRedirectionServiceImpl();


    @Test
    public void fullPaymentTest() {
        PaymentRequest request = createDefaultPaymentRequest();
        this.fullRedirectionPayment(request, paymentService, paymentWithRedirectionService);

    }


    protected Map<String, ContractProperty> generateParameterContract() {
        Map<String, ContractProperty> propertyHashMap = new HashMap<>();
        propertyHashMap.put(SamsungPayConstants.CONTRACT_CONFIG_MERCHANT_NAME, new ContractProperty(merchantName));

        return propertyHashMap;
    }

    protected PaymentFormContext generatePaymentFormContext() {
        return null;
    }


    protected String payOnPartnerWebsite(String script) {

        ChromeOptions options = new ChromeOptions();

        options.addArguments("disable-web-security");
        options.addArguments("allow-running-insecure-content");

        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY, options);

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        cap.setJavascriptEnabled(true);
        // Start browser
        WebDriver driver = new ChromeDriver(cap);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {

            ClassLoader classLoader = SamsungPayIT.class.getClassLoader();
            File keyFile = new File(classLoader.getResource("index.html").getFile());
            // Go to partner's website
            driver.get("file://" + keyFile.getAbsolutePath());


            js.executeScript(script);

            driver.findElement(By.id("email")).sendKeys(EMAIL);
            ((ChromeDriver) driver).findElementByXPath("/html/body/div/div[2]/div[1]/div[3]/div/button[2]").click();

            // Wait for redirection to success or cancel url
            WebDriverWait wait = new WebDriverWait(driver, 60 * 5);
            wait.until(ExpectedConditions.or(ExpectedConditions.urlContains(SUCCESS_URL), ExpectedConditions.urlToBe(FAILURE_URL)));

            return driver.getCurrentUrl();
        } finally {
            driver.quit();
        }
    }


    protected PaymentRequest createDefaultPaymentRequest() {
        return Utils.createCompletePaymentBuilder().build();
    }

    public void fullRedirectionPayment(PaymentRequest paymentRequest, PaymentService paymentService, PaymentWithRedirectionService paymentWithRedirectionService) {
        // call PaymentService
        PaymentResponse paymentResponseFromPaymentRequest = paymentService.paymentRequest(paymentRequest);

        // check response
        Assert.assertNotNull(paymentResponseFromPaymentRequest);
        Assert.assertEquals(PaymentResponseFormUpdated.class, paymentResponseFromPaymentRequest.getClass());

        // convert response
        PaymentResponseFormUpdated paymentResponseFormUpdated = (PaymentResponseFormUpdated) paymentResponseFromPaymentRequest;

        //
        PaymentFormConfigurationResponseSpecific formConfigurationResponse = (PaymentFormConfigurationResponseSpecific) paymentResponseFormUpdated.getPaymentFormConfigurationResponse();
        PartnerWidgetForm form = (PartnerWidgetForm) formConfigurationResponse.getPaymentForm();
        String functionToCall = form.getLoadingScriptAfterImport();

        Assert.assertNotNull(functionToCall);

        String redirectionUrl = this.payOnPartnerWebsite(functionToCall);
        // example of redirection url: https://www.thales.com/?ref_id=e3ecf390809649e98af0b2

        // check payment response
        Assert.assertTrue(redirectionUrl.contains(SUCCESS_URL));

        String referenceId = substringAfterLast(redirectionUrl, "ref_id=");
        Assert.assertTrue(referenceId.length() > 0);

        // create paymentWithRedirectionRequest
        Map<String, String[]> requestParam = new HashMap<>();
        String[] param = {referenceId};
        requestParam.put(SamsungPayConstants.REF_ID, param);
        RedirectionPaymentRequest redirectionPaymentRequest = Utils.createRedirectionPaymentRequestBuilder()
                .withHttpRequestParametersMap(requestParam)
                .build();

        // call paymentWithRedirectionService
        PaymentResponse paymentResponseFromFinalize = paymentWithRedirectionService.finalizeRedirectionPayment(redirectionPaymentRequest);

        // check response
        Assert.assertNotNull(paymentResponseFromPaymentRequest);
        Assert.assertEquals(PaymentResponseDoPayment.class, paymentResponseFromFinalize.getClass());
        PaymentResponseDoPayment responseDoPayment = (PaymentResponseDoPayment) paymentResponseFromFinalize;
        Assert.assertNotNull(responseDoPayment.getPaymentMode());
        PaymentModeCard modeCard = (PaymentModeCard) responseDoPayment.getPaymentMode();
        Assert.assertNotNull(modeCard.getCard());
    }


    private static String substringAfterLast(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        } else if (isEmpty(separator)) {
            return "";
        } else {
            int pos = str.lastIndexOf(separator);
            return pos != -1 && pos != str.length() - separator.length() ? str.substring(pos + separator.length()) : "";
        }
    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}