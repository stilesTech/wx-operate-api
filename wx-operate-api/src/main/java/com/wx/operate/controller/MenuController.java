package com.wx.operate.controller;

import com.alibaba.fastjson.JSONObject;
import com.wx.operate.pojo.query.BaseQuery;
import com.wx.operate.utils.AccessTokenUtil;
import com.wx.operate.utils.MenuUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "微信菜单接口")
@RequestMapping(value = "/menu")
@Slf4j
public class MenuController {
    @Autowired
    private MenuUtil menuUtil;
    @Autowired
    AccessTokenUtil accessTokenUtil;

    @RequestMapping(value = "/createMenu", method = RequestMethod.POST)
    public Object createMenu(BaseQuery baseQuery) {
        String accessToken = accessTokenUtil.getAccessToken(baseQuery.getWechatConfigId());
        JSONObject jsonObject = menuUtil.creatMenu(accessToken);
        return jsonObject;
    }

    @RequestMapping(value = "/getMenu", method = RequestMethod.GET)
    public Object getMenu(BaseQuery baseQuery) {
        String accessToken = accessTokenUtil.getAccessToken(baseQuery.getWechatConfigId());
        JSONObject jsonObject = menuUtil.getWXMenu(accessToken);
        return jsonObject;

    }
}
