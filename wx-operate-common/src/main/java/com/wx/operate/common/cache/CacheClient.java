package com.wx.operate.common.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

public class CacheClient {

    private static final Cache<String, String> Local_Cache;
    private static final Cache<String, String> Local_Short_Cache;

    public static Cache<String, String> getLocalCache(){
        return Local_Cache;
    }

    public static Cache<String, String> getLocalShortCache(){
        return Local_Short_Cache;
    }
    static {
        Local_Cache = build(10000, 24, TimeUnit.HOURS);
        Local_Short_Cache = build(10000, 5, TimeUnit.MINUTES);
    }


    static Cache<String, String> build(int maxSize, int expire, TimeUnit timeUnit) {
        return CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expire, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }
}
