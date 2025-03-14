package com.swwx.charm.commons.lang.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpSignUtils {

    /**
     * 通知第三方
     * @param url 第三方回调地址
     * @param params 参数
     * @param secretKey 第三方秘钥，用来签名
     * @return true代表通知成功，false为通知失败
     * @throws IOException
     */
    public static boolean post(String url, Map<String, Object> params, String secretKey) throws IOException {
        boolean result = false;
        CloseableHttpResponse response = null;
        try {
            LogPortal.info("callback request is {}, params is {}", url, params);

            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            CloseableHttpClient httpClient = httpClientBuilder.build();
            HttpPost httpPost = new HttpPost(url);

            String jsonStr = JSON.toJSONString(params);
            String timeStamp = String.valueOf(System.currentTimeMillis());
            String sign = SignatureUtil.getSignature(SignatureUtil.getSignParams(jsonStr), timeStamp, secretKey);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Key " + sign);
            httpPost.setHeader("Timestamp", timeStamp);


            StringEntity stringEntity = new StringEntity(jsonStr, "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(stringEntity);

            printHeader(httpPost);

            response = httpClient.execute(httpPost);
            String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");

            if (response.getStatusLine().getStatusCode() == 200 && "success".equals(responseStr)) {
                result = true;
            }

            LogPortal.info("callback request is {}, params is {}, response is [{}]", url, params, responseStr);

            EntityUtils.consumeQuietly(response.getEntity());
        } finally {
            if (response != null) {
                response.close();
            }
        }

        return result;
    }

    public static void printHeader(HttpRequest request) {
        Header[] headers = request.getAllHeaders();
        for (int i = 0; i < headers.length; i++) {
            Header header = headers[i];
            System.out.println(String.format("header name:%s, value:%s", header.getName(), header.getValue()));
        }
    }

    public static void main(String[] args) throws IOException {
        String secretKey = "5714bfa80b06424f91fc48678ed2b392";

        String url = "http://101.200.82.142:8081/callBackApplyResult";

        Map<String, Object> params = new HashMap<>();
        params.put("businessType", "APPLY_STATUS_CHANGED");

        Map<String, Object> data = new HashMap<>();

        params.put("data", data);

        post(url, params, secretKey);
    }
}
