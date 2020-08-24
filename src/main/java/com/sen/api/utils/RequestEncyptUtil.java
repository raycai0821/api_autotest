package com.sen.api.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author leifeng.cai
 **/
public class RequestEncyptUtil {
    public static String encrypt(String field ,String password){
        String beforesignature = field + password;
        return DigestUtils.md5Hex(beforesignature);
    }

//    public static void main(String[] args) {
//        System.out.println(new RequestEncyptUtil().encrypt("{\n" +
//                "    \"requestId\":\"\",\n" +
//                "    \"requestTime\":\"\",\n" +
//                "    \"reqMerchantId\":\"\",\n" +
//                "    \"merchantId\":\"\",\n" +
//                "    \"countryCode\":\"\",\n" +
//                "    \"currency\":\"\",\n" +
//                "    \"paymentMethod\":\"\",\n" +
//                "    \"channel\":\"\"\n" +
//                "\n" +
//                "}" , "1234"));
//    }
}
