package com.wx.operate.controller;

import com.wx.operate.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author stiles
 */
@RestController
@RequestMapping("r")
@Api(tags = "重定向接口")
@Slf4j
public class RedirectController {
    @Autowired
    ArticleService articleService;

    @ApiOperation(value = "跳转到微信图文")
    @RequestMapping(value = "/to", method = RequestMethod.GET)
    public void toWxArticle(String p,HttpServletResponse resp) throws Exception {
        if(StringUtils.isBlank(p)){
            return;
        }
        String url = articleService.getUrl(p);
        if(StringUtils.isBlank(url)){
            return;
        }
        resp.sendRedirect(url);
    }
}
