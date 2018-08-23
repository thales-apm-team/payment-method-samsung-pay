package com.payline.payment.samsung.pay.bean.rest.response;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.payline.payment.samsung.pay.bean.rest.response.nesteed.EncryptionInfo;

/**
 * Created by Thales on 20/08/2018.
 */
public class CreateTransactionPostResponse extends AbstractJsonResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("href")
    private String href;

    @SerializedName("encInfo")
    private EncryptionInfo encryptionInfo;

    /**
     * Constructor
     */
    protected CreateTransactionPostResponse() {
        super();
    }

    public String getId() {
        return this.id;
    }

    public String getHref() {
        return this.href;
    }

    public EncryptionInfo getEncryptionInfo() {
        return this.encryptionInfo;
    }

    public static final class Builder {
        public CreateTransactionPostResponse fromJson(String jsonContent ) {
            Gson gson = new Gson();
            return gson.fromJson( jsonContent, CreateTransactionPostResponse.class );
        }
    }

}