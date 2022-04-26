package com.wx.operate.common.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author stiles
 */
public class TimeUtil {

    /**
     * 时间转时间戳
     * @param date
     * @return
     */
    public static Long getTimeStamp(Date date){
        return date.getTime()/1000;
    }

    /**
     * 时间戳转时间
     * @param timestamp
     * @return
     */
    public static Date convertDate(Long timestamp){
        return new Date(timestamp*1000);
    }


    public static Long getTimeStamp(){
        String timestamp = String.valueOf(System.currentTimeMillis()/1000);
        return Long.valueOf(timestamp);
    }

    /**
     * 获取东八区的时间
     * @return
     */
    public static Date getCurrentDate() {
        LocalDateTime datetime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        ZonedDateTime zdt = datetime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }
}
