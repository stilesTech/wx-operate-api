package com.wx.operate.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.wx.operate.constants.URIConstant;
import com.wx.operate.entity.WechatConfig;
import com.wx.operate.service.WechatConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class AccessTokenUtil {

    private static final Cache<String, String> TOKEN_KEY_CACHE;
    private static final Cache<String, String> TOKEN_TIME_KEY_CACHE;
    @Autowired
    WechatConfigService wechatConfigService;

    static {
        TOKEN_KEY_CACHE = build(500, 2, TimeUnit.HOURS);
        TOKEN_TIME_KEY_CACHE = build(500, 2, TimeUnit.HOURS);
    }

    static Cache<String, String> build(int maxSize, int expire, TimeUnit timeUnit) {
        return CacheBuilder.newBuilder()
                .maximumSize(maxSize)
                .expireAfterWrite(expire, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    @Autowired
    private RestTemplate restTemplate;

    public String getAccessToken(int wechatConfigId) {

        String tokenKey = getTokenKey(wechatConfigId);
        String tokenTime = getTokenTime(wechatConfigId);
        //先从redis中获取access_token
        String accessToken = (String) TOKEN_KEY_CACHE.getIfPresent(tokenKey);
        if (accessToken != null) {
//            log.info("原来的access_token还有效存在！,token_key：{}, 产生的时间是:{}"
//                    , TOKEN_KEY_CACHE.getIfPresent(tokenKey)
//                    , TOKEN_TIME_KEY_CACHE.getIfPresent(tokenTime)
//            );
            return accessToken;
        } else {
            //否则就重新开始获取access_token
            try {
                String url = URIConstant.ACCESS_TOKEN_URL;
                Map<String, String> params = new HashMap<>();
                WechatConfig wechatConfig = wechatConfigService.getById(wechatConfigId);
                //这里设置开发者appid和secret
                params.put("appId", wechatConfig.getAppId());
                params.put("secret", wechatConfig.getAppSecret());

                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime time = LocalDateTime.now();
                String localTime = df.format(time);

                JSONObject jsonObject = restTemplate.getForObject(url, JSONObject.class, params);
                if (null != jsonObject) {
                    log.info("调用接口获取新的access_token！,时间是：{}", localTime);
                    String access_token = jsonObject.getString("access_token");
                    int expires_in = jsonObject.getIntValue("expires_in");
                    if (access_token != null) {
                        log.error("获得access_token成功：{} ", jsonObject.toJSONString());
                        TOKEN_KEY_CACHE.put(tokenKey, access_token);//保存2小时（官方是2小时失效）
                        TOKEN_TIME_KEY_CACHE.put(tokenTime, localTime);//保存时间
                        return access_token;
                    } else {
                        int errorCode = jsonObject.getIntValue("errcode");
                        String errorMsg = jsonObject.getString("errmsg");
                        log.error("获得access_token失败：{} ", jsonObject.toJSONString());
                    }
                }

            } catch (RestClientException e) {
                log.error("请根据提示信息设置接口的白名单IP：", e.getMessage());
            } catch (JSONException e) {
                log.error("字符串转换异常：", e.getMessage());
            }
            return null;
        }
    }

    private String getTokenKey(int wechatConfigId) {
        return String.format("access_token_%d", wechatConfigId);
    }

    private String getTokenTime(int wechatConfigId) {
        return String.format("token_time_%d", wechatConfigId);
    }
}