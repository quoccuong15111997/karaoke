package com.nqc.util;

/**
 * Created by thieumao on 1/29/17.
 */

public class Base64 {
    public static String base64UrlDecode(String input) {
        if(input != null && !input.isEmpty()) {
            String result = null;
            com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64 decoder = new com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64(true);
            byte[] decodedBytes = decoder.decode(input);
            result = new String(decodedBytes);
            return result;
        }
        return null;
    }
}
