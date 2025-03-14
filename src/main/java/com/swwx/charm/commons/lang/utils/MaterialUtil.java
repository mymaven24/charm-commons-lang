package com.swwx.charm.commons.lang.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MaterialUtil {

    /**
     * 将资料模块返回的用户资料信息转化为前端可识别的格式
     * @param metaDataValue
     * @return
     */
    public static Map<String, Object> convertMetaDataValueMapToMaterial(String metaDataValue) {
        Map<String, Object> data=new HashMap<String, Object>();
        Map<String, Object> metaDataMap=(Map<String, Object>)JSONObject.parse(metaDataValue);
        for (Map.Entry<String, Object> entry : metaDataMap.entrySet()) {
            data.put(entry.getKey(), parseMetaData((Map<String, Object>)entry.getValue()));
        }
        return data;
    }

    private static Object parseMetaData(Map<String,Object> metaData) {
        Object data=null;
        for(Map.Entry<String,Object> entry:metaData.entrySet()) {
            if("metaValue".equals(entry.getKey())) {
                Object val=entry.getValue();
                if (val instanceof JSONArray) {
                    JSONArray valArray = JSONArray.parseArray(String.valueOf(entry.getValue()));
                    data = parseArray(valArray.toArray());
                } else if(val instanceof JSONObject) {
                    data=parseMap((Map<String, Object>)val);
                } else {
                    data=entry.getValue();
                }
            }
        }
        return data;
    }

    private static Map<String, Object> parseMap(Map<String, Object> datas) {
        Map<String, Object> dataMap=new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : datas.entrySet()) {
            Object value=null;
            Object val=entry.getValue();
            if (val instanceof JSONArray) {
                JSONArray valArray=(JSONArray)JSONArray.parse(String.valueOf(val));
                value=parseArray(valArray.toArray());
            } else if(val instanceof JSONObject) {
                value=parseMetaData((Map<String, Object>)val);
            } else {
                value=entry.getValue();
            }
            dataMap.put(entry.getKey(), value);
        }
        return dataMap;
    }

    private static List<Map<String, Object>> parseArray(Object[] arrayDatas) {
        List<Map<String, Object>> arrayDataMap=new ArrayList<Map<String, Object>>();
        for (Object arrayData : arrayDatas) {
            Map dataMap=new HashMap();
            if (arrayData instanceof Map) {
                dataMap=parseMap((Map<String, Object>)arrayData);
            }
            arrayDataMap.add(dataMap);
        }
        return arrayDataMap;
    }

    public static void main(String[] args) {

        String jsonStr="{\"idcards\":{\"metaValue\":{\"idcardBack\":{\"metaValue\":{\"sourceType\":{\"metaValue\":\"camera\"},\"index\":{\"metaValue\":\"0\"},\"serverId\":{\"metaValue\":\"1.jpg\"}}},\"idcardHand\":{\"metaValue\":{\"sourceType\":{\"metaValue\":\"album\"},\"index\":{\"metaValue\":\"0\"},\"serverId\":{\"metaValue\":\"1.jpg\"}}},\"idcardFront\":{\"metaValue\":{\"sourceType\":{\"metaValue\":\"album\"},\"index\":{\"metaValue\":\"0\"},\"serverId\":{\"metaValue\":\"1.jpg\"}}}}},\"contacts\":{\"metaValue\":[{\"contactPhone\":{\"metaValue\":\"13611111111\"},\"contactName\":{\"metaValue\":\"张三\"},\"contactType\":{\"metaValue\":\"CLASSMATE\"}},{\"contactPhone\":{\"metaValue\":\"13611111112\"},\"contactName\":{\"metaValue\":\"张四\"},\"contactType\":{\"metaValue\":\"FRIEND\"}},{\"contactPhone\":{\"metaValue\":\"13611111113\"},\"contactName\":{\"metaValue\":\"张五\"},\"contactType\":{\"metaValue\":\"OTHER\"}}]}}";

//        Map data=(Map)JSON.parse(jsonStr);
        //        String metaDataValueMapString="{\"id\":\"1\",\"customerId\":\"110\",\"materialsMap\":[{\"metaName\":\"realName\",\"metaSource\":\"metadata\",\"metaValue\":\"汪红亮\"},{\"metaName\":\"contacts\",\"metaSource\":\"group\",\"metaValue\":[{\"contactName\":\"张三\",\"contactPhone\":\"13000000000\",\"contactType\":\"CLASSMATE\"},{\"contactName\":\"李四\",\"contactPhone\":\"13000000001\",\"contactType\":\"COLLEAGUE\"}]},{\"metaName\":\"idcardFront\",\"metaSource\":\"metadata\",\"metaValue\":[{\"imgName\":\"fdajklfdajfdkalfjdafkdaf;jadfjkfjdsa;fkdsaf\",\"indexNo\":1}]},{\"metaName\":\"ebankFlow\",\"metaSource\":\"metadata\",\"metaValue\":[{\"imgName\":\"34567fdsa\",\"indexNo\":1},{\"imgName\":\"hkfd#fhiodjspokj$hd\",\"indexNo\":2}]}]}";
        Map<String, Object> result=convertMetaDataValueMapToMaterial(jsonStr);

        System.out.println("1=====" + JSON.toJSONString(result));

    }

}
