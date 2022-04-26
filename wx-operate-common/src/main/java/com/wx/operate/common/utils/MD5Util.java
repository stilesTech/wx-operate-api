package com.wx.operate.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author stiles
 */
public class MD5Util {

    public static String encrypt16(String value) {
        if (StringUtils.isBlank(value)) {
            return "";
        }

        return encrypt32(value).substring(8, 24);
    }

    public static String encrypt32(String value) {
        if (StringUtils.isNotBlank(value)){
            return org.springframework.util.DigestUtils.md5DigestAsHex(value.getBytes());
        }
        return "";
    }
}
