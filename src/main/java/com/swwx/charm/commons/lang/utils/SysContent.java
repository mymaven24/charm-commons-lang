package com.swwx.charm.commons.lang.utils;

public class SysContent {

    private static ThreadLocal<String> clueThread = new ThreadLocal<String>();

    public static String getClueNo() {
        return clueThread.get();
    }

    public static void setClueNo(String no) {
        clueThread.set(no);
    }
}
