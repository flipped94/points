package org.example.points.auth.utils;

import java.util.UUID;

public class TokenUtil {

    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
