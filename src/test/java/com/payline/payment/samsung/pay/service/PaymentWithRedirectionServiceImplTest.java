package com.payline.payment.samsung.pay.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.payline.payment.samsung.pay.utils.http.SamsungPayHttpClient;

/**
 * Created by Thales on 27/08/2018.
 */
@RunWith( MockitoJUnitRunner.class )
public class PaymentWithRedirectionServiceImplTest {

    @Mock
    private SamsungPayHttpClient httpClient;

    @InjectMocks
    private PaymentWithRedirectionServiceImpl service;

    @Before
    public void setup(){

    }

    @Test
    public void test() {
        
    }

}