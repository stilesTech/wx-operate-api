package com.wx.operate.pojo.message;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TextMessage extends BaseMessage {

    private String Content;// 文本消息内容

    private String MsgId;// 消息id，64位整型

}