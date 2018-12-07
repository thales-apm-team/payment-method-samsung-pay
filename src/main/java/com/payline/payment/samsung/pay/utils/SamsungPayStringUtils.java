package com.payline.payment.samsung.pay.utils;

public class SamsungPayStringUtils {
    public static boolean isEmpty(String str){
        if( str == null || str.length() == 0){
            return true;
        }else{
            for(int i=0; i<str.length(); i++){
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }
}
