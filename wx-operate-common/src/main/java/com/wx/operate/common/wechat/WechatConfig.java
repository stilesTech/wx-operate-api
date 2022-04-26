package com.wx.operate.common.wechat;

import com.wx.operate.common.utils.SHA1Util;

import java.util.Arrays;

public class WechatConfig {

    //自己设置,要与微信页面设置的一致
    private static final String token = "BDNysaJ5";

    public static boolean checkSignature(String signature,String timestamp,String nonce){

        String[] str = new String[]{token,timestamp,nonce};
        //排序
        Arrays.sort(str);
        //拼接字符串
        StringBuffer buffer = new StringBuffer();
        for(int i =0 ;i<str.length;i++){
            buffer.append(str[i]);
        }
        //进行sha1加密
        String temp = SHA1Util.encode(buffer.toString());
        //与微信提供的signature进行匹对
        return signature.equals(temp);
    }
}
