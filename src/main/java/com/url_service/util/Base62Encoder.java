package com.url_service.util;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder {

    private static final String BASE62 =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = BASE62.length();

    public String encode(long value) {
        if (value == 0) {
            return "a";
        }

        StringBuilder sb = new StringBuilder();

        while (value > 0) {
            int remainder = (int) Math.abs(value % BASE);
            sb.append(BASE62.charAt(remainder));
            value = value / BASE;
        }

        return sb.reverse().toString();
    }
}
