package com.sen.api.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author leifeng.cai
 **/
public class RequestEncyptUtil {
    public static String encrypt(String field ,String password){
        String beforesignatureignature = field + password;
        return DigestUtils.md5Hex(beforesignatureignature);
    }
    private static final int HEX_VALUE_COUNT = 16;


    public static String getMD5(byte[] bytes) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] str = new char[32];

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte[] tmp = md.digest();
            int k = 0;

            for(int i = 0; i < 16; ++i) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return new String(str);
    }

    public static String getMD5(String value, String encode) {
        String result = "";

        try {
            result = getMD5(value.getBytes(encode));
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
        String json = "{" +
                "    \"countryCode\":\"GB\",\n" +
                "    \"channel\":\"BC000001\",\n" +
                "    \"currency\":\"EUR\",\n" +
                "    \"requestId\":\"123456\",\n" +
                "    \"memberCode\":\"memberCode1123\",\n" +
                "    \"paymentMethod\":\"LOCAL\",\n" +
                "    \"apiVersion\":\"1\",\n" +
                "    \"requestTime\":\"2021-01-01T21:30:00+0800\"\n" +
                "}";
        System.out.println(getMD5("" + "1223", StandardCharsets.UTF_8.name()));
    }
}
