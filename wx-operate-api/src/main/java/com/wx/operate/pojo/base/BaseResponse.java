package com.wx.operate.pojo.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
    private Integer errcode;
    private String errmsg;
}
