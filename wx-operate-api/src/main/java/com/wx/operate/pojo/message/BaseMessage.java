package com.wx.operate.pojo.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class BaseMessage {
    protected String ToUserName;
    protected String FromUserName;
    protected Long CreateTime;
    protected String MsgType;
}
