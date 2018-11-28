package com.payline.payment.samsung.pay.utils.type;

import com.payline.pmapi.bean.common.FailureCause;

import static com.payline.payment.samsung.pay.utils.SamsungPayConstants.*;

/**
 * Created by Thales on 22/08/2018.
 *
 * @See Confluence https://payline.atlassian.net/wiki/spaces/APMSDK/pages/637468827/Samsung+Pay for error mapping
 */
public enum WSRequestResultEnum {

    RESULT_0(
            HTTP_CODE_200,
            SAMSUNG_PAY_CODE_0,
            SAMSUNG_PAY_CODE_0_DESCRIPTION,
            null
    ),

    RESULT_OPM1N1001(
            HTTP_CODE_400,
            SAMSUNG_PAY_CODE_OPM1N1001,
            SAMSUNG_PAY_CODE_OPM1N1001_DESCRIPTION,
            FailureCause.INVALID_DATA
    ),

    RESULT_OPM1N1002(
            HTTP_CODE_400,
            SAMSUNG_PAY_CODE_OPM1N1002,
            SAMSUNG_PAY_CODE_OPM1N1002_DESCRIPTION,
            FailureCause.INVALID_DATA
    ),

    RESULT_OPM1N1003(
            HTTP_CODE_400,
            SAMSUNG_PAY_CODE_OPM1N1003,
            SAMSUNG_PAY_CODE_OPM1N1003_DESCRIPTION,
            FailureCause.INVALID_DATA
    ),

    RESULT_OPM1N1026(
            HTTP_CODE_400,
            SAMSUNG_PAY_CODE_OPM1N1026,
            SAMSUNG_PAY_CODE_OPM1N1026_DESCRIPTION,
            FailureCause.INVALID_DATA
    ),

    RESULT_OPM1N1033(
            HTTP_CODE_400,
            SAMSUNG_PAY_CODE_OPM1N1033,
            SAMSUNG_PAY_CODE_OPM1N1033_DESCRIPTION,
            FailureCause.REFUSED
    ),

    RESULT_OPM1N1035(
            HTTP_CODE_404,
            SAMSUNG_PAY_CODE_OPM1N1035,
            SAMSUNG_PAY_CODE_OPM1N1035_DESCRIPTION,
            FailureCause.REFUSED
    ),

    RESULT_OPM2N1103(
            HTTP_CODE_409,
            SAMSUNG_PAY_CODE_OPM2N1103,
            SAMSUNG_PAY_CODE_OPM2N1103_DESCRIPTION,
            FailureCause.REFUSED
    ),

    RESULT_OPM3N1002(
            HTTP_CODE_500,
            SAMSUNG_PAY_CODE_OPM3N1002,
            SAMSUNG_PAY_CODE_OPM3N1002_DESCRIPTION,
            FailureCause.INVALID_DATA
    ),

    RESULT_OPM3N1003(
            HTTP_CODE_500,
            SAMSUNG_PAY_CODE_OPM3N1003,
            SAMSUNG_PAY_CODE_OPM3N1003_DESCRIPTION,
            FailureCause.PAYMENT_PARTNER_ERROR
    ),

    RESULT_OPM5N9001(
            HTTP_CODE_500,
            SAMSUNG_PAY_CODE_OPM5N9001,
            SAMSUNG_PAY_CODE_OPM5N9001_DESCRIPTION,
            FailureCause.PAYMENT_PARTNER_ERROR
    );

    private String httpCode;
    private String resultCode;
    private String description;

    private FailureCause paylineResult;

    WSRequestResultEnum(String httpCode,
                        String resultCode,
                        String description,
                        FailureCause paylineResult) {

        this.httpCode       = httpCode;
        this.resultCode     = resultCode;
        this.description    = description;
        this.paylineResult  = paylineResult;

    }

    public String getHttpCode() {
        return httpCode;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getDescription() {
        return description;
    }

    public FailureCause getPaylineResult() {
        return paylineResult;
    }

    public static WSRequestResultEnum fromResultCodeValue(String text) {
        for (WSRequestResultEnum resultEnum : WSRequestResultEnum.values()) {
            if (resultEnum.getResultCode().equals(text)) {
                return resultEnum;
            }
        }
        return null;
    }

}