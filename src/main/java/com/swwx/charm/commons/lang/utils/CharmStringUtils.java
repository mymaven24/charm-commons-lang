package com.swwx.charm.commons.lang.utils;

import org.apache.commons.lang.StringUtils;

import java.util.UUID;

public class CharmStringUtils {

    public static String generateUUID(){
        return StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }
}
