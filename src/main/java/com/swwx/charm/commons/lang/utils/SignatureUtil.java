package com.swwx.charm.commons.lang.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;


public class SignatureUtil {

    public static String getSignature(Map<String,String> params, String timestamp, String secret) throws IOException
    {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            sb.append(param.getKey()).append("=").append(param.getValue());
        }
        sb.append(timestamp);
        sb.append(secret);

        //TODO
        System.out.println(String.format("no:%s, sign params is :%s", SysContent.getClueNo(), sb.toString()));

        // 使用MD5对待签名串求签
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(sb.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }

        // 将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }

    public static Map<String, String> getSignParams(String jsonStr) {
        Map<String, String> signParams = new HashMap<>();

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(jsonStr).getAsJsonObject();

        if (jsonObject != null) {
            Iterator it = jsonObject.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, JsonElement> entry = (Map.Entry<String, JsonElement>)it.next();
                JsonElement value = entry.getValue();
                String key = entry.getKey();
                if (value instanceof JsonArray) {
                    signParams.put(key, String.valueOf(value.getAsJsonArray()));
                }else if(value instanceof JsonObject) {
                    signParams.put(key, value.toString());
                }else {
                    signParams.put(key, value.getAsString());
                }
            }
        }

        return signParams;
    }

    public static void main(String[] args) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("amount", "5000");
        params.put("repaymentPeriod", "3");

        System.out.println(getSignature(params, "14523302363475714", "bfa80b06424f91fc48678ed2b392"));

    }

}
