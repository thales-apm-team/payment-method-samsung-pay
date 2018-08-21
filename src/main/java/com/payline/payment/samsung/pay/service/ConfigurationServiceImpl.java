package com.payline.payment.samsung.pay.service;

import com.payline.payment.samsung.pay.utils.i18n.I18nService;
import com.payline.pmapi.bean.configuration.AbstractParameter;
import com.payline.pmapi.bean.configuration.ContractParametersCheckRequest;
import com.payline.pmapi.bean.configuration.InputParameter;
import com.payline.pmapi.bean.configuration.ReleaseInformation;
import com.payline.pmapi.service.ConfigurationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.*;

/**
 * Created by Thales on 16/08/2018.
 */
public class ConfigurationServiceImpl implements ConfigurationService {

    private static final Logger logger = LogManager.getLogger( ConfigurationServiceImpl.class );

    private static final String RELEASE_DATE_FORMAT = "dd/MM/yyyy";

    private I18nService i18n;

    /**
     * Default public constructor
     */
    public ConfigurationServiceImpl() {
        i18n = I18nService.getInstance();
    }

    @Override
    public List<AbstractParameter> getParameters(Locale locale) {
        List<AbstractParameter> parameters = new ArrayList<>();

        // Merchant name
        final InputParameter merchantName = new InputParameter();
        merchantName.setKey(CONTRACT_CONFIG__MERCHANT_NAME);
        merchantName.setLabel(CONTRACT_CONFIG__MERCHANT_NAME_PROPERTY_LABEL);
        merchantName.setDescription(CONTRACT_CONFIG__MERCHANT_NAME_PROPERTY_DESCRIPTION);
        merchantName.setRequired(true);

        parameters.add(merchantName);

        return parameters;
    }

    @Override
    public Map<String, String> check(ContractParametersCheckRequest contractParametersCheckRequest) {
        return null;
    }

    @Override
    public ReleaseInformation getReleaseInformation() {
        Properties props = new Properties();
        try {
            props.load( ConfigurationServiceImpl.class.getClassLoader().getResourceAsStream( "release.properties" ) );
        } catch( IOException e ){
            logger.error("An error occurred reading the file: release.properties" );
            props.setProperty( "release.version", "unknown" );
            props.setProperty( "release.date", "01/01/1900" );
        }

        LocalDate date = LocalDate.parse( props.getProperty( "release.date" ), DateTimeFormatter.ofPattern( RELEASE_DATE_FORMAT ) );
        return ReleaseInformation.ReleaseBuilder.aRelease()
                .withDate( date )
                .withVersion( props.getProperty( "release.version" ) )
                .build();
    }

    @Override
    public String getName(Locale locale) {
        return i18n.getMessage( "paymentMethod.name", locale );
    }

}
