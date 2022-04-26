package com.wx.operate.pojo.message;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
//@EqualsAndHashCode(callSuper = true)
public class ImageMessage {
    private String FromUserName;
    private String ToUserName;
    private String MsgType;
    private long CreateTime;
}
