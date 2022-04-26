package com.wx.operate.common.utils;

import com.alibaba.fastjson.JSON;
import java.util.List;

public class JsonUtil {
    public static <T> String serialize(T obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T deserialize(String json, Class<T> targetClass) {
        return (T) JSON.parseObject(json,targetClass);
    }

    public static <T> String serializeList(List<T> objList) {
        return JSON.toJSONString(objList);
    }

    public static <T> List<T> deserializeList(String json, Class<T> targetClass) {
        return JSON.parseArray(json, targetClass);
    }
}

